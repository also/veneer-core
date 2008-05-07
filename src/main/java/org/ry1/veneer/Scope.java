package org.ry1.veneer;

import java.util.HashMap;

class Scope {
	private Scope parentScope;
	
	private HashMap<String, Object> localAttributes;
	
	public Scope(Scope parentState) {
		this.parentScope = parentState;
		
		if (parentState != null) {
			localAttributes = new HashMap<String, Object>(parentState.localAttributes.size() * 2);
			localAttributes.putAll(parentState.localAttributes);
		}
		else {
			localAttributes = new HashMap<String, Object>();
		}
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
