package com.prince.epik.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client implements KeyListener{
	
	JPanel southPanel;
	JTextField messageBox;
	String name;
	DataOutputStream out;
	public Client(JPanel panel,String serverName, int port, String name){
		
		
		JFrame frame = new JFrame("Setup");
		    // get the user's input. note that if they press Cancel, 'name' will be null
		 System.out.printf("The user's name is '%s'.\n", name);
		 this.name = name;
	      try{
	         /* Open a ClientSocket and connect to ServerSocket */
	         System.out.println("Connecting to " + serverName + " on port " + port);
	         //creating a new socket for client and binding it to a port
	         Socket client = new Socket(serverName, port);
	         
	         southPanel = new JPanel();
	         southPanel.setBackground(Color.BLUE);
	         southPanel.setLayout(new BorderLayout());
	         
	         messageBox = new JTextField(30);
	         //messageBox.setMinimumSize(new Dimension(200,200));
	         //messageBox.setMaximumSize(new Dimension(200,200));
	         messageBox.requestFocusInWindow();
	         messageBox.addKeyListener(this);
	         
	         JTextArea chatBox = new JTextArea();
	         chatBox.setPreferredSize(new Dimension(100,100));
	         chatBox.setMinimumSize(new Dimension(100,100));
	         chatBox.setEditable(false);
	         chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
	         chatBox.setLineWrap(true);

	         JButton sendMessage = new JButton("Send Message");
	         JButton help = new JButton("Help?");
	         JPanel btnPanel = new JPanel();
	         btnPanel.setLayout(new GridLayout(2,1));
	         
	         help.addActionListener(new ActionListener(){
	        	 public void actionPerformed(ActionEvent event) {
					JFrame helpFrame = new JFrame();
					JPanel manual = new JPanel();
				    BufferedImage img;
					
					manual.add(Box.createHorizontalGlue());
				    
					try {                
						img = ImageIO.read(getClass().getResource("/images/manual.png"));
						JLabel man = new JLabel(new ImageIcon(img));
					    manual.add(man);
					} catch (IOException e) {
						System.out.println(e);
					}
					
					helpFrame.add(manual);
					helpFrame.pack();
					helpFrame.setSize(new Dimension(500,400));				
					helpFrame.setResizable(false);
					helpFrame.setLocationRelativeTo(null);
					helpFrame.setVisible(true);
	        	 }
	         });
	         
	         btnPanel.add(sendMessage);
	         btnPanel.add(help);
	         
	         southPanel.add(messageBox,BorderLayout.CENTER);
	         southPanel.add(btnPanel,BorderLayout.EAST);
	         southPanel.setMinimumSize(new Dimension(100,20));
	         southPanel.setMaximumSize(new Dimension(100,20));
	           // panel = new JPanel();
	            panel.setLayout(new BorderLayout());
	           // panel.setBackground(Color.RED);
	           // panel.setMinimumSize(new Dimension(600,600));
	            panel.add(new JScrollPane(chatBox), BorderLayout.CENTER);
	            panel.add((southPanel), BorderLayout.SOUTH);
	            panel.setSize(new Dimension(200,200));
	            

	         System.out.println("Just connected to " + client.getRemoteSocketAddress());
	         //panel.revalidate();
	         OutputStream outToServer = client.getOutputStream();
	         out = new DataOutputStream(outToServer);
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
	                    	 System.out.println("<" + name + ">:  " + messageBox.getText()+ "\n");
	                     }
	                     messageBox.setText("");
	                 }
	                 messageBox.requestFocusInWindow();
	             }
	         });
	       
	         //Thread sender = new Thread(new Sender(out,name)); //this thread is for a client to send message to server
	         
	         Thread receiver = new Thread(new Receiver(client,chatBox)); //this thread is for a client to receive message from the server

	         //sender.start();
	         receiver.start();

	         //client.close();

	      }catch(IOException e){
	         //e.printStackTrace();
	    	JOptionPane.showMessageDialog(frame, "Cannot find Server", "Error", JOptionPane.ERROR_MESSAGE);
	      	System.out.println("Cannot find Server");
	      	System.exit(0);
	      }catch(ArrayIndexOutOfBoundsException e){
	         //System.out.println("Usage: java Client <server ip> <port no.> '<your message name>'");
	      }
	   }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER){
			sendMsg(messageBox);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void sendMsg(JTextField messageBox){
		if (messageBox.getText().length() < 1) {
            // do nothing
        } else {
            try{
           	 out.writeUTF("<" + name + ">:  " + messageBox.getText()+ "\n");
            }catch(IOException e){
           	 System.out.println("Message not Sent\n");
           	 System.out.println("<" + name + ">:  " + messageBox.getText()+ "\n");
            }
            messageBox.setText("");
        }
        messageBox.requestFocusInWindow();
	}
}
