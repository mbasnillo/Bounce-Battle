package com.miles.epik.window;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Menu {
	UDPClient udp;
	
	public Menu(int w, int h){
		
		JFrame frame = new JFrame("Setup");
		
		JPanel form = new JPanel(new GridLayout(5,1,100,100));
		JPanel title_panel = new JPanel();
		JPanel serverName_panel = new JPanel(new GridLayout(1,2));
		JPanel port_panel = new JPanel(new GridLayout(1,2));
		JPanel name_panel = new JPanel(new GridLayout(1,2));
		JButton btn = new JButton("Start game!");
		
		JTextArea serverName = new JTextArea("localhost");
		JTextArea port = new JTextArea("2000");
		JTextArea name = new JTextArea("Prince");
		
		//port.setBackground(Color.RED);
		title_panel.add(new JLabel("SETUP BOUNCE BATTLES!!!"));	
		serverName_panel.add(new JLabel("Enter IP Address: "));
		serverName_panel.add(serverName);
		port_panel.add(new JLabel("Enter port number: "));
		port_panel.add(port);
		name_panel.add(new JLabel("Enter your name: "));
		name_panel.add(name);
		
		form.add(title_panel);
		form.add(serverName_panel);
		form.add(port_panel);
		form.add(name_panel);
		form.add(btn);
		
		frame.add(form);
		
		frame.setPreferredSize(new Dimension(600, 600));
		frame.setMinimumSize(new Dimension(600, 600));
		frame.setMaximumSize(new Dimension(600, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		
		
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				if(serverName.getText().isEmpty() || port.getText().isEmpty() || name.getText().isEmpty()){
					JOptionPane.showMessageDialog(frame,
						    "Empty Text Boxes",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
				}
				else{
					frame.setVisible(false);

					Access access = new Access();
					new Window(access, 1000, 600, "Epic - Prototype", serverName.getText(), Integer.parseInt(port.getText()), name.getText());
					udp = new UDPClient(access, serverName.getText() , name.getText());
					udp.start();
					
					
				}
            }
		});
		
		
	}
	
}
