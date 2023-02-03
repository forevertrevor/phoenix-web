package com.mws.phoenix.web.press;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mws.phoenix.db.article.Article;
import com.mws.phoenix.db.despatch.Hit;
import com.mws.util.ReflectUtilities;

public class ExportNewsAction extends NewsAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		super.execute(mapping, form, request, response);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		DecimalFormat df = new DecimalFormat("0.00");
		List hits = (List)request.getAttribute("items");
		
		response.setContentType("text/csv");
		response.addHeader("Content-disposition", "attachment; filename=xymedia-news.csv");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        
        writer.write("Lead/Also,Article Type,Source Name,Publication Date,Location,AVE,Headline,Summary\n");
		Iterator it = hits.iterator();
	    while (it.hasNext()) {
	        Object o = it.next();
	        Hit h = (Hit)ReflectUtilities.getProperty(o, "hit");
	        writer.write("Lead");
	        writer.write(",");
	        writer.write(h.getArticle().getObjectName());
	        writer.write(",\"");
	        writer.write((h.getArticle().getSource().getSourceName().replaceAll("\"", "\"\"")));
	        writer.write("\",");
	        writer.write(sdf.format(h.getArticle().getArticleDate()));
	        writer.write(",\"");
	        writer.write(h.getArticle().getLocation().replaceAll("\"", "\"\""));
	        writer.write("\",");
	        if(h.getArticle().getAve() != null){
	        	writer.write(df.format(h.getArticle().getAve()));
	        } else {
	        	writer.write("n/a");
	        }
            	        
	        writer.write(",\"");
	        writer.write(h.getArticle().getHeadline().replaceAll("\"", "\"\""));
	        writer.write("\",\"");
	        if (h.getSummary() != null) {
	        	writer.write(h.getSummary().replaceAll("\"", "\"\""));
	        } else if (h.getArticle().getSummary() != null) {
	        	writer.write(h.getArticle().getSummary().replaceAll("\"", "\"\""));
	        } 
	        writer.write("\"\n");

            Iterator<Hit> alsos = h.getAlsoMentioned().iterator();
            while (alsos.hasNext()) {
            	Hit also = alsos.next();
    	        writer.write("Also");
    	        writer.write(",");
    	        writer.write(h.getArticle().getObjectName());
    	        writer.write(",\"");
    	        writer.write((also.getArticle().getSource().getSourceName().replaceAll("\"", "\"\"")));
    	        writer.write("\",");
    	        writer.write(sdf.format(also.getArticle().getArticleDate()));
    	        writer.write(",\"");
    	        writer.write(also.getArticle().getLocation().replaceAll("\"", "\"\""));
    	        writer.write("\",");
    	        //writer.write(df.format(also.getArticle().getAve()));
    	        if(also.getArticle().getAve() != null){
    	        	writer.write(df.format(also.getArticle().getAve()));
    	        } else {
    	        	writer.write("n/a");
    	        }
    	        writer.write(",\"");
    	        writer.write(also.getArticle().getHeadline().replaceAll("\"", "\"\""));
    	        writer.write("\",");
    	        writer.write("\n");
            }
	    }
		writer.flush();
		return null;
	}

	
    /**
     * Copy bytes from an <code>InputStream</code> to an
     * <code>OutputStream</code>.
     *
     * @param input  The <code>InputStream</code> to read from.
     * @param output The <code>OutputStream</code> to write to.
     *
     * @return the number of bytes copied
     *
     * @throws IOException In case of an I/O problem
     */
    public int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024 * 4];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }	
}
