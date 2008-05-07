/* $Id$ */

package org.ry1.veneer.tag;

import java.io.CharArrayWriter;
import java.io.IOException;

import javax.servlet.jsp.JspException;


public class SetTag extends VeneerTagSupport {
	private String name;
	private Object value;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		if (value == null && getJspBody() != null) {
			CharArrayWriter writer = new CharArrayWriter();
			getJspBody().invoke(writer);
			value = writer.toString();
		}
		
		setAttribute(name, value);
	}
}
