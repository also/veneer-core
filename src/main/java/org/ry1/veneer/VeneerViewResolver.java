/* $Id$ */

package org.ry1.veneer;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class VeneerViewResolver implements ViewResolver, Ordered {
	private String prefix = "/WEB-INF/jsp/";
	private String suffix = ".jsp";
	private String defaultTemplate = null;
	private int order;
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public void setDefaultTemplate(String defaultTemplate) {
		this.defaultTemplate = defaultTemplate;
	}
	
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return new VeneerView(prefix + viewName + suffix, defaultTemplate);
	}
	
	public String resolvePartialPath(String name, String relative) {
		if (name.indexOf('/') == 0) {
			return resolvePartialPath(name.substring(1), prefix);
		}
		else if (name.startsWith("../")) {
			return resolvePartialPath(name.substring(3), relative.substring(0, relative.lastIndexOf('/')));
		}
		else {
			int nameSlashIndex = name.lastIndexOf('/');
			if (nameSlashIndex != -1) {
				name = name.substring(0, nameSlashIndex + 1) + '_' + name.substring(nameSlashIndex + 1);
			}
			else {
				name = '_' + name;
			}
			
			int relativeSlashIndex = relative.lastIndexOf('/');
			if (relativeSlashIndex != -1) {
				return relative.substring(0, relativeSlashIndex + 1) + name + suffix;
			}
			else {
				return prefix + name + suffix;
			}
		}
	}

}
