package com.miles.epik.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	
	public Window(Access access, int w, int h, String title, String serverName, int port, String name){
		this.playerName = name;
		this.access = access;
		JPanel chat = new JPanel();
		chat.setPreferredSize(new Dimension(300,200));

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

		
		JFrame frame = new JFrame(title);
		frame.setLayout(new BorderLayout());
//		frame.add(game, BorderLayout.CENTER);
		
		start.addActionListener(new ActionListener(){
	       	 public void actionPerformed(ActionEvent event) {
	     		Game game = new Game();
	     		game.setServer(serverName);
	     		game.setName(name);
	     		game.setPreferredSize(new Dimension(w, h));
	     		game.setMinimumSize(new Dimension(w, h));
	     		game.setMaximumSize(new Dimension(w, h));
	     		scrollPane.setVisible(false);
	    		frame.add(game, BorderLayout.CENTER);
	    		System.out.println("GAME START");
	    		frame.revalidate();
	     		game.start();
	         }
	    });

		frame.add(center, BorderLayout.CENTER);
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
	public void updateTable(String message){
		String player[] = message.split(":");
		int i = 0;
		
		for (int j = 0 ; j < dtm.getRowCount(); j++){
			dtm.removeRow(j);
		}
		//Object[] newData;
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
