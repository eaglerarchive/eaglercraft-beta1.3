package net.lax1dude.eaglercraft.beta.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.util.config.Configuration;

public class EaglercraftConfig {

	private Configuration yamlFile = null;

	private boolean enablePasswordLoginValue = true;
	private boolean requirePasswordLoginValue = true;
	private boolean allowSelfRegistrationValue = false;
	private boolean allowSelfRegistrationWithoutExpireValue = false;
	private boolean allowSelfChangePasswordValue = true;
	private boolean allowSelfRenewPasswordValue = true;
	private boolean allowSelfRenewPasswordWithTimeValue = false;
	private boolean allowSelfDeletePasswordValue = false;
	private int defaultPasswordExpireTimeValue = 7 * 24 * 60 * 60;
	private int maximumPasswordExpireTimeValue = 21 * 24 * 60 * 60;
	private boolean allowPasswordsWithoutExpireValue = true;
	
	private static final File configFile = new File("eagler.yml");
	
	public boolean enablePasswordLogin() {
		return enablePasswordLoginValue;
	}
	
	public boolean requirePasswordLogin() {
		return requirePasswordLoginValue;
	}
	
	public boolean allowSelfRegistration() {
		return allowSelfRegistrationValue;
	}
	
	public boolean allowSelfRegistrationWithoutExpire() {
		return allowSelfRegistrationWithoutExpireValue;
	}
	
	public boolean allowSelfChangePassword() {
		return allowSelfChangePasswordValue;
	}
	
	public boolean allowSelfRenewPassword() {
		return allowSelfRenewPasswordValue;
	}
	
	public boolean allowSelfRenewPasswordWithTime() {
		return allowSelfRenewPasswordWithTimeValue;
	}
	
	public boolean allowSelfDeletePassword() {
		return allowSelfDeletePasswordValue;
	}
	
	public int defaultPasswordExpireTime() {
		return defaultPasswordExpireTimeValue;
	}
	
	public int maximumPasswordExpireTime() {
		return maximumPasswordExpireTimeValue;
	}
	
	public boolean allowPasswordsWithoutExpire() {
		return allowPasswordsWithoutExpireValue;
	}
	
	public void reload() {
		if(yamlFile == null) {
			if(!configFile.exists()) {
				InputStream is = EaglercraftConfig.class.getResourceAsStream("/default_eagler_config.yml");
				if(is == null) {
					System.err.println("The file '/default_eagler_config.yml' could not be located in this jar!");
					return;
				}
				try(FileOutputStream os = new FileOutputStream(configFile)) {
					byte[] buffer = new byte[1024];
					int i;
					while((i = is.read(buffer)) > 0) {
						os.write(buffer, 0, i);
					}
					os.close();
				}catch(IOException ex) {
					System.err.println("The default config fould not be written! (writing '" + configFile.getName() + "')");
					ex.printStackTrace();
				}
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			yamlFile = new Configuration(configFile);
			yamlFile.load();
			enablePasswordLoginValue = yamlFile.getBoolean("enable_password_logins", true);
		}else {
			yamlFile.load();
		}

		if(enablePasswordLoginValue != yamlFile.getBoolean("enable_password_logins", true)) {
			System.err.println("Please restart the server when you change the 'enable_password_logins' option ");
		}
		
		requirePasswordLoginValue = yamlFile.getBoolean("only_allow_registered_users_to_login", true);
		allowPasswordsWithoutExpireValue = yamlFile.getBoolean("allow_passwords_without_expiration", true);
		allowSelfRegistrationValue = yamlFile.getBoolean("allow_self_registration", false);
		allowSelfRegistrationWithoutExpireValue = yamlFile.getBoolean("allow_self_registration_without_expiration", false);
		allowSelfChangePasswordValue = yamlFile.getBoolean("allow_self_change_password", true);
		allowSelfRenewPasswordValue = yamlFile.getBoolean("allow_self_renew_password", true);
		allowSelfRenewPasswordWithTimeValue = yamlFile.getBoolean("allow_self_change_password_expiration", false);
		allowSelfDeletePasswordValue = yamlFile.getBoolean("allow_self_delete_password", false);
		defaultPasswordExpireTimeValue = yamlFile.getInt("default_password_expire_time_seconds", 604800); // 1 week
		maximumPasswordExpireTimeValue = yamlFile.getInt("maximum_password_expire_time_seconds", 1814400); // 1 week
	}

}
