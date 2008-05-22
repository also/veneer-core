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

package org.ry1.veneer.tag;

import org.ry1.veneer.VeneerSupport;

public class RenderTag extends ScopedTag {
	private String name;
	private String partial;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPartial(String partial) {
		this.partial = partial;
	}
	
	@Override
	public void doScoped() throws Exception {
		if (getJspBody() != null) {
			getBody();
		}
		
		String value;
		if (name != null) {
			value = VeneerSupport.render(getPageContext().getServletContext(), getRequest(), getResponse(), name);
		}
		else {
			value = VeneerSupport.renderPartial(getPageContext().getServletContext(), getRequest(), getResponse(), partial);
		}
		// TODO check for null partial
		
		if (value != null) {
			getJspContext().getOut().write(value);
		}
	}
}
