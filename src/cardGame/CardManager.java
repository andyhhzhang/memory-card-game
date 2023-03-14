package cardGame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class CardManager implements MouseListener {

	private Card[] cards;
	private int columnNumber, rowNumber;
	private int leftMargin, topMargin;
	private DrawingPanel panel;

	private boolean nonSelected = true, oneSelected, twoSelected;
	private Card firstSelected, secondSelected;
	private long timeSecondCardSelected;

	public static int score = 0;
	private boolean gameOver;

	// This is an array of the image names
	private String[] imageNames = { 
			"apple.png",
			"banana.png",
			"bank.png",
			"basketball.png",
			"burger.png",
			"campfire.jpg",
			"cat.png",
			"crossaint.png",
			"dog.png",
			"donald-duck.png",
			"football.png",
			"fries.png",
			"gold.png",
			"phone.png",
			"school.png",
			"snowman.png",
			"soccer.png",
			"turtle.png"
	};

	// This is the constructor which will create the number of cards, the images, and the column numbers
	public CardManager(int numberOfCards, int columnNumber){

		if(numberOfCards%2 == 1)
			numberOfCards++;
		if(numberOfCards/2 > imageNames.length) 
			numberOfCards = 2*imageNames.length;

		cards = new Card[numberOfCards];
		this.columnNumber = columnNumber;
		rowNumber = numberOfCards/columnNumber + (numberOfCards%columnNumber == 0 ? 0 : 1);

		ArrayList<String> names = new ArrayList<String>();
		for(int i=0; i<imageNames.length; i++)
			names.add(imageNames[i]);

		ArrayList<Image> images = new ArrayList<Image>();

		Image image;
		for(int i=0; i<numberOfCards/2; i++){
			String each = names.remove( (int)(names.size()*Math.random()) );
			image = new ImageIcon("Images/" + each).getImage();
			images.add( (int)(images.size()*Math.random()), image);
			images.add( (int)(images.size()*Math.random()), image);
		}

		leftMargin = (1000 - columnNumber*Card.SIZE)/2;
		topMargin = (800 - rowNumber*Card.SIZE)/2;

		int x, y;
		for(int i=0; i<cards.length; i++){
			x = leftMargin + i%columnNumber*Card.SIZE;
			y = topMargin + i/columnNumber*Card.SIZE;;

			cards[i] = new Card(x, y, images.get(i));
		}
		
	}

	// this method will show all of the cards on the board
	public void showAllCards(){
		for(int i=0; i<cards.length; i++) cards[i].show();
	}

	// this method will hide all of the cards on the board
	public void hideAllCards(){
		for(int i=0; i<cards.length; i++) cards[i].hide();
	}

	// this method will set the cards as well as the drawingpanel
	public void setDrawingPanel(DrawingPanel panel){
		this.panel = panel;
		panel.setCards(cards);
	}

	// this method determines whether the two pressed cards match or not
	public void match(){

		if(!twoSelected || System.currentTimeMillis() < timeSecondCardSelected+1000)
			return;

		if(firstSelected.hasSameImage(secondSelected)){
			firstSelected.matched();
			secondSelected.matched();
			score += 20;
			panel.setScore(score);
		}
		
		else {
			firstSelected.hide();
			secondSelected.hide();
			score -= 5;
			panel.setScore(score);
		}

		boolean allMatched = true;
		for(int i=0; i<cards.length; i++){
			if(!cards[i].getMatched()) {
				allMatched = false;
				break;
			}
		}

		// this determines whether all of the cards are matched and cleared off of the board
		if(allMatched) {
			
			if(Coordinator.level1 == true){
				Coordinator.level1 = false;
				Coordinator.level2 = true;
				
				// this will launch the second level
				CardManager cardManager = new CardManager(16, 4);
				cardManager.setDrawingPanel(panel);
				panel.addMouseListener(cardManager);

				cardManager.showAllCards();
				panel.repaint();
				
				try{
					Thread.sleep(2000); // show the cards for 2 seconds
				}
				catch(Exception e){}

				cardManager.hideAllCards();

				while(true){
					panel.repaint();
					cardManager.match();
					try{
						Thread.sleep(30);
					}
					catch(Exception e){}
				}
			}

			else if(Coordinator.level2 == true){
				Coordinator.level2 = false;
				Coordinator.level3 = true;
				
				// this will launch the third level
				CardManager cardManager = new CardManager(16, 4);
				cardManager.setDrawingPanel(panel);
				panel.addMouseListener(cardManager);

				cardManager.showAllCards();
				panel.repaint();
				
				try{
					Thread.sleep(1000); // show the cards for 1 second
				}
				catch(Exception e){}

				cardManager.hideAllCards();

				while(true){
					panel.repaint();
					cardManager.match();
					try{
						Thread.sleep(30);
					}
					catch(Exception e){}
				}
			}

			// This will create the end panel after the third level is completed
			else if(Coordinator.level3 == true){
				EndPanel ep = new EndPanel();
			}
			
		}

		nonSelected = true;
		twoSelected = false;

	}

	// determine which card is pressed and flip over that card
	public void mousePressed(MouseEvent e) {

		if(twoSelected) return;

		for(int i=0; i<cards.length; i++){
			if(cards[i].isSelected(e)){

				if(nonSelected){
					nonSelected = false;
					oneSelected = true;
					firstSelected = cards[i];
					firstSelected.show();

					return;
				}
				else if(oneSelected){
					if(cards[i].equals(firstSelected)) return;
					oneSelected = false;
					twoSelected = true;
					secondSelected = cards[i];

					secondSelected.show();

					timeSecondCardSelected = System.currentTimeMillis();
					return;
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}

