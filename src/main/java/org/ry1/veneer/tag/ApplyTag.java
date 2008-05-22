package org.ry1.veneer.tag;

import org.ry1.veneer.RenderContext;


public class ApplyTag extends ScopedTag {
	private String templateName;
	
	public void setTemplate(String templateName) {
		this.templateName = templateName;
	}
	
	@Override
	public void doScoped() throws Exception {
		RenderContext renderContext = getContext();
		
		if (templateName == null) {
			 templateName = renderContext.getConfiguration().getDefaultTemplateName();
		}
		
		renderContext.setTemplateName(templateName);
		getJspContext().getOut().write(renderContext.applyTemplate(getResponse(), getBody()));
	}
}
