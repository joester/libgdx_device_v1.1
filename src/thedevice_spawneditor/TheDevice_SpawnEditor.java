package thedevice_spawneditor;

import javax.swing.JFrame;

public class TheDevice_SpawnEditor{
    public static void changeTitle(String filename){
	jframe.setTitle(filename == null ? "The Device: Creature Spawn Point Editor" : "Spawn Editor - "+filename);
    }
    static JFrame jframe;
    public static void main(String[] args){
    	jframe = new JFrame();
        jframe.setSize(_G.screenWidth,_G.screenHeight);
	changeTitle(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jframe.setResizable(false);
	Center center = new Center();
	center.start();
        jframe.add(center);
        jframe.setVisible(true);
    }
}
