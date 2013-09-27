package dev;

import game.objects.enemy.Enemy;
import game.room.Room;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MonsterEditor extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel[] des = {new JLabel("HP"), new JLabel("Speed"), new JLabel("Power"),
			new JLabel("Range"), new JLabel("Damage")};
	JTextField[] att = {new JTextField(), new JTextField(), new JTextField(),
			new JTextField(), new JTextField()};
	JButton commit = new JButton("Spawn");

	public MonsterEditor(final Enemy e, final Room r) {
		setTitle("Creation of Monster w/ ID: " + e.getID());
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().setLayout(new GridLayout(6, 1));
		att[0].setText(Float.toString(e.getHp()));
		att[1].setText(Float.toString(e.get_speed()));
		att[2].setText(Float.toString(e.getAttack().power));
		att[3].setText(Float.toString(e.getAttack().range));
		att[4].setText(Integer.toString(e.getAttack().damage));
		for(int i = 0; i <= 4; i ++){
			add(des[i]);
			add(att[i]);
		}
		commit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				e.setHp(Float.parseFloat(att[0].getText()));
				e.set_speed(Float.parseFloat(att[1].getText()));
				e.getAttack().power = Float.parseFloat(att[2].getText());
				e.getAttack().range = Float.parseFloat(att[3].getText());
				e.getAttack().damage = Integer.parseInt(att[4].getText());
				r.spawn_object(e);
				setVisible(false);
				dispose();
			}
		});
		add(commit);
		pack();
	}
}
