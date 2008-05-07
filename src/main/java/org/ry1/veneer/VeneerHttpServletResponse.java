/* $Id$ */

package org.ry1.veneer;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/** Wraps the response to buffer the output and notice 404 errors.
 * @author Ryan Berdeen
 *
 */
class VeneerHttpServletResponse extends HttpServletResponseWrapper {
	private ServletOutputStream out;
	private ByteArrayOutputStream byteOut;
	private CharArrayWriter charWriter;
	private PrintWriter writer;
	private boolean notFound;
	
	public VeneerHttpServletResponse(HttpServletResponse response) {
		super(response);
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (out == null) {
			byteOut = new ByteArrayOutputStream();
			out = new ServletOutputStream() {
				@Override
				public void write(int b) throws IOException {
					byteOut.write(b);
				}
			};
		}
		
		return out;
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer == null) {
			charWriter = new CharArrayWriter();
			writer = new PrintWriter(charWriter);
		}
		
		return writer;
	}
	
	/** Returns the String value that has been written to the output stream or writer.
	 */
	public String getValue() {
		if (out != null) {
			return byteOut.toString();
		}
		else if (writer != null){
			return charWriter.toString();
		}
		else {
			return null;
		}
	}
	
	@Override
	public void sendError(int sc) throws IOException {
		sendError(sc, null);
	}
	
	@Override
	public void sendError(int sc, String msg) throws IOException {
		if (sc == SC_NOT_FOUND) {
			notFound = true;
		}
		else {
			super.sendError(sc, msg);
		}
	}
	
	/** Returns whether the servlet reported a 404 error.
	 */
	public boolean isNotFound() {
		return notFound;
	}

}