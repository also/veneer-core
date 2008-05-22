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

import java.io.IOException;

import javax.servlet.jsp.JspException;


public class TemplateTag extends VeneerTagSupport {
	private String name;
	private boolean skip = false;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		String templateName = skip ? null : name;
		getContext().setTemplateName(templateName);
	}
}
