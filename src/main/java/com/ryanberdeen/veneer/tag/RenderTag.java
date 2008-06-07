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

import javax.servlet.jsp.JspException;

/** Renders a view. The view is usually a partial, specified using {@link #setPartial(String)},
 * but can also be a normal view, specified by {@link #setName(String)}. The
 * view is rendered in a local scope. The tag's body is evaluated in the same
 * scope before the view is rendered, but its output is discarded.
 * 
 * @author Ryan Berdeen
 *
 */
public class RenderTag extends ScopedTag {
	private String name;
	private String partial;
	
	/** Sets the name of the view to render.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Sets the name of the partial to render.
	 */
	public void setPartial(String partial) {
		this.partial = partial;
	}
	
	@Override
	public void doScoped() throws Exception {
		renderBody();
		
		String value;
		
		if (name != null) {
			value = getContext().render(getResponse(), name);
		}
		else if(partial != null) {
			value = getContext().renderPartial(getResponse(), partial);
		}
		else if(partial != null) {
			value = getContext().renderPartial(getResponse(), partial);
		}
		else {
			throw new JspException("Either the name attribute or the partial attribute must be specified");
		}
		
		if (value != null) {
			getJspContext().getOut().write(value);
		}
	}
}
