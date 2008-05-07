/* $Id$ */

package org.ry1.veneer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

public class VeneerView implements View {
	public static final String CONTEXT_ATTRIBUTE_NAME = VeneerView.class.getName() + ".viewState";
	
	private String uri;
	
	private String defaultTemplateName;
	
	public VeneerView(String uri, String defaultTemplateName) {
		this.uri = uri;
		this.defaultTemplateName = defaultTemplateName;
	}
	
	public String getContentType() {
		return null;
	}
	
	public String getPath() {
		return uri;
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (model != null) {
			for (Map.Entry<String, Object> modelEntry : ((Map<String, Object>) model).entrySet()) {
				request.setAttribute(modelEntry.getKey(), modelEntry.getValue());
			}
		}

		Context context = VeneerUtils.getContext(request);
		context.setTemplateName(defaultTemplateName);
		
		String value = context.render(request, response, uri);
		
		if (value != null) {
			response.getWriter().write(value);
		}
	}

}
  