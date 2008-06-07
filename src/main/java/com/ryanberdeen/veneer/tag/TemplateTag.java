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

import com.ryanberdeen.veneer.RenderContext;

/** Sets the template name and related context attributes.
 * 
 * @author Ryan Berdeen
 *
 */
public class TemplateTag extends VeneerTagSupport {
	
	/** Sets the name of the template.
	 * 
	 * @see RenderContext#setTemplateName(String)
	 */
	public void setName(String name) {
		getContext().setTemplateName(name);
	}
	
	/** Sets whether or not to skip the template.
	 * 
	 * <p>This effectively sets the template to <code>null</code>.</p>
	 */
	public void setSkip(boolean skip) {
		if (skip) {
			getContext().setTemplateName(null);
		}
	}
	
	/** Sets the content type.
	 * 
	 * @see RenderContext#getContentType()
	 */
	public void setContentType(String contentType) {
		getContext().setContentType(contentType);
	}
	
	/** Sets the character encoding.
	 * 
	 * @see RenderContext#getCharacterEncoding()
	 */
	public void setCharacterEncoding(String characterEncoding) {
		getContext().setCharacterEncoding(characterEncoding);
	}
}
