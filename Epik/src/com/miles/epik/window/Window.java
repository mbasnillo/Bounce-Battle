package com.miles.epik.window;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.prince.epik.chat.Client;

public class Window {
	
	public Window(int w, int h, String title, Game game){
		game.setPreferredSize(new Dimension(w, h));
		game.setMinimumSize(new Dimension(w, h));
		game.setMaximumSize(new Dimension(w, h));
		JPanel chat = new JPanel();
		chat.setMinimumSize(new Dimension(600,600));
		
		JFrame frame = new JFrame(title);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.add(chat, BorderLayout.EAST);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		

		game.start();

		new Client(chat);
		frame.pack();
		
		
	}
}
