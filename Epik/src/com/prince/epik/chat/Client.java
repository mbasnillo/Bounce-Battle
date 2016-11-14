package com.prince.epik.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {
	public Client(JPanel panel){
		 JFrame frame = new JFrame("Setup");

		    // prompt the user to enter their name
		 String serverName = JOptionPane.showInputDialog(frame, "Enter Server IP address: ");
		 int port = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter port number: "));
		 String name = JOptionPane.showInputDialog(frame, "What's your name? ");
		 
		    // get the user's input. note that if they press Cancel, 'name' will be null
		 System.out.printf("The user's name is '%s'.\n", name);
	      try{
	         /* Open a ClientSocket and connect to ServerSocket */
	         System.out.println("Connecting to " + serverName + " on port " + port);
	         //creating a new socket for client and binding it to a port
	         Socket client = new Socket(serverName, port);
	         
	         JPanel southPanel = new JPanel();
	         southPanel.setBackground(Color.BLUE);
	         southPanel.setLayout(new BorderLayout());
	         
	         JTextField messageBox = new JTextField(30);
	         messageBox.setMinimumSize(new Dimension(200,200));
	         messageBox.setMaximumSize(new Dimension(200,200));
	         messageBox.requestFocusInWindow();
	         
	         JTextArea chatBox = new JTextArea();
	         chatBox.setPreferredSize(new Dimension(300,300));
	         chatBox.setMinimumSize(new Dimension(500,500));
	         chatBox.setMaximumSize(new Dimension(500,500));
	         chatBox.setEditable(false);
	         chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
	         chatBox.setLineWrap(true);

	         JButton sendMessage = new JButton("Send Message");
	         
	         
	         
	         
	         southPanel.add(messageBox,BorderLayout.CENTER);
	         southPanel.add(sendMessage,BorderLayout.EAST);
	         
	           // panel = new JPanel();
	            panel.setLayout(new BorderLayout());
	            panel.setBackground(Color.RED);
	            panel.setMinimumSize(new Dimension(600,600));
	            panel.add(new JScrollPane(chatBox), BorderLayout.CENTER);
	            panel.add(new JScrollPane(southPanel), BorderLayout.SOUTH);
//	            frame.pack();
//		 		
//	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setResizable(false);
//	            frame.setLocationRelativeTo(null);
//	            frame.setVisible(true);
//	            frame.setTitle("Chat Box: <"+ name + ">");
	         System.out.println("Just connected to " + client.getRemoteSocketAddress());

	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         out.writeUTF(name+" has connected!\n");
	         sendMessage.addActionListener(new ActionListener(){
	        	 public void actionPerformed(ActionEvent event) {
	                 if (messageBox.getText().length() < 1) {
	                     // do nothing
	                 } else {
	                     try{
	                    	 out.writeUTF("<" + name + ">:  " + messageBox.getText()+ "\n");
	                     }catch(IOException e){
	                    	 System.out.println("Message not Sent\n");
	                     }
	                     messageBox.setText("");
	                 }
	                 messageBox.requestFocusInWindow();
	             }
	         });
	       
	         //Thread sender = new Thread(new Sender(out,name)); //this thread is for a client to send message to server
	         Thread receiver = new Thread(new Receiver(client,chatBox)); //this thread is for a client to recienve message from the server

	         //sender.start();
	         receiver.run();

	         client.close();

	      }catch(IOException e){
	         //e.printStackTrace();
	    	JOptionPane.showMessageDialog(frame, "Cannot find Server", "Error", JOptionPane.ERROR_MESSAGE);
	      	System.out.println("Cannot find Server");
	      	System.exit(0);
	      }catch(ArrayIndexOutOfBoundsException e){
	         System.out.println("Usage: java Client <server ip> <port no.> '<your message name>'");
	      }
	   }
}
