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

package com.ryanberdeen.veneer.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public abstract class ScopedTag extends VeneerTagSupport {
	private String scopeName;
	
	public void setScope(String scopeName) {
		this.scopeName = scopeName;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		boolean scoped = isScoped();
		if (scoped) {
			getContext().pushScope(scopeName);
		}
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
			if (scoped) {
				getContext().popScope();
			}
		}
	}
	
	protected boolean isScoped() {
		return true;
	}
	
	protected abstract void doScoped() throws Exception;
}
