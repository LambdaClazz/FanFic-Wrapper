package AO3;

import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Util.AO3Util;
import Util.Util;

public class AO3Work {
	public static enum Property {
		Rating("Rating"),
		ArchiveWarning("Archive Warning"),
		Category("Category"),
		Fandom("Fandom"),
		Relationship("Relationship"),
		Character("Character"), 
		AdditionalTags("Additional Tags"),
		Series("Series"),
		Collections("Collections"),
		Title("Title"),
		Language("Language"),
		Author("Author");
		
		private String name;
		private Property(String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
		
		public static Property parse(String name) {
			for(Property p : values()) 
				if(p.name.equals(name))
					return p;
			return null;
		}
	}
	
	private HashMap<Property, String[]> properties;
	
	private Date published, updated, completed;
	private boolean complete, oneShot;
	private int updatedChapter, totalChapterCount;
	private long words;
	private int comments, kudos, bookmarks, hits;
	
	private long workIndex;
	
	public AO3Work(int workIndex) { 
		this.workIndex = workIndex; 
		
		properties = new HashMap<>();
		
		Document[] documents = AO3Util.getBaseHTML(workIndex);
		Document originalDocument = documents[0];
		
		//the basic html file holds all of the more complicated information, but does not include the basic info on comments and hits
		//However, this is easy to retrieve from the original page, unlike the other information about ao3 works
		Elements originalStats = originalDocument.select("dl");
		properties.put(Property.Language, new String[] { originalStats.select("dd.language").text() });
		comments = Integer.parseInt(originalStats.select("dd.comments").text());
		kudos = Integer.parseInt(originalStats.select("dd.kudos").text());
		bookmarks = Integer.parseInt(originalStats.select("dd.bookmarks").text());
		hits = Integer.parseInt(originalStats.select("dd.hits").text());
		
		Document htmldocument = documents[1];
		
		Elements tagElements = htmldocument.select("dl.tags");
		Elements labels = tagElements.select("dt");
		Elements values = tagElements.select("dd");

		//Stats are special
		for(int i = 0; i < labels.size() - 1; i++) {
			String label = labels.get(i).text();
			label = label.substring(0, label.length() - 1);
			
			Elements words = values.get(i).select("a");
			
			String[] tempValues = new String[words.size()];
			for(int j = 0; j < tempValues.length; j++) 
				tempValues[j] = words.get(j).text();
			
			properties.put(Property.parse(label), tempValues);
		}
		
		//Stats
		String[] stats = values.get(values.size() - 1).text().split(" ");
		for(int i = 0; i < stats.length; i++) {
			if(stats[i].equals("Updated:")) 
				updated = Util.parseDate(stats[i + 1]);
			else if(stats[i].equals("Completed:"))
				completed = Util.parseDate(stats[i + 1]);
		    else if(stats[i].equals("Published:"))
				published = Util.parseDate(stats[i + 1]);
			else if(stats[i].equals("Words:")) 
				words = Integer.parseInt(stats[i + 1]);
			else if(stats[i].equals("Chapters:")) {
				String[] parts = stats[i + 1].split("/");
				updatedChapter = Util.parseInt(parts[0]);
				totalChapterCount = Util.parseInt(parts[1]);
				
				oneShot = totalChapterCount == 1;
				complete = totalChapterCount == updatedChapter;
			}
		}
		
		if(totalChapterCount == 0 && updatedChapter == 0) {//HTML one shot doesn't list the chapter counts
			totalChapterCount = 1;
			updatedChapter = 1;
			
			oneShot = true;
			complete = true;
		}
		
		properties.put(Property.Title, new String[] { htmldocument.select("h1").text() });
		properties.put(Property.Author, new String[] { htmldocument.select("a[rel=author]").text() });
		
		for(Entry<Property, String[]> entry : properties.entrySet()) {
			System.out.println("---------");
			System.out.println(entry.getKey());
			for(String s : entry.getValue())
				System.out.print(s + ", ");
			System.out.println();
		}
		System.out.println();
		
		System.out.println("Updated: " + updated);
		System.out.println("Published: " + published);
		System.out.println("Completed: " + completed);
		System.out.println("Complete: " + complete);
		System.out.println("One Shot: " + oneShot);
		System.out.println("Current Chapter Count: " + updatedChapter);
		System.out.println("Totle Chapter Count: " + totalChapterCount);
		System.out.println("Word Count: " + words);
		System.out.println("Comments: " + comments);
		System.out.println("Kudos: " + kudos);
		System.out.println("Bookmarks: " + bookmarks);
		System.out.println("Hits: " + hits);
	}
}
