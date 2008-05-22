package org.ry1.veneer.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public abstract class ScopedTag extends VeneerTagSupport {
	private String scopeName;
	
	public void setScope(String scopeName) {
		this.scopeName = scopeName;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		getContext().pushScope(scopeName);
		try {
			doScoped();
		}
		catch (JspException ex) {
			throw ex;
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
	
	protected abstract void doScoped() throws Exception;
}
