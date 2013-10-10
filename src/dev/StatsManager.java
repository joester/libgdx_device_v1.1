package dev;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class StatsManager extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel[] tag = {};
	JTextField[] input = new JTextField[5];
	JButton commit = new JButton("Commit");
	
	public StatsManager(){
		setTitle("Settin' Stats");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().setLayout(new GridLayout(6, 1));
		for(int i = 0; i < input.length; i ++){
			input[i] = new JTextField();
			add(tag[i]);
			add(input[i]);
		}
		commit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				try{
					File file = new File("./../stats.txt");
					if(!file.exists()){
						file.createNewFile();
					}
				}
				catch(IOException ex){
					System.out.println("Stats failed at file creation");
				}
			}
		});
		pack();
	}
	
}
