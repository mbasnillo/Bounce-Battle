package com.miles.epik.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.prince.epik.chat.Client;

public class Window {
	JTable table;
	DefaultTableModel dtm = new DefaultTableModel(0,0);
	UDPClient udp;
	Access access;
	Object[][] data;
	JPanel center;
	String playerName;
	JButton start;
	boolean gameStarted = false;
	
	public Window(Access access, int w, int h, String title, String serverName, int port, String name){
		this.playerName = name;
		this.access = access;
		JPanel chat = new JPanel();
		chat.setPreferredSize(new Dimension(275,200));

		new Client(chat, serverName,  port,  name);
		
		String[] columnNames = {"Player", "Status"};
		//Object[] data = {name,"Not Ready"};
		
		this.table = new JTable();
		dtm.setColumnIdentifiers(columnNames);
		this.table.setModel(dtm);
		//dtm.addRow(data);
		this.access.setWindow(this);
		JPanel southPanel = new JPanel(new GridLayout(1,2));
		JButton ready = new JButton("Ready");
		this.start = new JButton("Start!");
		southPanel.add(ready);
		southPanel.add(start);
		start.setEnabled(false);

		this.center = new JPanel();
		
		ready.addActionListener(new ActionListener(){
       	 public void actionPerformed(ActionEvent event) {
               for(int j = 0; j < dtm.getRowCount();j++){
            	   if(dtm.getValueAt(j, 0).equals(playerName)){
            		   dtm.setValueAt("Ready", j, 1);
            		   udp.send("READY "+playerName);
            		   break;
            	   }
               }
               boolean flag = true;
               for(int j = 0; j < dtm.getRowCount();j++){
            	   if(dtm.getValueAt(j, 1).equals("Not Ready")){
            		   flag = false;
            		   break;
            	   }
               }
               start.setEnabled(flag);
            }
        });
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		center.add(scrollPane);
//
	
		JPanel manual = new JPanel();
		manual.add(Box.createHorizontalGlue());
	    JLabel manText = new JLabel("MECHANICS");
	    JLabel manText2 = new JLabel();
	    
	    manText2.setText("<html><center>Use arrow keys to move around<br>"
	    		+ "Bash enemies off the screen<br>"
	    		+ "Last player standing wins!</center></html>");
	    
	    manText.setFont(new Font("Helvetica",1,15));
	    manText2.setFont(new Font("Helvetica",0,15));
	    manText.setBorder(new EmptyBorder(10,10,10,10));
	    manText2.setBorder(new EmptyBorder(10,10,10,10));
	    
	    
	    manual.add(manText);
	    manual.add(manText2);
		
//		
		
		JFrame frame = new JFrame(title);
		frame.setLayout(new BorderLayout());
//		frame.add(game, BorderLayout.CENTER);
		
		
		frame.add(manual, BorderLayout.CENTER);
		frame.add(center, BorderLayout.WEST);
		frame.add(chat, BorderLayout.EAST);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.setPreferredSize(new Dimension(w, h));
 		frame.setMinimumSize(new Dimension(w, h));
 		frame.setMaximumSize(new Dimension(w, h));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		start.addActionListener(new ActionListener(){
	       	 public void actionPerformed(ActionEvent event) {
	       		if(gameStarted == false){
	       			gameStarted = true;
	       			Game game = new Game();
	       			game.setAccess(access);
	       			udp.setGame(game);
	       			game.setUDP(udp);
		     		game.setServer(serverName);
		     		game.setName(name);
		     		//game.setPreferredSize(new Dimension(w, h));
		     		//game.setMinimumSize(new Dimension(w, h));
		     		//game.setMaximumSize(new Dimension(w, h));
		     		//scrollPane.setVisible(false);
		     		frame.remove(center);
		     		frame.remove(manual);
		    		frame.add(game, BorderLayout.CENTER);
		    		udp.send("GAME START");
		    		frame.revalidate();
		    		frame.repaint();
		     		game.start();
	       		}
	         }
	    });
		//game.start();
		//frame.pack();
		
	}
	public void updateTable(String message){
		//String player[] = message.split(":");
		int i = dtm.getRowCount();
		
		//Clear table, first.
		if (dtm.getRowCount() > 0) {
			for (i=i-1; i>-1; i--) {
				dtm.removeRow(i);
		 	}
		}
		
		String player[] = message.split(":");
		
		for(String p : player){
			
			i++;
			String elements[] = p.split(" ");
			String name = elements[0];
			//if(name.equals(playerName)){ dtm.removeRow(arg0);;}
			Object[] newData = {name,"Not Ready"};
			dtm.addRow(newData);
			
		}
		center.revalidate();
		center.repaint();
	}
	public void setUDPClient(UDPClient udp){
		this.udp=udp;
	}
	public void setPlayerReady(String name){
		for (int j = 0 ; j < dtm.getRowCount(); j++){
			if(dtm.getValueAt(j, 0).equals(name)){
     		   dtm.setValueAt("Ready", j, 1);
     		   break;
     	   }
		}
		 boolean flag = true;
         for(int j = 0; j < dtm.getRowCount();j++){
      	   if(dtm.getValueAt(j, 1).equals("Not Ready")){
      		   flag = false;
      		   break;
      	   }
         }
         start.setEnabled(flag);
	}
}
