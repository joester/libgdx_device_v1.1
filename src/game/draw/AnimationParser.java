package game.draw;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AnimationParser {
	private AnimationParser(){}
	
	final static void parser(String filepath){
		String data = "";
		File f = new File(filepath);
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader(f));
			int i;
			while((i = reader.read()) != -1){
				data += (char) i;
			}
			reader.close();
		}
		catch(IOException e){
			
		}
		String[] entries = data.split("\n");
		int numAnimators = Integer.parseInt(entries[0]);
		int index = 0;
		for(int currentAnimator = 0; currentAnimator < numAnimators; currentAnimator ++){
			Animator anim = new Animator(entries[index++], entries[index++]);
			int numAnims = Integer.parseInt(entries[index++]);
			for(int animIndex = 0; animIndex < numAnims; animIndex ++){
				String animName = entries[index++];
				int numFrames = Integer.parseInt(entries[index++]);
				int frameArray[][] = new int[numFrames][4];
				for(int frame = 0; frame < numFrames; frame ++){
					String args[] = entries[index++].split(" ");
					for(int i = 0; i < 4; i ++){
						frameArray[frame][i] = Integer.parseInt(args[i]);
					}
				}
				int numFramesToAdd = Integer.parseInt(entries[index++]);
				int frameOrder[] = new int[numFramesToAdd];
				String[] orderNumbers = entries[index++].split(" ");
				for(int i = 0; i < numFramesToAdd; i ++){
					frameOrder[i] = Integer.parseInt(orderNumbers[i]);
				}
				anim.add_animation(animName, frameArray, frameOrder);
			}
		}
	}
}
