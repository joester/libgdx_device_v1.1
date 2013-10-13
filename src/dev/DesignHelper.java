package dev;

import game.GameStats;
import game.UI.UI;
import game.objects.GameObject;
import game.objects.enemy.MonsterManager;
import game.room.Room;
import game.objects.Device;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DesignHelper extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton mon1 = new JButton("Fuzz 1");
	JButton mon2 = new JButton("Fuzz 2");
	JButton mon3 = new JButton("Fuzz 3");
	JButton mon4 = new JButton("Plant 1");
	JButton mon5 = new JButton("Plant 2");
	JButton addMine = new JButton("Add Mine");
	JButton addVort = new JButton("Add Vortex");
	JButton addNuke = new JButton("Add N00k");
	JRadioButton invinc = new JRadioButton("God Mode On");
	JRadioButton xpSpawn = new JRadioButton("Toggle XP");
	JRadioButton monsterSpawn = new JRadioButton("Toggle Fuzzie");
	
	
	public DesignHelper(final MonsterManager m, final Room r, final GameObject g, final GameStats stats, final UI ui) {
		setTitle("Design Helper v0.2a");
		getContentPane().setLayout(new GridLayout(8,1));
		setSize(500, 1000);
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
		mon4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				new MonsterEditor(m.spawnMonster(4, 0, 0), r);
			}
		});
		mon5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				new MonsterEditor(m.spawnMonster(5, 0, 0), r);
			}
		});
		addMine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				stats.addMine();
			}
		});
		addVort.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				stats.addVortex();
			}
		});
		addNuke.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				
			}
		});
		invinc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				g.toggleGodMode();
			}
		});
		xpSpawn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				((Device) g).toggleSpawnXP();
			}
		});
		monsterSpawn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				r.toggleSpawn();
			}
		});
		add(mon1);
		add(mon2);
		add(mon3);
		add(mon4);
		add(mon5);
		add(addMine);
		add(addVort);
		add(addNuke);
		add(invinc);
		add(xpSpawn);
		add(monsterSpawn);
		pack();
	}
}
