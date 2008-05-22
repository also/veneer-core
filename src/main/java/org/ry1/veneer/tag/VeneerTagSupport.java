/* $Id$ */

package org.ry1.veneer.tag;

import java.io.CharArrayWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.ry1.veneer.RenderContext;
import org.ry1.veneer.VeneerSupport;

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
