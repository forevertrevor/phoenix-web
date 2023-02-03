package com.mws.phoenix.web;

import java.io.InputStream;
import java.net.MalformedURLException;

import javax.servlet.ServletContext;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;

import com.mws.phoenix.db.web.LoginStyle;
import com.mws.util.xml.XMLException;
import com.mws.util.xml.XMLFactory;

public class MWSWebResolver implements URIResolver {

	private ServletContext application;
	private String userFolder;
	private String defaultFolder;
	
	public MWSWebResolver(ServletContext application, LoginStyle style, String folder) {
		this.application = application;
		userFolder = style.getFolder() + folder;
		defaultFolder = "default/" + folder;
	}

	public Source resolve(String href, String base) throws TransformerException {
        try {
			String file = WebUtilities.getResource(application, userFolder, defaultFolder, href);
			InputStream is = application.getResourceAsStream(file);
			return XMLFactory.source(is);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (XMLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
