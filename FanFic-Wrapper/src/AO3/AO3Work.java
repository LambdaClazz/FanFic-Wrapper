package AO3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Util.AO3Util;
import Util.Util;

public class AO3Work {
	public static enum Rating {
		Unrated("Not Rated", 0), General("General Audiences", 1), Teen("Teen And Up Audiences", 2), M("Mature", 3), E("Explicit", 4);
		
		private String name;
		private int index;
		private Rating(String name, int index) {
			this.name = name;
			this.index = index;
		}
		
		public static Rating parse(String string) {
			for(Rating r : values())
				if(r.toString().equals(string))
					return r;
			return Unrated;
		}
		
		public String toString() { return name; }
		public int getIndex() { return index; }
	}
	
	public static enum Warning {
		None("Creator Chose Not To Use Archive Warnings"), Graphic("Graphic Depictions Of Violence"), 
		MajorDeath("Major Character Death"), 
		NoApply("No Archive Warnings Apply"), RapeNonCon("Rape/Non-Con"), Underage("Underage");
		
		private String name;
		private Warning(String name) {
			this.name = name;
		}
		
		public static Warning parse(String string) {
			for(Warning w : values())
				if(w.toString().equals(string))
					return w;
			return None;
		}
		
		public String toString() { return name; }
		public String getName() { return name; }
	}
	
	public static enum Catagories {
		FF("F/F", 0), FM("F/M", 1), Gen("Gen", 2), MM("M/M", 3), Multi("Multi", 4), Other("Other", 5);
		
		private String name;
		private int index;
		private Catagories(String name, int index) {
			this.name = name;
			this.index = index;
		}
		
		public static Catagories parse(String string) {
			for(Catagories c : values())
				if(c.toString().equals(string))
					return c;
			return Other;
		}
		
		public int getIndex() { return index; }
		public String toString() { return name; }
		public String getName() { return name; }
	}
	
	private Rating rating;
	private String title, language, author;
	
	private ArrayList<Catagories> catagories;
	private ArrayList<Warning> warnings;
	private ArrayList<AO3Tag> fandoms, relationships, characters, additionalTags;
	
	private Date published, current;
	private boolean complete, oneShot;
	private int updatedChapter, totalChapterCount;
	private long words;
	private int comments, kudos, bookmarks, hits;
	
	private long workIndex;
	
	public AO3Work(int workIndex) { 
		this.workIndex = workIndex; 
		
		catagories = new ArrayList<>();
		warnings = new ArrayList<>();
		fandoms = new ArrayList<>();
		relationships = new ArrayList<>();
		characters = new ArrayList<>();
		additionalTags = new ArrayList<>();
		
		Document document = AO3Util.getBaseHTML(workIndex);
		Elements tagElements = document.select("dl.tags");
		Elements labels = tagElements.select("dt");
		Elements values = tagElements.select("dd");

		//Stats are special
		for(int i = 0; i < labels.size() - 1; i++) {
			System.out.println(labels.get(i).text());
			System.out.println(values.get(i).text());
		}
		
		System.out.println("----");
	}
	
	public String toString() { 
		StringBuilder tags = new StringBuilder();

		tags.append("\nRating: " + rating);
		
		for(Warning temp : warnings) 
			tags.append("\nWarning: " + temp);
		
		for(Catagories temp : catagories) 
			tags.append("\nCatagory: " + temp);
		
		for(AO3Tag temp : relationships) 
			tags.append("\nRelationship: " + temp);
		
		for(AO3Tag temp : characters) 
			tags.append("\nCharacter: " + temp);
		
		for(AO3Tag temp : additionalTags) 
			tags.append("\nAdditional Tag: " + temp);
		
		return "Title: " + title + 
				"\nAuthor: " + author +
				tags.toString() + 
				"\nLanguage: " + language + "\nWords: " + words + 
				"\nCurrent Updated Chapter: " + updatedChapter + 
				"\nTotal Chapters: " + totalChapterCount + 
				"\nComment Count: " + comments + 
				"\nKudos: " + kudos + 
				"\nBookmarks: " + bookmarks +
				"\nHits: " + hits +
				"\nCompleted: " + complete +
				"\nOne Shot: " + oneShot;
	}
	
	public Date getPublished() { return published; }
	public Date getCurrent() { return current; }
	public int getUpdatedChapter() { return updatedChapter; }
	public int getTotalChapterCount() { return totalChapterCount; }
	public long getWords() { return words; }
	public int getComments() { return comments; }
	public int getKudos() { return kudos; }
	public int getBookmarks() { return bookmarks; }
	public int getHits() { return hits; }

	public Rating getRating() { return rating; }
	public String getAuthor() { return author; }
	public String getTitle() { return title; }
	public String getLanguage() { return language; }
	public ArrayList<Catagories> getCatagories() { return catagories; }
	public ArrayList<Warning> getWarnings() { return warnings; }
	public ArrayList<AO3Tag> getFandoms() { return fandoms; }
	public ArrayList<AO3Tag> getCharacters() { return characters; }
	public ArrayList<AO3Tag> getAdditionalTags() { return additionalTags; }
	public boolean isComplete() { return complete; }
	public boolean isOneShot() { return oneShot; }
	public long getWorkIndex() { return workIndex; }
}
