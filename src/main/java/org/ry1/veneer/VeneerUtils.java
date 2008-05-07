/* $Id$ */

package org.ry1.veneer;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class VeneerUtils {
	
	public static Object getAttribute(HttpServletRequest request, String name) {
		return getContext(request).getAttribute(name);
	}
	
	public static void setAttribute(HttpServletRequest request, String name, Object value) {
		getContext(request).setAttribute(name, value);
	}
	
	public static String render(HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		VeneerView view = (VeneerView) getResolver(request).resolveViewName(name, null);
		return getContext(request).render(request, response, view.getPath());
	}
	
	public static String renderParial(HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		Context context = getContext(request);
		return context.render(request, response, getResolver(request).resolvePartialPath(name, context.getCurrentUri()));
	}
	
	@SuppressWarnings("unchecked")
	public static VeneerViewResolver getResolver(ServletRequest request) {
		WebApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
		Map<String, VeneerViewResolver> resolvers = context.getBeansOfType(VeneerViewResolver.class);
		
		if (resolvers.size() > 0) {
			return resolvers.values().iterator().next();
		}
		else {
			return null;
		}
	}
	
	public static Context getContext(HttpServletRequest request) {
		Context context = (Context) request.getAttribute(VeneerView.CONTEXT_ATTRIBUTE_NAME);
		
		if (context == null) {
			context = new Context(request);
			request.setAttribute(VeneerView.CONTEXT_ATTRIBUTE_NAME, context);
		}
		
		return context;
	}
}
