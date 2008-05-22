/*
 * Copyright 2008 Ryan Berdeen.
 *
 * This file is part of Veneer.
 *
 * Veneer is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Veneer is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Veneer.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ryanberdeen.veneer;

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
