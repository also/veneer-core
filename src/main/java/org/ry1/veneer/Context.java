/* $Id$ */

package org.ry1.veneer;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Context {
	public static final String EXPOSED_ATTRIBUTES_ATTRIBUTE_NAME = "view";
	
	public static final String TEMPLATE_NAME_ATTRIBUTE_NAME = "template";
	
	public static final String CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME = "contentForLayout";
	
	private static final String URI_ATTRIBUTE_NAME = "viewUri";
	
	private Scope currentScope;
	private HttpServletRequest request;

	public Context(HttpServletRequest request) {
		this.request = request;
		
		pushScope();
	}
	
	public void pushScope() {
		currentScope = new Scope(currentScope);
		
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
		getCurrentScope().setAttribute(name, value);
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
		
		String templateName = (String) getAttribute(TEMPLATE_NAME_ATTRIBUTE_NAME);
		if (templateName != null) {
			setAttribute(CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME, cachingResponse.getValue());
			setTemplateName(null);
			return VeneerUtils.render(request, response, templateName);
		}
		else {
			return cachingResponse.getValue();
			
		}
	}
}
