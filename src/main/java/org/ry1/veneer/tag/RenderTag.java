/* $Id$ */

package org.ry1.veneer.tag;

import org.ry1.veneer.VeneerSupport;

public class RenderTag extends ScopedTag {
	private String name;
	private String partial;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPartial(String partial) {
		this.partial = partial;
	}
	
	@Override
	public void doScoped() throws Exception {
		if (getJspBody() != null) {
			getBody();
		}
		
		String value;
		if (name != null) {
			value = VeneerSupport.render(getPageContext().getServletContext(), getRequest(), getResponse(), name);
		}
		else {
			value = VeneerSupport.renderPartial(getPageContext().getServletContext(), getRequest(), getResponse(), partial);
		}
		// TODO check for null partial
		
		if (value != null) {
			getJspContext().getOut().write(value);
		}
	}
}
