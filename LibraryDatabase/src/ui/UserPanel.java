package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UserPanel extends JPanel{

	private UserFrame parentFrame;
	
	public UserPanel(UserFrame uf){
		
		super(new GridLayout(5,1));
		parentFrame = uf;
		
		JLabel label = new JLabel("Welcome!");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
        JButton clerkButton = new JButton("Clerk");
        clerkButton.setVerticalTextPosition(AbstractButton.CENTER);
        clerkButton.addActionListener(new ActionListener() {
        	 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                parentFrame.dispose();
                new ClerkFrame();
            }
        });

		add(label);
		add(clerkButton);
	}
	

}
