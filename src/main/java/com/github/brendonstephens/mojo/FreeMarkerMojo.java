package com.github.brendonstephens.mojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.CharEncoding;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.github.brendonstephens.util.ResourceUtils;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * Simple Maven Mojo to wrap and call the Apache FreeMarker template engine.
 * 
 * @author Brendon Stephens
 * @since 2020-01-09
 */
@Mojo(name = "apply-freemarker-template")
public class FreeMarkerMojo extends AbstractMojo {

	/**
	 * The source directory containing the input files.
	 */
	@Parameter(required = true, readonly = true)
	private File srcDir;
	
	/**
	 * The template file to be processed.
	 */
	@Parameter(alias = "template", required = true, readonly = true)
	private String templateName;
	
	/**
	 * The location of the output file.
	 */
	@Parameter(required = true, readonly = true)
	private File outputFile;
	
	/**
	 * Data models to be used during template processing.
	 */
	@Parameter(required = false, readonly = true)
	private HashMap<String, String> dataModels;
	
	/**
	 * Raw variables to be used during template processing.
	 */
	@Parameter(required = false, readonly = true)
	private HashMap<String, String> rawInputs;
			
	/**
	 * The Apache FreeMarker version to use during processing.
	 */
    private final Version freemarkerVersion = Configuration.VERSION_2_3_28;

	@Override
	public void execute() throws MojoFailureException {
        
		getLog().info("Executing the apply-freemarker-template plugin.");
		
		Configuration configuration = new Configuration(freemarkerVersion);
		configuration.setLocale(Locale.UK);
		configuration.setDefaultEncoding(CharEncoding.UTF_8);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		try {
			configuration.setTemplateLoader(new FileTemplateLoader(srcDir));
		} catch (IOException e) {
			throw new MojoFailureException(String.format("Error reading source folder '%s'", srcDir.getAbsolutePath()), e);
		}

		HashMap<String, Object> input = new HashMap<String, Object>();

		if(dataModels != null) {
			for(String key : dataModels.keySet()) {
				final File model = new File(srcDir.getAbsolutePath(), dataModels.get(key));
				try {
					input.put(key, ResourceUtils.readAllFileBytes(model));
				} catch (Exception e) {
					throw new MojoFailureException(String.format("There was an error loading the file '%s'"), e);

				}
			}
		}

		if(rawInputs != null) {
			input.putAll(rawInputs);
		}

		try {
			outputFile.getParentFile().mkdirs();
			
			Template template = configuration.getTemplate(templateName);
			template.process(input, new FileWriter(outputFile));
		} catch (TemplateException | IOException e) {
			throw new MojoFailureException(String.format("There was an error processing the template file '%s'"), e);
		}
	}
}
