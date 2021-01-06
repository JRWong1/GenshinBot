package GenshinBot;

public class Item {
	
	public boolean isChar;
	public int starCount;
	public String name;
	
	public String imageLink;
	
	public Item(boolean isChar, int starCount, String name) {
		this.isChar = isChar;
		this.starCount = starCount;
		this.name = name;
	}
	
	public Item(boolean isChar, int starCount, String name, String imageLink) {
		this(isChar, starCount, name);
		this.imageLink = imageLink;
	}
	
	public String toString() {
		if(isChar) {
			return "Character: " + this.name + " - " + this.starCount + " Stars";
		}
		return "Weapon: " + this.name + " - " + this.starCount + " Stars";
	}
}
