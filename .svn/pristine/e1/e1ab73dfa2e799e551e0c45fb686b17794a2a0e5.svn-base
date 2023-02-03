package com.mws.phoenix.web.filter;

import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * <p>Title: Web Site</p>
 * <p>Description: Xtreme Information News Web Site</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Xtreme Information</p>
 * @author Ed Webb
 * @version 1.0
 */

public class GzipResponseWrapper extends HttpServletResponseWrapper {

  protected HttpServletResponse resp = null;
  protected ServletOutputStream stream = null;
  protected PrintWriter writer = null;

  public GzipResponseWrapper(HttpServletResponse response) {
    super(response);
    resp = response;
  }

  public void complete() {
    if (null != writer) {
      writer.close();
    } else if (null != stream) {
      try {
        stream.close();
      } catch (IOException ioe) {
        //ignore
      }
    }
  }

  public void flush() throws IOException {
    stream.flush();
  }

  public ServletOutputStream getOutputSteam() throws IOException {
    if (null != writer) {
      throw new IllegalStateException("A PrintWriter has already been created for this response");
    }

    if (null == stream) {
      stream = createOutputStream();
    }
    return stream;
  }

  public PrintWriter getWriter() throws IOException {
    if (null != writer) {
      return writer;
    }
    if (null != stream) {
      throw new IllegalStateException("A ServletOutputStream has already been created for this response");
    }
    stream = createOutputStream();
    writer = new PrintWriter(stream);
    return writer;
  }

  protected ServletOutputStream createOutputStream() throws IOException {
    return new GzipResponseStream(resp);
  }

}