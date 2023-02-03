package com.mws.phoenix.web.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.GZIPOutputStream;

public class GzipResponseStream extends ServletOutputStream {

  protected ByteArrayOutputStream baos = null;
  protected GZIPOutputStream gzip = null;
  protected boolean closed = false;
  protected HttpServletResponse resp = null;
  protected ServletOutputStream stream = null;

  public GzipResponseStream(HttpServletResponse response) throws IOException {
    super();
    resp = response;
    stream = response.getOutputStream();
    baos = new ByteArrayOutputStream();
    gzip = new GZIPOutputStream(baos);
  }

  public void close() throws IOException {
    if (closed) {
      throw new IOException("This output stream has already been closed");
    }
    gzip.finish();

    byte[] bytes = baos.toByteArray();

    resp.addHeader("Content-Encoding","gzip");
    resp.addHeader("Content-Length",Integer.toString(bytes.length));
    stream.write(bytes);
    stream.flush();
    stream.close();
    closed = true;
  }

  public void write(byte b[]) throws IOException {
    if (closed) {
      throw new IOException("This output stream has already been closed");
    }
    gzip.write(b);
  }

  public void write(int i) throws IOException {
    if (closed) {
      throw new IOException("This output stream has already been closed");
    }
    gzip.write(i);
  }

  public void write(byte b[], int off, int len) throws IOException {
    if (closed) {
      throw new IOException("This output stream has already been closed");
    }
    gzip.write(b,off,len);
  }

}