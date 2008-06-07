package com.ryanberdeen.veneer.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public class WithTag extends SetTag {
	@Override
	protected Object getValue() throws JspException, IOException {
		return getJspBody();
	}
}
