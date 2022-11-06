package net.lax1dude.eaglercraft.beta;

import java.util.Collection;
import java.util.function.Consumer;

import net.lax1dude.eaglercraft.EaglerAdapter;
import net.lax1dude.eaglercraft.adapter.EaglerAdapterImpl2.FileEntry;
import net.minecraft.src.IProgressUpdate;

public class FilesystemUtils {
	
	public static void recursiveDeleteDirectory(String dir) {
		Collection<FileEntry> lst = EaglerAdapter.listFiles(dir, true, true);
		for(FileEntry t : lst) {
			if(!t.isDirectory) {
				EaglerAdapter.deleteFile(t.path);
			}
		}
		for(FileEntry t : lst) {
			if(t.isDirectory) {
				EaglerAdapter.deleteFile(t.path);
			}
		}
		EaglerAdapter.deleteFile(dir);
	}

	public static void recursiveDeleteDirectoryWithProgress(String dir, String title, String subText, IProgressUpdate progress) {
		progress.displayLoadingString(title, "(please wait)");
		Collection<FileEntry> lst = EaglerAdapter.listFiles(dir, true, true);
		int totalDeleted = 0;
		int lastTotalDeleted = 0;
		for(FileEntry t : lst) {
			if(!t.isDirectory) {
				EaglerAdapter.deleteFile(t.path);
				++totalDeleted;
				if(totalDeleted - lastTotalDeleted >= 10) {
					lastTotalDeleted = totalDeleted;
					progress.displayLoadingString(title, subText.replace("%i", "" + totalDeleted));
				}
			}
		}
		for(FileEntry t : lst) {
			if(t.isDirectory) {
				EaglerAdapter.deleteFile(t.path);
			}
		}
		EaglerAdapter.deleteFile(dir);
	}

}
