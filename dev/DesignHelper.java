package dev;

import game.objects.GameObject;
import game.objects.enemy.MonsterManager;
import game.room.Room;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DesignHelper extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton mon1 = new JButton("Monster 1");
	JButton mon2 = new JButton("Monster 2");
	JButton mon3 = new JButton("Monster 3");
	JRadioButton invinc = new JRadioButton("God Mode On");
	
	
	public DesignHelper(final MonsterManager m, final Room r, final GameObject g) {
		setTitle("Design Helper v0.1a");
		getContentPane().setLayout(new GridLayout(4,1));
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		mon1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				new MonsterEditor(m.spawnMonster(1, 0, 0), r);
			}
		});
		mon2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				new MonsterEditor(m.spawnMonster(2, 0, 0), r);
			}
		});
		mon3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				new MonsterEditor(m.spawnMonster(3, 0, 0), r);
			}
		});
		invinc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				g.toggleGodMode();
			}
		});
		add(mon1);
		add(mon2);
		add(mon3);
		add(invinc);
		pack();
	}
}
