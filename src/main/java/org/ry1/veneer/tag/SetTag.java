/* $Id$ */

package org.ry1.veneer.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;


public class SetTag extends VeneerTagSupport {
	private String scopeName;
	private String attributeName;
	private Object value;
	
	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}
	
	public void setName(String name) {
		this.attributeName = name;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		if (value == null && getJspBody() != null) {
			value = getBody();
		}
		
		setAttribute(scopeName, attributeName, value);
	}
}
