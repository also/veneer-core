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

import java.util.HashMap;

class Scope {
	private Scope parentScope;
	private String name;
	private HashMap<String, Object> localAttributes;
	
	public Scope(Scope parentScope) {
		this.parentScope = parentScope;
		
		if (parentScope != null) {
			localAttributes = new HashMap<String, Object>(parentScope.localAttributes.size() * 2);
			localAttributes.putAll(parentScope.localAttributes);
		}
		else {
			localAttributes = new HashMap<String, Object>();
		}
	}
	
	public Scope(Scope parentScope, String name) {
		this(parentScope);
		this.name = name;
	}
	
	public HashMap<String, Object> getLocalAttributes() {
		return localAttributes;
	}
	
	public Scope getParentState() {
		return parentScope;
	}
	
	public void setAttribute(String name, Object value) {
		localAttributes.put(name, value);
	}
	
	public boolean setAttribute(String scopeName, String attributeName, Object value) {
		if (scopeName == null || scopeName.equals(this.name)) {
			setAttribute(attributeName, value);
			return true;
		}
		else if (parentScope != null) {
			if (parentScope.setAttribute(scopeName, attributeName, value)) {
				setAttribute(name, value);
				return true;
			}
		}
		return false;
	}
	
	public Object getAttribute(String name) {
		return localAttributes.get(name);
	}
	
	public void replaceAttribute(String name, String value) {
		if (getAttribute(name) != null) {
			setAttribute(name, value);
			if (parentScope != null) {
				parentScope.replaceAttribute(name, value);
			}
		}
	}
}
