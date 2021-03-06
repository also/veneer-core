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

import java.io.CharArrayWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.ryanberdeen.veneer.RenderContext;
import com.ryanberdeen.veneer.VeneerSupport;

/** Support superclass for Veneer tags. Contains several support methods
 *  for interacting with the {@link RenderContext} and the {@link JspContext}.
 *  
 * @author Ryan Berdeen
 *
 */
public abstract class VeneerTagSupport extends SimpleTagSupport {
	private RenderContext context;
	
	/** Returns the named context attribute.
	 * 
	 * @see RenderContext#getAttribute(String)
	 */
	protected Object getAttribute(String name) {
		return getContext().getAttribute(name);
	}
	
	/** Sets the named context attribute.
	 * 
	 * @see RenderContext#setAttribute(String, Object)
	 */
	protected void setAttribute(String scopeName, String attributeName, Object value) {
		getContext().setAttribute(scopeName, attributeName, value);
	}
	
	/** Return the {@link HttpServletRequest} associated with the tag.
	 */
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getPageContext().getRequest();
	}
	
	/** Returns the {@link HttpServletResponse} associated with the tag.
	 */
	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) getPageContext().getResponse();
	}
	
	/** Returns the {@link PageContext} associated with the tag.
	 */
	protected PageContext getPageContext() {
		return (PageContext) getJspContext();
	}
	
	/** Returns the context associated with the tag.
	 */
	protected RenderContext getContext() {
		if (context == null) {
			context = VeneerSupport.getContext(getPageContext().getServletContext(), getRequest());
		}
		
		return context;
	}
	
	/** Returns the rendered body fragment.
	 */
	protected String renderBody() throws JspException, IOException {
		JspFragment jspBody = getJspBody();
		return (jspBody != null) ? renderFragment(jspBody) : null;
	}
	
	/** Returns the fragment, rendered to a <code>String</code>.
	 */
	protected String renderFragment(JspFragment fragment) throws JspException, IOException {
		CharArrayWriter writer = new CharArrayWriter();
		fragment.invoke(writer);
		return writer.toString();
	}
}
