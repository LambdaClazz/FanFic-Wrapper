package Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AO3Util {
	public static final String URL = "https://archiveofourown.org";
	public static final String WORKS = "https://archiveofourown.org/works/";
	
	/**
	 * 
	 * @param workIndex
	 * @return -> An array of Documents, the first being the original page, the second is the basic html document;
	 */
	public static Document[] getBaseHTML(int workIndex) {
		URL url = null;
	    InputStream is = null;
	    Document original = null;
	    
        try {
        	original = Jsoup.connect(WORKS + workIndex).get();
			url = new URL(original.baseUri());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        try {
			is = url.openStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}  
		
		Document document = null;
		
		try {
			document = Jsoup.parse(is, "UTF-8", url.toString());
			if(document.select("body").text().equals("You are being redirected.")) {
				is.close();
				
				url = new URL(document.select("a").attr("abs:href").replaceAll("http", "https"));
				
				is = url.openStream();
				document = Jsoup.parse(is, "UTF-8", url.toString());
			}
			
			Elements e1 = document.select("li.download");
			
			Elements e2 = e1.select("ul");
			Elements e3 = e2.select("li");
			Elements e4 = e3.select("a");
			document = Jsoup.connect(e4.get(3).attr("abs:href")).get();
			
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new Document[] { original, document };
	}
}
