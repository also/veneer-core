/* $Id$ */

package org.ry1.veneer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VeneerSupport {
	
	public static Object getAttribute(ServletContext context, HttpServletRequest request, String name) {
		return getContext(context, request).getAttribute(name);
	}
	
	public static void setAttribute(ServletContext context, HttpServletRequest request, String name, Object value) {
		getContext(context, request).setAttribute(name, value);
	}
	
	public static String render(ServletContext context, HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		RenderContext renderContext = getContext(context, request);
		return renderContext.render(request, response, renderContext.resolveViewPath(name));
	}
	
	public static String renderPartial(ServletContext context, HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		RenderContext renderContext = getContext(context, request);
		return renderContext.render(request, response, renderContext.resolvePartialPath(name, renderContext.getCurrentUri()));
	}
	
	@SuppressWarnings("unchecked")
	/*public static VeneerViewResolver getResolver(ServletRequest request) {
		WebApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
		Map<String, VeneerViewResolver> resolvers = context.getBeansOfType(VeneerViewResolver.class);
		
		if (resolvers.size() > 0) {
			return resolvers.values().iterator().next();
		}
		else {
			return null;
		}
	}*/
	
	public static RenderContext getContext(ServletContext context, HttpServletRequest request) {
		RenderContext renderContext = (RenderContext) request.getAttribute(RenderContext.ATTRIBUTE_NAME);
		
		if (renderContext == null) {
			renderContext = new RenderContext(context, request);
			request.setAttribute(RenderContext.ATTRIBUTE_NAME, renderContext);
		}
		
		return renderContext;
	}
	
	public static Configuration getConfiguration(ServletContext context) {
		Configuration configuration = (Configuration) context.getAttribute(Configuration.ATTRIBUTE_NAME);
		
		if (configuration == null) {
			configuration = new Configuration();
			setConfiguration(context, configuration);
		}
		
		return configuration;
	}
	
	public static void setConfiguration(ServletContext context, Configuration configuration) {
		context.setAttribute(Configuration.ATTRIBUTE_NAME, configuration);
	}
}
