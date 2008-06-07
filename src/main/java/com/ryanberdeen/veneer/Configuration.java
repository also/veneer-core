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

/** Stores Veneer configuration options.
 * 
 * @author Ryan Berdeen
 *
 */
public class Configuration {
	/** Attribute name used to store the configuration in the servlet context.
	 */
	public static final String ATTRIBUTE_NAME = Configuration.class.getName() + ".configuration";
	
	private String prefix = "/WEB-INF/jsp/";
	private String suffix = ".jsp";
	private String partialPrefix = "_";
	private String defaultTemplateName = null;
	private String outerScopeName = "main";
	private boolean ignoreInvalidPaths;
	
	/** Returns the prefix.
	 * 
	 * @see #setPrefix(String)
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/** Sets the prefix used to resolve view names to URIs.
	 * 
	 * <p>The default prefix is <code>/WEB-INF/jsp/</code></p>
	 * 
	 * @see RenderContext#resolveViewPath(String)
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/** Returns the suffix.
	 * 
	 * @see #setSuffix(String)
	 */
	public String getSuffix() {
		return suffix;
	}

	/** Sets the suffix used to resolve view names to URIs.
	 * 
	 * <p>The default suffix is <code>.jsp</code></p>
	 * 
	 * @see RenderContext#resolveViewPath(String)
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/** Returns the partial prefix.
	 * 
	 * @see #setPartialPrefix(String)
	 */
	public String getPartialPrefix() {
		return partialPrefix;
	}

	/** Sets the partial prefix used to resolve partial names to URIs.
	 * 
	 * <p>The default partial prefix is <code>_</code></p>
	 */
	public void setPartialPrefix(String partialPrefix) {
		this.partialPrefix = partialPrefix;
	}

	/** Returns the default template name.
	 * 
	 * @see #setDefaultTemplateName(String)
	 */
	public String getDefaultTemplateName() {
		return defaultTemplateName;
	}

	/** Sets the default template name.
	 * 
	 * @see RenderContext#renderMain(javax.servlet.http.HttpServletResponse, String)
	 */
	public void setDefaultTemplateName(String defaultTemplateName) {
		this.defaultTemplateName = defaultTemplateName;
	}

	/** Returns the name of the outer scope.
	 * 
	 * @see #setOuterScopeName(String)
	 */
	public String getOuterScopeName() {
		return outerScopeName;
	}

	/** Sets the name of the outer scope.
	 * 
	 * <p>The outer scope is the first scope created by the {@link RenderContext}.</p>
	 */
	public void setOuterScopeName(String outerScopeName) {
		this.outerScopeName = outerScopeName;
	}

	/** Returns whether or not to ignore invalid paths.
	 * 
	 * @see #setIgnoreInvalidPaths(boolean)
	 */
	public boolean getIgnoreInvalidPaths() {
		return ignoreInvalidPaths;
	}

	/** Sets whether or not to ignore invalid paths.
	 * 
	 * <p>By default, if rendering a view results in a 404 error, an exception
	 * will be thrown. Setting this to <code>true</code> suppresses the
	 * exception.</p>
	 * 
	 * <p>The default is <code>false</code>.</p>
	 * 
	 * @see RenderContext#renderUri(javax.servlet.http.HttpServletResponse, String)
	 */
	public void setIgnoreInvalidPaths(boolean ignoreInvalidPaths) {
		this.ignoreInvalidPaths = ignoreInvalidPaths;
	}

}
