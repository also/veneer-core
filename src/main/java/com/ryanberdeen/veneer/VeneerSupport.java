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
	
	/** Gets the attribute from the current scope.
	 *
	 * @return the attribute value, or <code>null</code> if none
	 */
	public static Object getAttribute(ServletContext context, HttpServletRequest request, String name) {
		return getContext(context, request).getAttribute(name);
	}
	
	/** Sets the value of the attribute in the current scope.
	 */
	public static void setAttribute(ServletContext context, HttpServletRequest request, String name, Object value) {
		getContext(context, request).setAttribute(name, value);
	}
	
	/** Renders a named view.
	 */
	public static String render(ServletContext context, HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		return getContext(context, request).render(response, name);
	}
	
	/** Renders a named partial view.
	 */
	public static String renderPartial(ServletContext context, HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		return getContext(context, request).renderPartial(response, name);
	}

	/** Returns the {@link RenderContext} for the request. If none exists, it is created.
	 */
	public static RenderContext getContext(ServletContext context, HttpServletRequest request) {
		RenderContext renderContext = (RenderContext) request.getAttribute(RenderContext.ATTRIBUTE_NAME);

		if (renderContext == null) {
			renderContext = new RenderContext(context, request);
			request.setAttribute(RenderContext.ATTRIBUTE_NAME, renderContext);
		}

		return renderContext;
	}

	/** Returns the application's {@link Configuration}. If it doesn't exist, it is created.
	 *  Configuration options are loaded from the <code>web.xml</code> init parameters, in
	 *  the form of <code>com.ryanberdeen.veneer.Configuration.&lt;parameterName&gt;</code>.
	 *  See {@link Configuration} for available parameters.
	 */
	public static Configuration getConfiguration(ServletContext context) {
		Configuration configuration = (Configuration) context.getAttribute(Configuration.ATTRIBUTE_NAME);

		if (configuration == null) {
			configuration = new Configuration();

			String prefix = context.getInitParameter(Configuration.class.getCanonicalName() + ".prefix");
			if (prefix != null) {
				configuration.setPrefix(prefix);
			}

			String suffix = context.getInitParameter(Configuration.class.getCanonicalName() + ".suffix");
			if (suffix != null) {
				configuration.setSuffix(suffix);
			}

			String partialPrefix = context.getInitParameter(Configuration.class.getCanonicalName() + ".partialPrefix");
			if (suffix != null) {
				configuration.setPartialPrefix(partialPrefix);
			}

			String defaultTemplateName = context.getInitParameter(Configuration.class.getCanonicalName() + ".defaultTemplateName");
			if (defaultTemplateName != null) {
				configuration.setDefaultTemplateName(defaultTemplateName);
			}

			String ignoreInvalidPaths = context.getInitParameter(Configuration.class.getCanonicalName() + ".ignoreInvalidPaths");
			if ("true".equals(ignoreInvalidPaths)) {
				configuration.setIgnoreInvalidPaths(true);
			}
			setConfiguration(context, configuration);
		}

		return configuration;
	}

	/** Sets the application's configuration.
	 */
	public static void setConfiguration(ServletContext context, Configuration configuration) {
		context.setAttribute(Configuration.ATTRIBUTE_NAME, configuration);
	}
}
