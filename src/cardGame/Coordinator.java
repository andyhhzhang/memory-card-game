package cardGame;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;
import javax.swing.event.*;
import javax.swing.*;

// This class creates the title page as well as the instructions page
// It will also launch the game
public class Coordinator extends JPanel{

	private Image titlecard;
	public static JFrame frame;
	public static boolean launch;
	private boolean instructionpage;
	private Card[] cards;
	private JButton play, instructions, backbutton;
	private static CountDownLatch latch;
	private static int time = 0;
	public static boolean level1 = true, level2, level3;

	
	public static void main(String[] args) {
		Coordinator c = new Coordinator();

		// makes sure that the thread is held here until the latch decreases
		latch = new CountDownLatch(1);
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// launch the first level
		if(launch == true && level1 == true){
			DrawingPanel panel = new DrawingPanel();

			CardManager cardManager = new CardManager(16, 4);
			cardManager.setDrawingPanel(panel);
			panel.addMouseListener(cardManager);

			cardManager.showAllCards();
			panel.repaint();
			
			try{
				Thread.sleep(3000); // show cards for 3 seconds
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
		
	}
	
	public Coordinator(){
		frame = new JFrame("Memory Card Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(1000, 800));
		frame.pack();
		this.setLayout(null);
		
		// button to play the game
		play = new JButton("Play");
		play.setFont(new Font("Serif", Font.PLAIN, 20));
		play.setBackground(Color.WHITE);
		playhandler playh = new playhandler();
		play.addActionListener(playh);
		// location and size of the play button
		play.setBounds(100, 400, 100, 40);
		this.add(play);
		
		// button to read the instructions
		instructions = new JButton("Instructions");
		instructions.setFont(new Font("Serif", Font.PLAIN, 20));
		instructions.setBackground(Color.WHITE);
		instructionhandler instructionh = new instructionhandler();
		instructions.addActionListener(instructionh);
		instructions.setBounds(100, 500, 170, 40);
		this.add(instructions);
		
		frame.getContentPane().add(this);
		frame.setVisible(true);
		frame.setResizable(false);

		// Back button from the instruction page
		backbutton = new JButton("Back");
		backbutton.setFont(new Font("Georgia", Font.PLAIN, 20));
		backbutton.setBackground(Color.WHITE);
		backhandler bhandler = new backhandler();
		backbutton.addActionListener(bhandler);
		backbutton.setBounds(100, 500, 100, 40);
	}

	// This method makes the title page as well as the instructions page
	public void paintComponent(Graphics g){
		// Makes the background of both the title page and the instructions page
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 1000, 800);

		if(instructionpage == true){
			g.setColor(Color.WHITE);
			Font a = new Font("Serif", Font.BOLD, 36);
			g.setFont(a);
			g.drawString("Instructions", 100, 150);
			Font f = new Font("Serif", Font.PLAIN, 20);
			g.setFont(f);
			g.drawString("1. Select two cards to turnover", 100, 220);
			g.drawString("2. If the pictures on the cards you selected match, you earn 20 points and the", 100, 270);
			g.drawString("two cards will disappear", 100, 295);
			g.drawString("3. If the pictures on the cards do not match, you lose 5 points and the two cards", 100, 345);
			g.drawString("will flip back over", 100, 370);
			g.drawString("4. Complete all 3 levels to finish the game", 100, 420);
			backbutton.setVisible(true);
			this.add(backbutton);
		}

		if(instructionpage == false){  
			// title page
			backbutton.setVisible(false);
			g.setColor(Color.WHITE);
			Font title = new Font("Serif", Font.BOLD, 70);
			g.setFont(title);
			g.drawString("Memory Card Game", 100, 150);
			Font author = new Font("Serif", Font.BOLD, 25);
			g.setFont(author);
			g.drawString("By: Andy Zhang", 100, 205);
			titlecard = Toolkit.getDefaultToolkit().getImage("Images/title-card.png");
			g.drawImage(titlecard, 550, 300, 275, 275, this);
		}

		// If the play button is pressed, the game will launch
		if(launch == true){
			launch = false;
			frame.setVisible(false);
			// Background for the cards to be on
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1000, 800);

		}    
	}

	// This method listens to whether the play button is pressed or not
	class playhandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			// This checks whether the play button is pressed
			if(command.equals("Play")){
				// Once the play button is pressed, the game launches and sets all the other buttons to be invisible
				launch = true;
				latch.countDown();
				
				play.setVisible(false);
				instructions.setVisible(false);
				backbutton.setVisible(false);
			}
			repaint();
		}
	}
	
	// This method listens to whether the play button is pressed or not
	class instructionhandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			// This checks whether the instructions button is pressed
			if(command.equals("Instructions")){
				// Once the play button is pressed, the instructions page is drawn, the play and instructions buttons disappear, and the back button appears
				instructionpage = true;
				play.setVisible(false);
				instructions.setVisible(false);
			}
			repaint();
		}
	}
	// This method listens to whether the back button is pressed or not
	class backhandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();
			// This checks whether the back button is pressed
			if(command.equals("Back")){
				// Once the play button is pressed, the title page is drawn, the play and instructions buttons reappear, and the back button disappears
				instructionpage = false;
				play.setVisible(true);
				instructions.setVisible(true);
			}
			repaint();
		}
	}
	
}
