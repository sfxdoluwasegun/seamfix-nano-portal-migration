package com.nano.portal.email;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

/**
 * Initializes the FreeMarker configuration and transforms the templates using {@link StringWriter}.
 * 
 * @author segz
 *
 */

@Singleton
public class EmailTemplate {

	private Logger log = LoggerFactory.getLogger(getClass());

	/** The template location. */
	private String templateLocation = "com/nano/portal/email/templates";

	/** The configuration. */
	private Configuration configuration ;

	/**
	 * Initializes the FreeMarker mail template.
	 */
	@PostConstruct
	public void init(){
		Version incompatibleImprovements = Configuration.VERSION_2_3_23; 
		configuration = new Configuration(incompatibleImprovements);
		configuration.setObjectWrapper(new DefaultObjectWrapper(incompatibleImprovements));

		try {
			configuration.setDirectoryForTemplateLoading(new File(templateLocation));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}

	/**
	 * Transforms the FreeMarker template into a string.
	 *
	 * @param templateModel the template model
	 * @param templateFilename the template filename
	 * @return the string writer
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws TemplateException the template exception
	 */
	public StringWriter transform(Map<String, Object> templateModel, 
			String templateFilename) throws IOException, TemplateException {

		addDefaults(templateModel);
		Template template = configuration.getTemplate(templateFilename);

		StringWriter stringWriter = new StringWriter();
		template.process(templateModel, stringWriter);

		return stringWriter;
	}

	/**
	 * Adds the default parameters to the template.
	 *
	 * @param templateModel the template model
	 */
	public void addDefaults(Map<String, Object> templateModel){

		try {
			templateModel.put("portalURL", "http://mitmoh.com");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}

}