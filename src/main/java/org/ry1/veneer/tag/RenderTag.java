/* $Id$ */

package org.ry1.veneer.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.ry1.veneer.VeneerUtils;

public class RenderTag extends VeneerTagSupport {
	private String name;
	private String partial;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPartial(String partial) {
		this.partial = partial;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		getContext().pushScope();
		try {
			// TODO discard output
			if (getJspBody() != null) {
				getJspBody().invoke(getJspContext().getOut());
			}
			
			String value;
			if (name != null) {
				value = VeneerUtils.render(getRequest(), getResponse(), name);
			}
			else {
				value = VeneerUtils.renderParial(getRequest(), getResponse(), partial);
			}
			// TODO check for null partial
			
			if (value != null) {
				getJspContext().getOut().write(value);
			}
		}
		catch (IOException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new JspException(ex);
		}
		finally {
			getContext().popScope();
		}
	}
}
