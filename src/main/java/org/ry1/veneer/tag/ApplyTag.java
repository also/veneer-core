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

import org.ry1.veneer.RenderContext;


public class ApplyTag extends ScopedTag {
	private String templateName;
	
	public void setTemplate(String templateName) {
		this.templateName = templateName;
	}
	
	@Override
	public void doScoped() throws Exception {
		RenderContext renderContext = getContext();
		
		if (templateName == null) {
			 templateName = renderContext.getConfiguration().getDefaultTemplateName();
		}
		
		renderContext.setTemplateName(templateName);
		getJspContext().getOut().write(renderContext.applyTemplate(getResponse(), getBody()));
	}
}
