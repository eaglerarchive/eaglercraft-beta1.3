package net.lax1dude.eaglercraft.beta.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.CRC32;

public class PasswordManager {
	
	public static final Logger log = Logger.getLogger("Eaglercraft");
	
	public static class PasswordEntry {
		
		public static final int ROW_LENGTH = 
				4 // crc
				+ 8 // date created
				+ 4 // expires after
				+ 4 // username hashCode()
				+ 2 //username length
				+ 16 * 2 //username chars
				+ 9 // salt
				+ 20; // hash

		public final long createTime;
		public final int expiresAfter;
		
		public final String username;
		
		public final byte[] salt;
		public final byte[] password;
		
		protected PasswordEntry(String username, byte[] salt, byte[] password, long createTime, int expiresAfter) {
			this.username = username.toLowerCase();
			this.salt = salt;
			this.password = password;
			this.createTime = createTime;
			this.expiresAfter = expiresAfter;
		}
		
		public int secondsRemaining() {
			if(expiresAfter <= 0) {
				return Integer.MAX_VALUE;
			}else {
				int r = expiresAfter - (int)((System.currentTimeMillis() - createTime) / 1000l);
				if(r < 0) {
					return 0;
				}else {
					return r;
				}
			}
		}
		
	}
	
	public static final int passDBVersion = 1;
	
	protected static final Map<String, PasswordEntry> passwordEntries = new HashMap();

	public static final File passwordDatabaseFile = new File("passDB.dat");
	public static final File passwordDatabaseFileOld = new File("passDB.dat.bak");

	private static final byte[] header = ":> Eag!BPsDB#_:".getBytes(StandardCharsets.US_ASCII);
	private static final byte[] footerEOF = "!EOF".getBytes(StandardCharsets.US_ASCII);
	
	public static void loadPasswordDB() throws IOException {
		if(passwordDatabaseFileOld.exists()) {
			log.warning("The password database wasn't saved correctly, restoring '" + passwordDatabaseFileOld.getName() + "' backup");
			if(passwordDatabaseFile.exists()) {
				passwordDatabaseFile.delete();
			}
			passwordDatabaseFileOld.renameTo(passwordDatabaseFile);
		}
		if(!passwordDatabaseFile.exists()) {
			initializeNewDatabase();
		}
		if(!loadFile(passwordDatabaseFile)) {
			log.severe("The password database '" + passwordDatabaseFile.getName() + "' was corrupt, it will be reset");
			initializeNewDatabase();
		}
	}
	
	public static PasswordEntry load(String username) {
		if(discardExpiredPasswords()) {
			try {
				syncDatabase();
			}catch(SyncException e) {
				Throwable t = e.getCause();
				System.err.println("Could not write passwords to disk!");
				if(t != null) {
					t.printStackTrace();
				}else {
					e.printStackTrace();
				}
			}
		}
		synchronized(passwordEntries) {
			return passwordEntries.get(username.toLowerCase());
		}
	}
	
	private static boolean loadFile(File f) {
		try(FileInputStream fis = new FileInputStream(f)) {
			synchronized(passwordEntries) {
				passwordEntries.clear();
			}
			
			DataInputStream dis = new DataInputStream(fis);
			
			byte[] headerBytes = new byte[15];
			dis.read(headerBytes);
			if(!Arrays.equals(headerBytes, header)) {
				log.severe("Error, could not load '" + f.getName() + "' because it's not a Eag!BPsDB# file");
				return false;
			}
			
			byte[] versionString = new byte[dis.read() & 0xF];
			dis.read(versionString);
			
			String correctVers = "" + passDBVersion;
			while(correctVers.length() < 4) {
				correctVers = "0" + correctVers;
			}
			correctVers = "v" + correctVers;
			
			String versionStringString = new String(versionString, StandardCharsets.US_ASCII);
			if(!versionStringString.equals(correctVers)) {
				log.severe("Error, could not load '" + f.getName() + "' because it is an unsupported version of the password database format");
				return false;
			}

			int supposedToHaveRows = dis.readInt();
			if(supposedToHaveRows != dis.readInt() || supposedToHaveRows != dis.readInt()) {
				log.severe("Error, could not load '" + f.getName() + "' because it contains a corrupt header");
				return false;
			}
			
			CRC32 crc = new CRC32();
			
			boolean mustRewrite = false;
			int fuck = 0;
			for(int i = 0; i < supposedToHaveRows; ++i) {
				int crcInt = dis.readInt();
				byte[] rowBytes = new byte[PasswordEntry.ROW_LENGTH - 4];
				dis.read(rowBytes);
				crc.reset();
				crc.update(rowBytes);
				if(((long)crcInt & 0xFFFFFFFFL) != crc.getValue()) {
					++fuck;
					continue;
				}
				PasswordEntry pse = readRow(rowBytes);
				if(pse == null) {
					++fuck;
					continue;
				}
				synchronized(passwordEntries) {
					if(pse.secondsRemaining() > 0 && !(!EaglercraftServer.config.allowPasswordsWithoutExpire() && pse.expiresAfter == -1)) {
						passwordEntries.put(pse.username, pse);
					}else {
						mustRewrite = true;
					}
				}
			}
			
			if(fuck > 0) {
				log.severe("Danger, file '" + f.getName() + "' contained " + fuck + " passwords that were not loaded!");
			}
			
			byte[] eof = new byte[footerEOF.length];
			dis.read(eof);
			
			if(!Arrays.equals(eof, footerEOF)) {
				log.severe("Danger, file '" + f.getName() + "' contained extra data, the 'extra' data has been discarded!");
			}
			
			if(mustRewrite) {
				try {
					syncDatabase();
				}catch(SyncException e) {
					Throwable t = e.getCause();
					System.err.println("Some passwords expired, could not write remaining to disk!");
					if(t != null) {
						t.printStackTrace();
					}else {
						e.printStackTrace();
					}
				}
			}
			
			return true;
		}catch(Throwable t) {
			log.severe("Error, could not load '" + f.getName() + "' because: " + t.toString());
			t.printStackTrace();
			return false;
		}
	}
	
	private static void initializeNewDatabase() throws IOException {
		synchronized(passwordEntries) {
			passwordEntries.clear();
		}
		try {
			syncDatabase();
		}catch(SyncException e) {
			Throwable t = e.getCause();
			if(t != null && t instanceof IOException) {
				throw (IOException)t;
			}else {
				throw new IOException("Database creation failed!", t);
			}
		}
	}
	
	private static class SyncException extends Exception {
		public SyncException(String message, Throwable cause) {
			super(message, cause);
		}
		public SyncException(String message) {
			super(message);
		}
		public SyncException(Throwable cause) {
			super(cause);
		}
	}
	
	private static void syncDatabase() throws SyncException {
		if(passwordDatabaseFile.exists()) {
			if(passwordDatabaseFileOld.exists()) {
				passwordDatabaseFileOld.delete();
			}
			passwordDatabaseFile.renameTo(passwordDatabaseFileOld);
		}
		try(OutputStream os = new FileOutputStream(passwordDatabaseFile)) {
			synchronized(passwordEntries) {
				ArrayList<byte[]> chunks = new ArrayList(passwordEntries.size());
				
				for(PasswordEntry etr : passwordEntries.values()) {
					byte[] b = writeRow(etr);
					if(b != null) {
						chunks.add(b);
					}else {
						log.warning("Password for '" + etr.username + "' was not saved (for some reason), it will be skipped");
					}
				}
				
				CRC32 crc = new CRC32();
				
				os.write(header);
				
				String vers = "" + passDBVersion;
				while(vers.length() < 4) {
					vers = "0" + vers;
				}
				vers = "v" + vers;
				
				DataOutputStream dos = new DataOutputStream(os);
				dos.write(vers.length() & 0xF);
				dos.write(vers.getBytes(StandardCharsets.US_ASCII));
				dos.writeInt(chunks.size());
				dos.writeInt(chunks.size());
				dos.writeInt(chunks.size());
				
				for(byte[] bts : chunks) {
					crc.reset();
					crc.update(bts);
					dos.writeInt((int)crc.getValue());
					dos.write(bts);
				}
				
				os.write(footerEOF);
				os.close();
				
				if(passwordDatabaseFileOld.exists()) {
					passwordDatabaseFileOld.delete();
				}
			}
		}catch(Throwable t) {
			if(passwordDatabaseFileOld.exists()) {
				passwordDatabaseFileOld.renameTo(passwordDatabaseFile);
			}
			if(t instanceof SyncException) {
				throw (SyncException)t;
			}else {
				throw new SyncException(t);
			}
		}
	}
	
	private static byte[] writeRow(PasswordEntry etr) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(PasswordEntry.ROW_LENGTH - 4);
			DataOutputStream dos = new DataOutputStream(bos);
			
			dos.writeLong(etr.createTime);
			dos.writeInt(etr.expiresAfter);
			dos.writeInt(etr.username.hashCode());
			
			int l = etr.username.length();
			dos.writeShort(l);
			for(int i = 0; i < 16; ++i) {
				if(i < l) {
					dos.writeChar(etr.username.charAt(i));
				}else {
					dos.writeChar(0);
				}
			}
			
			if(etr.salt.length != 9) {
				return null;
			}
			dos.write(etr.salt);

			if(etr.password.length != 20) {
				return null;
			}
			dos.write(etr.password);
			
			return bos.toByteArray();
		}catch(IOException ex) {
			return null;
		}
	}
	
	private static PasswordEntry readRow(byte[] etr) {
		try {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(etr));
			
			long createTime = dis.readLong();
			int expiresAfter = dis.readInt();
			int usernameHashCode = dis.readInt();
			int usernameLen = dis.readUnsignedShort();
			
			if(usernameLen > 16) {
				return null;
			}
			
			char[] un = new char[usernameLen];
			for(int i = 0; i < 16; ++i) {
				char ch = dis.readChar();
				if(i < un.length) {
					un[i] = ch;
				}
			}
			
			String username = new String(un);
			if(username.hashCode() != usernameHashCode) {
				return null;
			}
			
			byte[] salt = new byte[9];
			dis.read(salt);
			
			byte[] pass = new byte[20];
			dis.read(pass);
			
			return new PasswordEntry(username, salt, pass, createTime, expiresAfter);
		}catch(IOException ex) {
			return null;
		}
	}
	
	public static boolean delete(String username) {
		boolean flag = discardExpiredPasswords();
		boolean flag2 = true;
		synchronized(passwordEntries) {
			if(passwordEntries.remove(username) == null) {
				flag2 = false;
			}
		}
		if(flag || flag2) {
			try {
				syncDatabase();
			}catch(SyncException e) {
				Throwable t = e.getCause();
				System.err.println("Could not write passwords to disk!");
				if(t != null) {
					t.printStackTrace();
				}else {
					e.printStackTrace();
				}
			}
		}
		return flag2;
	}
	
	public static final byte[] eaglerSalt = {
			(byte)84,(byte)97,(byte)107,(byte)101,(byte)32,(byte)104,(byte)105,(byte)115,
			(byte)32,(byte)106,(byte)117,(byte)107,(byte)101,(byte)98,(byte)111,(byte)120,
			(byte)44,(byte)32,(byte)102,(byte)117,(byte)99,(byte)107,(byte)32,(byte)116,
			(byte)104,(byte)105,(byte)115,(byte)32,(byte)107,(byte)105,(byte)100,(byte)46
	};
	
	public static final SecureRandom rand = new SecureRandom();
	
	public static void create(String username, String password, int expiresAfter) {
		discardExpiredPasswords();
		
		if(expiresAfter < -1) {
			expiresAfter = -1;
		}
		
		byte[] salt = new byte[9];
		synchronized(rand) {
			rand.nextBytes(salt);
		}
		
		SHA1Digest dg = new SHA1Digest();
		dg.update(eaglerSalt, 0, eaglerSalt.length);
		byte[] p = password.getBytes(StandardCharsets.UTF_8);
		dg.update(p, 0, p.length);
		byte[] pass = new byte[20];
		dg.doFinal(pass, 0);

		dg.update(eaglerSalt, 0, eaglerSalt.length);
		dg.update(pass, 0, pass.length);
		dg.update(salt, 0, salt.length);
		dg.doFinal(pass, 0);
		
		synchronized(passwordEntries) {
			passwordEntries.put(username, new PasswordEntry(username, salt, pass, System.currentTimeMillis(), expiresAfter));
		}
		
		try {
			syncDatabase();
		}catch(SyncException e) {
			Throwable t = e.getCause();
			System.err.println("Could not write passwords to disk!");
			if(t != null) {
				t.printStackTrace();
			}else {
				e.printStackTrace();
			}
		}
	}
	
	public static int changeExpires(String username, int expiresAfter) {
		PasswordEntry et;
		synchronized(passwordEntries) {
			et = passwordEntries.get(username.toLowerCase());
		}
		if(et != null) {
			if(expiresAfter == -1) {
				expiresAfter = et.expiresAfter;
			}
			synchronized(passwordEntries) {
				passwordEntries.put(et.username, new PasswordEntry(et.username, et.salt, et.password, System.currentTimeMillis(), expiresAfter == -2 ? -1 : expiresAfter));
			}
		}
		if(discardExpiredPasswords() || et != null) {
			try {
				syncDatabase();
			}catch(SyncException e) {
				Throwable t = e.getCause();
				System.err.println("Could not write passwords to disk!");
				if(t != null) {
					t.printStackTrace();
				}else {
					e.printStackTrace();
				}
			}
		}
		return et == null ? -1 : (expiresAfter == -1 ? -2 : expiresAfter);
	}
	
	private static boolean discardExpiredPasswords() {
		boolean flag = false;
		boolean removePasswordsWithoutExpire = !EaglercraftServer.config.allowPasswordsWithoutExpire();
		synchronized(passwordEntries) {
			Iterator<PasswordEntry> itr = passwordEntries.values().iterator();
			while(itr.hasNext()) {
				PasswordEntry et = itr.next();
				if(et.secondsRemaining() <= 0 || (removePasswordsWithoutExpire && et.expiresAfter == -1)) {
					flag = true;
					itr.remove();
				}
			}
		}
		return flag;
	}
	
	public static Collection<PasswordEntry> getPasswordList() {
		if(discardExpiredPasswords()) {
			try {
				syncDatabase();
			}catch(SyncException e) {
				Throwable t = e.getCause();
				System.err.println("Could not write passwords to disk!");
				if(t != null) {
					t.printStackTrace();
				}else {
					e.printStackTrace();
				}
			}
		}
		return passwordEntries.values();
	}

}
