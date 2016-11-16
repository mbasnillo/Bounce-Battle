package com.miles.epik.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.prince.epik.chat.Client;

public class Window {
	
	public Window(int w, int h, String title, String serverName, int port, String name){

		JPanel chat = new JPanel();
		chat.setPreferredSize(new Dimension(300,200));

		new Client(chat, serverName,  port,  name);
		
		String[] columnNames = {"Player", "Status"};
		Object[][] data = {{name,"Not Ready"}};
		
		JTable table = new JTable(data, columnNames);
		
		JPanel southPanel = new JPanel(new GridLayout(1,2));
		JButton ready = new JButton("Ready");
		JButton start = new JButton("Start!");
		southPanel.add(ready);
		southPanel.add(start);
		start.setEnabled(false);
		
		ready.addActionListener(new ActionListener(){
       	 public void actionPerformed(ActionEvent event) {
                for(int j = 0 ; j < data.length; j++){
                	if(data[j][0] == name){
                		data[j][1] = "Ready";
                		start.setEnabled(true);
                	}
                }
            }
        });
		
		
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		

		
		JFrame frame = new JFrame(title);
		frame.setLayout(new BorderLayout());
//		frame.add(game, BorderLayout.CENTER);
		
		start.addActionListener(new ActionListener(){
	       	 public void actionPerformed(ActionEvent event) {
	     		Game game = new Game();
	     		
	     		game.setPreferredSize(new Dimension(w, h));
	     		game.setMinimumSize(new Dimension(w, h));
	     		game.setMaximumSize(new Dimension(w, h));

	    		frame.add(game, BorderLayout.CENTER);
	     		game.start();
	         }
	    });

		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(chat, BorderLayout.EAST);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//game.start();
		//frame.pack();
		
	}
}
