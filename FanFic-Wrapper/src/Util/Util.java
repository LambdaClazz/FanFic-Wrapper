package Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Document;

public class Util {
	public static Number parseCommaNumber(String string) {
		DecimalFormat df = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		df.setDecimalFormatSymbols(symbols);
		
		try {
			return df.parse(string);
		} catch (ParseException e) {
			return -1;
		}
	}
	
	public static Date parseDate(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date startDate = null;
		try {
		    startDate = df.parse(date);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		return startDate;
	}
	
	public static Integer parseInt(String number) {
		int num = 0;
		
		try {
			num = Integer.parseInt(number);
		} catch(NumberFormatException e) {
			return -1;
		}
		
		return num;
	}
	
	public static void main(String[] args) throws IOException {
		Document[] docs = AO3Util.getBaseHTML(5562529);
		Document originaldoc = docs[0];
		Document html = docs[1];

		int index = 2;
		
		PrintWriter writer = new PrintWriter(new File("res/FileOriginal" + index));
		writer.println(originaldoc);
		writer.close();
		
		writer = new PrintWriter(new File("res/FileHtml" + index));
		writer.println(html);
		writer.close();
	}
}
