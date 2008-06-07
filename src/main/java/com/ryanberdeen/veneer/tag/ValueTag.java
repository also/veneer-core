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

import static com.ryanberdeen.veneer.RenderContext.CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;


public class ValueTag extends ScopedTag {
	private String name;
	private boolean optional;
	private Object value;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	
	@Override
	protected boolean isScoped() {
		if (name == null) {
			name = CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME;
		}
		
		value = getAttribute(name);
		if (value == null) {
			if (!optional) {
				throw new MissingValueException("No value for '" + name + "'");
			}
		}
		
		// no need to scope for simple values
		return value instanceof JspFragment;
	}
	
	@Override
	public void doScoped() throws JspException, IOException {
		if (value != null) {
			if (value instanceof JspFragment) {
				renderBody();
				value = renderFragment((JspFragment) value);
			}
			
			getJspContext().getOut().write(value.toString());
		}
	}
}
