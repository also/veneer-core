/* $Id$ */

package org.ry1.veneer;

import java.io.FileNotFoundException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RenderContext {
	public static final String ATTRIBUTE_NAME = RenderContext.class.getName() + ".renderContext";
	public static final String EXPOSED_ATTRIBUTES_ATTRIBUTE_NAME = "view";
	
	public static final String TEMPLATE_NAME_ATTRIBUTE_NAME = "template";
	
	public static final String CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME = "contentForLayout";
	
	private static final String URI_ATTRIBUTE_NAME = "viewUri";
	
	private ServletContext context;
	private Configuration config;
	
	private Scope currentScope;
	private HttpServletRequest request;

	public RenderContext(ServletContext context, HttpServletRequest request) {
		this.context = context;
		this.request = request;
		
		pushScope(getConfiguration().getOuterScopeName());
	}
	
	public void setConfiguration(Configuration config) {
		this.config = config;
	}
	
	public Configuration getConfiguration() {
		if (config == null) {
			config = VeneerSupport.getConfiguration(context);
		}
		
		return config;
	}
	
	public String resolveViewPath(String viewName) {
		return getConfiguration().getPrefix() + viewName + getConfiguration().getSuffix();
	}
	
	public void pushScope(String name) {
		currentScope = new Scope(currentScope, name);
		
		setTemplateName(null);
		
		request.setAttribute(EXPOSED_ATTRIBUTES_ATTRIBUTE_NAME, currentScope.getLocalAttributes());
	}
	
	public void popScope() {
		currentScope = currentScope.getParentState();
		
		request.setAttribute(EXPOSED_ATTRIBUTES_ATTRIBUTE_NAME, currentScope.getLocalAttributes());
	}
	
	public void setTemplateName(String templateName) {
		setAttribute(TEMPLATE_NAME_ATTRIBUTE_NAME, templateName);
	}
	
	public String getTemplateName() {
		return (String) getAttribute(TEMPLATE_NAME_ATTRIBUTE_NAME);
	}
	
	public void setAttribute(String name, Object value) {
		getCurrentScope().setAttribute(null, name, value);
	}
	
	public void setAttribute(String scopeName, String attributeName, Object value) {
		getCurrentScope().setAttribute(attributeName, value);
	}
	
	public Object getAttribute(String name) {
		return getCurrentScope().getAttribute(name);
	}
	
	private Scope getCurrentScope() {
		if (currentScope == null) {
			// TODO exception type
			throw new RuntimeException("No view scope available");
		}
		
		return currentScope;
	}
	
	public String getCurrentUri() {
		return (String) getAttribute(URI_ATTRIBUTE_NAME);
	}
	
	public String render(HttpServletRequest request, HttpServletResponse response, String uri) throws Exception {
		VeneerHttpServletResponse cachingResponse = new VeneerHttpServletResponse(response);
		setAttribute(URI_ATTRIBUTE_NAME, uri);
		
		request.getRequestDispatcher(uri).include(request, cachingResponse);
		// TODO show correct uri if partial is not found
		if (cachingResponse.isNotFound()) {
			throw new FileNotFoundException(uri);
		}
		
		return applyTemplate(response, cachingResponse.getValue());
	}
	
	public String applyTemplate(HttpServletResponse response, String content) throws Exception {
		String templateName = (String) getAttribute(TEMPLATE_NAME_ATTRIBUTE_NAME);
		if (templateName != null) {
			setAttribute(CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME, content);
			setTemplateName(null);
			return VeneerSupport.render(context, request, response, templateName);
		}
		else {
			return content;
		}
	}
	
	public String resolvePartialPath(String name, String relative) {
		String prefix = getConfiguration().getPrefix();
		String suffix = getConfiguration().getSuffix();
		
		if (name.indexOf('/') == 0) {
			return resolvePartialPath(name.substring(1), prefix);
		}
		else if (name.startsWith("../")) {
			return resolvePartialPath(name.substring(3), relative.substring(0, relative.lastIndexOf('/')));
		}
		else {
			int nameSlashIndex = name.lastIndexOf('/');
			if (nameSlashIndex != -1) {
				name = name.substring(0, nameSlashIndex + 1) + '_' + name.substring(nameSlashIndex + 1);
			}
			else {
				name = '_' + name;
			}
			
			int relativeSlashIndex = relative.lastIndexOf('/');
			if (relativeSlashIndex != -1) {
				return relative.substring(0, relativeSlashIndex + 1) + name + suffix;
			}
			else {
				return prefix + name + suffix;
			}
		}
	}
}
