package com.m1racle.yuedong.cache;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Cache Data Clean Manager
 * 
 */
public class DataCleanManager {
	
	/**
	 * Clean internal cache
	 * directory: /data/data/com.xxx.xxx/cache
	 */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * Clean all databases
	 * directory: /data/data/com.xxx.xxx/databases
	 */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/databases"));
	}

	/**
	 * Clean SharedPreference
	 * directory: /data/data/com.xxx.xxx/shared_prefs
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/shared_prefs"));
	}
	
	/**
	 * Clean database cache by name
	 * @param dbName database name
	 */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/**
	 * Clean files
	 * directory: /data/data/com.xxx.xxx/files
	 */
	public static void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
     * Clean external cache in device or sdcard
	 * directory: /mnt/sdcard/android/data/com.xxx.xxx/cache
	 */
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}

	/**
	 * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
	 */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}
	
	/**
	 * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
	 */
	public static void cleanCustomCache(File file) {
		deleteFilesByDirectory(file);
	}

	/**
	 * Remove(destroy) all data of this app
	 * WARNING: this will DESTROY ALL OF YOUR DATA!
	 */
	public static void cleanApplicationData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/**
	 * Only delete a certain file in a directory
     * this method do not delete directory
	 */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File child : directory.listFiles()) {
				if (child.isDirectory()) {
					deleteFilesByDirectory(child);
				} 
				child.delete();
			}
		}
	}
}
