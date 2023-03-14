package cardGame;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;

import javax.swing.event.*;
import javax.swing.*;

// this class will create the end panel as well as display the final score and some advice
public class EndPanel extends JPanel{
	
	private JFrame endframe;
	private JButton quit;
	private Image dancingcard;
	
	public EndPanel(){
		endframe = new JFrame("Memory Card Game");
		endframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		endframe.pack();
		endframe.setBounds(DrawingPanel.frame.getX(), DrawingPanel.frame.getY(), 1000, 800);
		
		DrawingPanel.frame.setVisible(false);
		
		this.setLayout(null);
		
		endframe.getContentPane().add(this);
		endframe.setVisible(true);
		endframe.setResizable(false);
		
		quit = new JButton("Quit");
		quit.setFont(new Font("Georgia", Font.PLAIN, 20));
		quithandler quith = new quithandler();
		quit.addActionListener(quith);
		// location and size of the back button
		quit.setBounds(420, 550, 100, 40);
		this.add(quit);
	}
	
	// Write the final score
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1000, 1000);
		g.setColor(Color.BLACK);
		Font f = new Font("Serif", Font.PLAIN, 50);
		g.setFont(f);
		
		g.drawString("Final Score: " + CardManager.score, 285, 200);
		
	}	
	
	// this will determine whether the quit button is pressed or not
	class quithandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();
			if(command.equals("Quit")){
				System.exit(0);
			}
			repaint();
		}
	}

	
}			