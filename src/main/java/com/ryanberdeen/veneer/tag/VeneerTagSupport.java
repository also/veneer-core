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
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.ryanberdeen.veneer.RenderContext;
import com.ryanberdeen.veneer.VeneerSupport;

public class VeneerTagSupport extends SimpleTagSupport {
	protected Object getAttribute(String name) {
		return getContext().getAttribute(name);
	}
	
	protected void setAttribute(String scopeName, String attributeName, Object value) {
		getContext().setAttribute(scopeName, attributeName, value);
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getPageContext().getRequest();
	}
	
	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) getPageContext().getResponse();
	}
	
	protected RenderContext getContext() {
		return VeneerSupport.getContext(getPageContext().getServletContext(), getRequest());
	}
	
	protected PageContext getPageContext() {
		return (PageContext) getJspContext();
	}
	
	protected String getBody() throws JspException, IOException {
		CharArrayWriter writer = new CharArrayWriter();
		getJspBody().invoke(writer);
		return writer.toString();
	}
}
