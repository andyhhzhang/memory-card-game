package cardGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

// this class will get the back image of the card
// it will also set the card location and image and determine whether the two flipped over cards match or not
public class Card {
	
	private static Image backImage = new ImageIcon("Images/backside.jpg").getImage();
	public static final int SIZE;
	private static Color borderColor;
	private static int inset, padding;
	
	private Image image;
	private int x, y;
	private boolean shouldReveal;
	private boolean matched;
	
	// initialize Card dimensions
	static {
		 SIZE = 100;
		 inset = 10;
		 padding = 5;
	}
	
	// Set coordinates and image for the card
	public Card(int x, int y, Image image){
		this.x = x;
		this.y = y;
		this.image = image;
	}
	
	// Determine if the two cards are a pair
	public boolean hasSameImage(Card card){
		return image.equals(card.image);
	}
	
	public boolean isSelected(MouseEvent e){
		if(matched) return false;
		return e.getX()>x && e.getX()<x+SIZE && e.getY()>y & e.getY()<y+SIZE;
	}
	
	public void matched(){ matched = true; }
	public boolean getMatched(){ return matched; }
	
	public void show(){ shouldReveal = true; }
	public void hide(){ shouldReveal = false; }
	
	// Draw image and its border
	public void draw(Graphics g){
		if(matched){
			g.setColor(Color.BLACK);
			g.drawRect(x, y, SIZE, SIZE);
			return;
		}
		
		if(shouldReveal){
			g.drawImage(image, x+inset, y+inset, SIZE-2*inset, SIZE-2*inset, null);
		}
		else {
			g.drawImage(backImage, x+inset, y+inset, SIZE-2*inset, SIZE-2*inset, null);
		}
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y, SIZE, SIZE);
	}
	
}
