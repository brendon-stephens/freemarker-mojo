package com.github.brendonstephens.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.maven.plugin.MojoFailureException;

public final class ResourceUtils {

	private ResourceUtils() {
		// prevent instantiation on utility class
	}

	/**
	 * Loads a file and returns it's contents.
	 * 
	 * @param file
	 * @return
	 * @throws MojoFailureException 
	 */
	public static String readAllFileBytes(final File file) throws MojoFailureException {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		} catch (IOException e) {
			throw new MojoFailureException("Could not read file '" + file.getAbsolutePath() + "'");
		}
		
		return content;
	}
	
	/**
	 * Returns a file object relative to the Maven Mojo configuration.
	 *  
	 * @param path
	 * @return File
	 */
	public static File getProjectResource(final String srcDir, final String path) {
		return new File(srcDir, path); 
	}
}
