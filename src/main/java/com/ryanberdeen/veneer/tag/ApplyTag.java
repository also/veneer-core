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

import com.ryanberdeen.veneer.Configuration;
import com.ryanberdeen.veneer.RenderContext;

/** Applies a template to the tag body. The body is evaluated in a local scope.
 * 
 * @author Ryan Berdeen
 *
 */
public class ApplyTag extends ScopedTag {
	private String templateName;
	
	/** Sets the name of the template that will be applied.
	 * The default is the template {@link Configuration#getDefaultTemplateName()}.
	 *
	 * @see RenderContext#setTemplateName(String)
	 */
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
		getJspContext().getOut().write(renderContext.applyTemplate(getResponse(), renderBody()));
	}
}
