package org.ry1.veneer;

public class Configuration {
	public static final String ATTRIBUTE_NAME = Configuration.class.getName() + ".configuration";
	
	private String prefix = "/WEB-INF/jsp/";
	private String suffix = ".jsp";
	private String defaultTemplateName = null;
	private String outerScopeName = "main";
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public String getDefaultTemplateName() {
		return defaultTemplateName;
	}
	
	public void setDefaultTemplateName(String defaultTemplateName) {
		this.defaultTemplateName = defaultTemplateName;
	}
	
	public String getOuterScopeName() {
		return outerScopeName;
	}
	
	public void setOuterScopeName(String outerScopeName) {
		this.outerScopeName = outerScopeName;
	}

}
