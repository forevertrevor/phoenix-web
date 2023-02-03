package com.mws.phoenix.web.alert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class HTMLImageAttacher {

	Pattern imgTag = Pattern.compile("<img.*?src=[\"'](.*?:8080.*?)[\"']");
	
	
	/**
	 * Attaches the images referenced in the html string to a multipart
	 * and alteres the html to reference these attachments.
	 * 
	 * @param html the html string to use
	 * @return a multipart that contains the html body and image attachments
	 */
	public MimeMultipart attachImages(String html) {
		MimeMultipart mp = new MimeMultipart("related");
		
		Set<String> urls = extractImages(html);
		
		attachImages(mp, urls);
		
		html = updateHTML(html, urls);
		
		BodyPart msg = new MimeBodyPart();
		try {
			msg.setContent(html, "text/html; charset=utf-8");
			mp.addBodyPart(msg, 0);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mp;
	}

	/**
	 * Finds all image tags and extracts the src attribute. Turns them 
	 * into URLs and adds them to a Set which is returned.
	 *  
	 * @param html the html to search
	 * @return a Set to image URLs
	 */
	private Set<String> extractImages(String html) {
		Set<String> urls = new HashSet<String>();
		
		Matcher match = imgTag.matcher(html);
		boolean matched = match.find();
		while (matched) {
			urls.add(match.group(1));
			matched = match.find();
		}

		return urls;
	}

	/**
	 * Creates a BodyPart for each url in the set and attaches them to 
	 * the Multipart
	 * 
	 * @param mp the Multipart to attach the BodyParts to
	 * @param urls the list of image URLs
	 */
	private void attachImages(MimeMultipart mp, Set<String> urls) {
		Iterator<String> it = urls.iterator();
		int imgCount = 0;
		while(it.hasNext()) {
			try {
				URL url = new URL(it.next());
				BodyPart img = new MimeBodyPart();
				img.setDataHandler(new DataHandler(url));
				img.setHeader("Content-ID", "<image-" + imgCount++ + ">");
				mp.addBodyPart(img);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Updates the html string with new source attributes for all 
	 * image tags. These will now use the cid (Content-ID) protocol
	 * so that the mail reader will look in the message for the image
	 * 
	 * @param html the html String
	 * @param urls the Set of urls
	 * @return the altered html
	 */
	@SuppressWarnings("unused")
	private String updateHTML(String html, Set<String> urls) {
		Iterator<String> it = urls.iterator();
		int imgCount = 0;
		while (it.hasNext()) {
			try {
				// Check that this is a valid URL - if not we shouldn't replace it.
				String string = it.next();
				new URL(string);
				html = html.replaceAll(string, "cid:image-" + imgCount++);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return html;
	}
}
