package Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

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
	
	public static void main(String[] args) throws IOException {
		Document doc = AO3Util.getBaseHTML(5562529);

		PrintWriter writer = new PrintWriter(new File("res/File1"));
		writer.println(doc);
		writer.close();
	}
}
