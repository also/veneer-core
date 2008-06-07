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

/** Support methods for using Veneer.
 * @author Ryan Berdeen
 *
 */
public class VeneerSupport {

	/** Returns the {@link RenderContext} for the request. If none exists, one
	 * is created and stored as a request attribute.
	 * 
	 * @see RenderContext#ATTRIBUTE_NAME
	 */
	public static RenderContext getContext(ServletContext context, HttpServletRequest request) {
		RenderContext renderContext = (RenderContext) request.getAttribute(RenderContext.ATTRIBUTE_NAME);

		if (renderContext == null) {
			renderContext = new RenderContext(getConfiguration(context), request);
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
