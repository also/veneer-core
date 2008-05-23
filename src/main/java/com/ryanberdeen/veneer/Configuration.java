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

public class Configuration {
	public static final String ATTRIBUTE_NAME = Configuration.class.getName() + ".configuration";
	
	private String prefix = "/WEB-INF/jsp/";
	private String suffix = ".jsp";
	private String partialPrefix = "_";
	private String defaultTemplateName = null;
	private String outerScopeName = "main";
	private boolean ignoreInvalidPaths;
	
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

	public String getPartialPrefix() {
		return partialPrefix;
	}

	public void setPartialPrefix(String partialPrefix) {
		this.partialPrefix = partialPrefix;
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

	public boolean getIgnoreInvalidPaths() {
		return ignoreInvalidPaths;
	}

	public void setIgnoreInvalidPaths(boolean ignoreInvalidPaths) {
		this.ignoreInvalidPaths = ignoreInvalidPaths;
	}

}
