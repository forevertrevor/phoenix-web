package com.mws.phoenix.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet capable of byte-serving a file created in the
 * Tomcat Work folder on a web server. Based in part on
 * code contained in DefaultServlet.java of the Tomcat application.
 * 
 * @author Ed Webb
 *
 */
public class RangeServlet extends HttpServlet {

	/**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int START = 0;
	private static final int END = 1;
	private static final int LENGTH = 2;

	private static final String mimeSeparator = "MWS-SYSTEMS_MULTIPART_SEPARATOR";
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response, request.getParameter("mimetype"), request.getParameter("filename"));
	}

	public void processRequest(HttpServletRequest request, HttpServletResponse response, String mimeType, String fileName) throws IOException {
        File tempFolder = (File)this.getServletContext().getAttribute("javax.servlet.context.tempdir");
		File file = new File(tempFolder, fileName);
		
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		long[][] ranges = getRanges(request.getHeader("Range"), file);
		if (ranges == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		if (ranges.length == 1) {
			response.setHeader("Content-Length", "" + ranges[0][LENGTH]);
			if (ranges[0][LENGTH] != file.length()) {
				response.setHeader("Content-Range", "bytes " + ranges[0][START] + "-" + ranges[0][END] + "/" + ranges[0][LENGTH]);
				response.setContentType(mimeType);
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			}

			try {
				BufferedReader is = new BufferedReader(new FileReader(file));
				writeData(new OutputStreamWriter(response.getOutputStream()), is, ranges[0]);
			} catch (IOException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
		} else {
			response.setContentType("multipart/byteranges,boundary=" + mimeSeparator);
			try {
				BufferedReader is = new BufferedReader(new FileReader(file));
				PrintWriter os = new PrintWriter(response.getOutputStream());
				for (int i = 0; i < ranges.length; i++) {
					os.println();
					os.println("--" + mimeSeparator);
					os.println("Content-Type: " + mimeType);
					os.println("Content-Range: " + ranges[i][START] + "-" + ranges[i][END] + "/" + ranges[i][LENGTH]);
					os.println();
					writeData(os, is, ranges[i]);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			} catch (IOException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}
	}
	
	/**
	 * Returns a 2 dimensional array of long values.
	 * @param rangeHeader
	 * @param file
	 * @return
	 */	
	private long[][] getRanges(String rangeHeader, File file) {
		if (rangeHeader == null) {
			file.length();
			return new long[][] {{0, file.length()-1, file.length()}};
		}
		if (!rangeHeader.startsWith("bytes")) {
			return null;
		}
		rangeHeader = rangeHeader.substring(6).trim();
		String[] ranges = rangeHeader.split(",");
		long[][] result = new long[ranges.length][3];
		for (int range = 0; range < ranges.length; range++) {
			String[] values = ranges[range].split("-");
			if (values.length != 2) {
				return null;
			}
			if (values[0].equals("")) {
				result[range][END] = file.length() - 1;
				result[range][LENGTH] = Long.parseLong(values[1]);
				result[range][START] = result[range][END] - result[range][LENGTH] + 1;
			} else if (values[1].equals("")) {
				result[range][START] = Long.parseLong(values[0]);
				result[range][END] = file.length() - 1;
				result[range][LENGTH] = result[range][END] - result[range][START] + 1;
			} else {
				result[range][START] = Long.parseLong(values[0]);
				result[range][END] = Long.parseLong(values[1]);
				result[range][LENGTH] = result[range][END] - result[range][START] + 1;
			}
			if (result[range][END] < result[range][START] 
			 || result[range][END] - result[range][START] != result[range][LENGTH]-1) {
				return null;
			}
		}
		return result;
	}
	
	private void writeData(Writer os, Reader is, long[] range) throws IOException {
		is.skip(range[START]);
		long toRead = range[LENGTH];
		char[] buffer = new char[1024];
		int len = buffer.length;
		while (toRead > 0) {
			len = is.read(buffer);
			if (len <= toRead) {
				os.write(buffer, 0, len);
				toRead -= len;
			} else {
				os.write(buffer, 0, (int)toRead);
				toRead = 0;
			}
		}
	}
}
