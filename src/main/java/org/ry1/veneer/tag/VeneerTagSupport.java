/* $Id$ */

package org.ry1.veneer.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.ry1.veneer.Context;
import org.ry1.veneer.VeneerUtils;

public class VeneerTagSupport extends SimpleTagSupport {
	protected Object getAttribute(String name) {
		return getContext().getAttribute(name);
	}
	
	protected void setAttribute(String name, Object value) {
		getContext().setAttribute(name, value);
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) ((PageContext) getJspContext()).getRequest();
	}
	
	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) ((PageContext) getJspContext()).getResponse();
	}
	
	protected Context getContext() {
		return VeneerUtils.getContext(getRequest());
	}
}
