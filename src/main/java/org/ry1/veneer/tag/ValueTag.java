/* $Id$ */

package org.ry1.veneer.tag;

import static org.ry1.veneer.Context.CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME;

import java.io.IOException;

import javax.servlet.jsp.JspException;


public class ValueTag extends VeneerTagSupport {
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		if (name == null) {
			name = CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME;
		}
		
		getJspContext().getOut().write(getAttribute(name).toString());
	}
}
