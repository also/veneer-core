package com.ryanberdeen.veneer.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

/** Sets an attribute to a JSP fragment.
 * 
 * @author Ryan Berdeen
 *
 */
public class WithTag extends SetTag {
	@Override
	protected Object getValue() throws JspException, IOException {
		return getJspBody();
	}
}
