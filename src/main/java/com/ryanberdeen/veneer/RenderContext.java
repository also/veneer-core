/*
 * Copyright 2008 Ryan Berdeen.
 *
 * This file is part of Veneer.
 *
 * Veneer is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Veneer is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Veneer.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ryanberdeen.veneer;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Central Veneer class. The render context is responsible for maintaining the
 * scope stack and rendering the views.
 * 
 * @author Ryan Berdeen
 *
 */
public class RenderContext {
	/** Attribute name used to store the context in the request.
	 */
	public static final String ATTRIBUTE_NAME = RenderContext.class.getName() + ".renderContext";
	
	/** Attribute name used to expose Veneer scoped attributes directly to the
	 * request.
	 */
	public static final String EXPOSED_ATTRIBUTES_ATTRIBUTE_NAME = "view";

	/** Attribute name used to store the template name in the scope.
	 */
	public static final String TEMPLATE_NAME_ATTRIBUTE_NAME = "template";

	/** Attribute name used to store the content for the template in the scope.
	 */
	public static final String CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME = "contentForLayout";

	/** Attribute name used to store the content type in the scope.
	 */
	public static final String CONTENT_TYPE_ATTRIBUTE_NAME = "contentType";

	/** Attribute name used to store the character encoding in the scope.
	 */
	public static final String CHARACTER_ENCODING_ATTRIBUTE_NAME = "characterEncoding";

	/** Attribute name used to store the current URI in the scope.
	 */
	private static final String URI_ATTRIBUTE_NAME = "viewUri";

	private Configuration config;

	private Scope currentScope;
	private HttpServletRequest request;

	private String mainTemplateName;

	/** Creates a new RenderContext with the specified configuration for the request.
	 * 
	 * <p>The outer scope is pushed onto the stack, with the {@linkplain Configuration#setOuterScopeName(String) configured name}.</p>
	 */
	public RenderContext(Configuration config, HttpServletRequest request) {
		this.config = config;
		this.request = request;

		mainTemplateName = config.getDefaultTemplateName();

		pushScope(config.getOuterScopeName());
	}

	/** Returns the configuration used by this context.
	 */
	public Configuration getConfiguration() {
		return config;
	}

	/** Pushes a new scope onto the stack. The new scope has starts with no
	 * template set.
	 */
	public void pushScope(String name) {
		currentScope = new Scope(currentScope, name);

		setTemplateName(null);

		request.setAttribute(EXPOSED_ATTRIBUTES_ATTRIBUTE_NAME, currentScope.getLocalAttributes());
	}

	/** Pops the current scope off the stack.
	 */
	public void popScope() {
		currentScope = currentScope.getParentState();

		request.setAttribute(EXPOSED_ATTRIBUTES_ATTRIBUTE_NAME, currentScope.getLocalAttributes());
	}
	
	/** Returns the content type set in the current scope.
	 */
	public String getContentType() {
		return (String) getAttribute(CONTENT_TYPE_ATTRIBUTE_NAME);
	}
	
	/** Sets the content type in the current scope.
	 */
	public void setContentType(String contentType) {
		setAttribute(CONTENT_TYPE_ATTRIBUTE_NAME, contentType);
	}
	
	/** Returns the character encoding set in the current scope.
	 */
	public String getCharacterEncoding() {
		return (String) getAttribute(CHARACTER_ENCODING_ATTRIBUTE_NAME);
	}
	
	/** Sets the content type in the current scope.
	 */
	public void setCharacterEncoding(String characterEncoding) {
		setAttribute(CHARACTER_ENCODING_ATTRIBUTE_NAME, characterEncoding);
	}

	/** Set the name of the main template used by {@link #renderMain(HttpServletResponse, String)}.
	 * 
	 * <p>The default main template name is the {@linkplain Configuration#setDefaultTemplateName(String) configured default template}.</p>
	 */
	public void setMainTemplateName(String mainTemplateName) {
		this.mainTemplateName = mainTemplateName;
	}

	/** Returns the template name set in the current scope.
	 */
	public String getTemplateName() {
		return (String) getAttribute(TEMPLATE_NAME_ATTRIBUTE_NAME);
	}

	/** Sets the template name in the current scope.
	 */
	public void setTemplateName(String templateName) {
		setAttribute(TEMPLATE_NAME_ATTRIBUTE_NAME, templateName);
	}

	/** Sets the value of the named attribute in the current scope.
	 * @param name
	 * @param value
	 */
	public void setAttribute(String name, Object value) {
		getCurrentScope().setAttribute(null, name, value);
	}

	/** Sets the value of an attribute in the specified scope, relative to the
	 * current scope.
	 */
	public void setAttribute(String scopeName, String attributeName, Object value) {
		getCurrentScope().setAttribute(attributeName, value);
	}

	/** Returns the value of the attribute in the current scope.
	 */
	public Object getAttribute(String name) {
		return getCurrentScope().getAttribute(name);
	}

	/** Returns the current scope.
	 */
	private Scope getCurrentScope() {
		if (currentScope == null) {
			// TODO exception type
			throw new RuntimeException("No view scope available");
		}

		return currentScope;
	}

	/** Returns the URI of the view currently being rendered.
	 */
	public String getCurrentUri() {
		return (String) getAttribute(URI_ATTRIBUTE_NAME);
	}

	/** Renders the named view.
	 * 
	 * @see #renderUri(HttpServletResponse, String)
	 */
	public String render(HttpServletResponse response, String name) throws ServletException, IOException {
		return renderUri(response, resolveViewPath(name));
	}
	
	/** Renders the view and writes the response to the {@link HttpServletResponse}.
	 *  Sets the content type and character encoding to those specified by
	 *  setContentType and setCharacterEncoding.
	 *  
	 * @param response the response to write to
	 * @param name the name of the view to render
	 */
	public void writeMain(HttpServletResponse response, String name) throws ServletException, IOException {
		String value = renderMain(response, name);

		if (value != null) {
			String characterEncoding = getCharacterEncoding();
			if (characterEncoding != null) {
				response.setCharacterEncoding(characterEncoding);
			}
			String contentType = getContentType();
			if (contentType != null) {
				response.setContentType(getContentType());
			}
			response.getWriter().write(value);
		}
	}

	/** Renders the named view with the main template applied. The template may
	 * be overridden by the view.
	 * 
	 * @see #setMainTemplateName(String)
	 * @see #resolveViewPath(String)
	 * @see #renderUri(HttpServletResponse, String)
	 */
	public String renderMain(HttpServletResponse response, String name) throws ServletException, IOException {
		setTemplateName(mainTemplateName);
		return renderUri(response, resolveViewPath(name));
	}

	/** Renders the named partial.
	 * 
	 * @see #resolvePartialPath(String)
	 * @see #renderUri(HttpServletResponse, String)
	 */
	public String renderPartial(HttpServletResponse response, String name) throws ServletException, IOException {
		return renderUri(response, resolvePartialPath(name));
	}

	/** Renders the URI. If a template name is set after the URI has been
	 * rendered the template is applied before the result is returned.
	 * 
	 * @see #applyTemplate(HttpServletResponse, String)
	 */
	public String renderUri(HttpServletResponse response, String uri) throws ServletException, IOException {
		VeneerHttpServletResponse cachingResponse = new VeneerHttpServletResponse(response);
		setAttribute(URI_ATTRIBUTE_NAME, uri);

		request.getRequestDispatcher(uri).include(request, cachingResponse);
		if (cachingResponse.isNotFound() && !getConfiguration().getIgnoreInvalidPaths()) {
			throw new FileNotFoundException(uri);
		}

		return applyTemplate(response, cachingResponse.getValue());
	}

	/** Applies the current template to the content and returns the result. If
	 * there is no current template name, the content is returned unchanged.
	 * The template is applied using {@link #render(HttpServletResponse, String)},
	 * with the content in the {@link #CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME} attribute.
	 * As a result, the template itself may specify a template.
	 */
	public String applyTemplate(HttpServletResponse response, String content) throws ServletException, IOException {
		String templateName = (String) getAttribute(TEMPLATE_NAME_ATTRIBUTE_NAME);
		if (templateName != null) {
			setAttribute(CONTENT_FOR_LAYOUT_ATTRIBUTE_NAME, content);
			setTemplateName(null);
			return render(response, templateName);
		}
		else {
			return content;
		}
	}

	/** Resolve the view name into a path.
	 */
	public String resolveViewPath(String viewName) {
		return config.getPrefix() + viewName + config.getSuffix();
	}

	/** Resolves a partial name into a path relative to the current URI.
	 * 
	 * @see #getCurrentUri()
	 * @see #resolvePartialPath(String, String)
	 */
	public String resolvePartialPath(String name) {
		return resolvePartialPath(name, getCurrentUri());
	}

	/** Resolves a partial name into a path relative to the specified URI.
	 * 
	 * <p>If the name starts with a slash (<code>/</code>), the name is name is
	 * resolved relative to the view root directory.</p>
	 * 
	 * <p>The filename of the URI is prefixed by the partial prefix.</p>
	 * 
	 * @see Configuration#getPartialPrefix()
	 */
	public String resolvePartialPath(String name, String relative) {
		Configuration configuration = getConfiguration();
		String prefix = configuration.getPrefix();
		String suffix = configuration.getSuffix();

		if (name.charAt(0) == '/') {
			return resolvePartialPath(name.substring(1), prefix);
		}
		else if (name.startsWith("../")) {
			return resolvePartialPath(name.substring(3), relative.substring(0, relative.lastIndexOf('/')));
		}
		else {
			int nameSlashIndex = name.lastIndexOf('/');
			if (nameSlashIndex != -1) {
				name = name.substring(0, nameSlashIndex + 1) + configuration.getPartialPrefix() + name.substring(nameSlashIndex + 1);
			}
			else {
				name = configuration.getPartialPrefix() + name;
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
