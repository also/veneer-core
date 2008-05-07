/* $Id$ */

package org.ry1.veneer.tag;

import static org.ry1.veneer.Context.TEMPLATE_NAME_ATTRIBUTE_NAME;

import java.io.IOException;

import javax.servlet.jsp.JspException;


public class TemplateTag extends VeneerTagSupport {
	private String name;
	private boolean skip = false;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		String templateName = skip ? null : name;
		setAttribute(TEMPLATE_NAME_ATTRIBUTE_NAME, templateName);
	}
}
