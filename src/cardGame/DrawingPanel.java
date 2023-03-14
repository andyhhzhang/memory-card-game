package cardGame;

import java.awt.*;

import javax.swing.*;

public class DrawingPanel extends JPanel {

	public static JFrame frame;
	private JLabel scoreLabel;
	
	private Card[] cards;
	
	public DrawingPanel(){
		frame = new JFrame("Memory Card Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setBounds(Coordinator.frame.getX(), Coordinator.frame.getY(), 1000, 800);
		
		this.setLayout(null);
		scoreLabel = new JLabel();
		scoreLabel.setBounds(440, 80, 225, 50);
		scoreLabel.setFont(new Font("Serif", Font.BOLD, 24));
		scoreLabel.setText("Score: 0");
		this.add(scoreLabel);
		
		frame.getContentPane().add(this);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public void setScore(int score){ 
		scoreLabel.setText("Score: " + score);
	}
	
	public void setCards(Card[] cards){
		this.cards = cards;
	}
	
	public void paintComponent(Graphics g){ 
		
		if(cards == null) return;
		
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for(int i=0; i<cards.length; i++){
			cards[i].draw(g);
		}
	}
	
	
}