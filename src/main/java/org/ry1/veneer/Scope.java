package org.ry1.veneer;

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
