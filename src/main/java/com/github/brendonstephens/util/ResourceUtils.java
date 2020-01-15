package com.github.brendonstephens.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class ResourceUtils {

	private ResourceUtils() {
		// prevent instantiation on utility class
	}

	/**
	 * Loads a file and returns it's contents.
	 * 
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String readAllFileBytes(final File file) throws IOException {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		} catch (IOException e) {
			throw e;
		}
		
		return content;
	}
}
