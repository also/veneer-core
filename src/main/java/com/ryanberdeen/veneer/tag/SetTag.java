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

package com.ryanberdeen.veneer.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

/** Sets an attribute in the named scope.
 * 
 * <p>The body of the tag is rendered before the value is set. To set an
 * attribute whose body is evaluated when it is used, use {@link WithTag}.</p>
 * 
 * <p>The default scope is the current scope.</p>
 * 
 * @author Ryan Berdeen
 *
 */
public class SetTag extends VeneerTagSupport {
	private String scopeName;
	private String attributeName;
	private Object value;
	
	/** Sets the name of the scope in which to set the attribute.
	 */
	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}
	
	/** Sets the name of the attribute to set.
	 */
	public void setName(String name) {
		this.attributeName = name;
	}
	
	/** Sets the value of the attribute.
	 * 
	 * <p>If the value is set, the tag's body will be ignored.</p>
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		setAttribute(scopeName, attributeName, getValue());
	}
	
	/** Returns the value to set. This method can be overridden by subclasses
	 * to set a custom value.
	 */
	protected Object getValue() throws JspException, IOException {
		if (value == null && getJspBody() != null) {
			return renderBody();
		}
		else {
			return value;
		}
	}
}
