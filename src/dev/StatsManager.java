package dev;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;

public class StatsManager extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel[] tag = {new JLabel("PackName"), new JLabel("Damage"),
			new JLabel("HP"), new JLabel("Speed"), new JLabel("Power"), new JLabel("Range")};
	JTextField[] input = new JTextField[6];
	JButton commit = new JButton("Commit");
	ArrayList<StatsObject> objs = new ArrayList<StatsObject>();
	
	public StatsManager(){
		setTitle("Settin' Stats");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().setLayout(new GridLayout(7,2));
		
		for(int i = 0; i < input.length; i ++){
			input[i] = new JTextField();
			add(tag[i]);
			add(input[i]);
		}
		commit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				try{
					File file = new File("stats.jcf");
					if(!file.exists()){
						file.createNewFile();
					}
					StatsObject obj = new StatsObject(
							input[0].getText(),
							Integer.parseInt(input[1].getText()),
							Float.parseFloat(input[2].getText()),
							Float.parseFloat(input[3].getText()),
							Float.parseFloat(input[4].getText()),
							Float.parseFloat(input[5].getText())
					);
					objs.add(obj);
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("stats.jcf"));
					out.writeObject(objs);
					out.close();
					JOptionPane.showMessageDialog(null, "Stats set.");
				}
				catch(IOException ex){
					System.out.println("Stats failed at file creation");
				}
			}			
		});
		add(commit);
		pack();
	}	
}
