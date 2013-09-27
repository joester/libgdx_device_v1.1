package sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.FileHandle;

public class SoundSystem {
	
	/*
	 * For each sound file, write an entry here designating its index.
	 * Give it a name that will be used by other components of the Game.
	 * Store an positive integer that will index that sound in the 
	 * associated array.  
	 */
	public static final int
	//	NAME_OF_SOUND = unique index,
		mlevel = 0,
		monster1_1_death = 1,
		monster1_2_death = 2,
		monster1_3_death = 3,
		monster1_1_bite = 4,
		monster1_2_bite = 5,
		monster1_3_attack = 6,
		monster1_2_damage = 7,
		monster1_3_damage = 8,
		monster1_2_charge = 9,
		monster1_2_collide = 10,
		monster1_3_roar = 11,
		monster1_3_slam = 12,
		
		nuke = 10,
		mine = 11,
		vortex = 12,
		
		xp = 21,
		
		hit = 31,
		spawn = 32,
		plevel = 33,
		smack0 = 130,
		smack1 = 131,
		smack2 = 132,
		smack3 = 133,
		smack4 = 134,
		smack5 = 135,
		smack6 = 136,
		smack7 = 137,
		
		laugh = 41,
		
		buttonh = 99,
		buttonl = 98;
	/*
	 * These function identically to the sound indices.  Note that
	 * while no two Sounds can have the same index, and no two Music
	 * can have the same index, a Music and a Sound may share an index.
	 */
	public static final int
	//	NAME_OF_MUSIC = unique index,
		menu_background_music = 1,
		game_background_music = 2;
		
	
	private Sound[] soundList = new Sound[64];
	private Music[] musicList = new Music[4];
	
	
	
	/*
	 * Places the sound stored in file into the sound list.
	 * Common usage would be:
	 * 		addSound(NAME, Gdx.files.internal("name.mp3"));
	 */
	public void addSound(int name, FileHandle file) {
		while (name >= soundList.length) {
			Sound[] temp = new Sound[2*soundList.length];
			System.arraycopy(soundList, 0, temp, 0, soundList.length);
			soundList = temp;
		}
		soundList[name] = Gdx.audio.newSound(file);
	}
	
	/*
	 * Places the music stored in file into the music list.
	 * Common usage would be:
	 * 		addMusic(NAME, Gdx.files.internal("name.mp3"));
	 */
	public void addMusic(int name, FileHandle file) {
		while (name >= musicList.length) {
			Music[] temp = new Music[2*musicList.length];
			System.arraycopy(musicList, 0, temp, 0, musicList.length);
			musicList = temp;
		}
		musicList[name] = Gdx.audio.newMusic(file);
	}
	
	/*
	 * Plays the sound 'name' for its duration.  Multiple instances of 
	 * the same sound may be playing at once.
	 */
	public void playSound(int name) {
		soundList[name].play();
	}
	
	/*
	 * Plays 'name' with volume multiplied by 'volume', and pitch
	 * multiplied by 'pitch'.
	 */
	public void playSound(int name, float volume, float pitch) {
		long i = soundList[name].play();
		soundList[name].setVolume(i, volume);
		soundList[name].setPitch(i, pitch);
	}
	
	/*
	 * Stops all instances of 'name' that are playing.
	 */
	public void stopSound(int name) {
		soundList[name].stop();
	}
	
	/*
	 * Plays the music 'name' for its duration.  Only one instance of the
	 * same music may be playing at once.
	 */
	public void playMusic(int name) {
		musicList[name].play();
	}
	
	/*
	 * Plays 'name', looping until stopped.
	 */
	public void playMusicLooping(int name) {
		musicList[name].play();
		musicList[name].setLooping(true);
	}
	
	/*
	 * Multiplies the volume of 'name' by 'volume'.
	 */
	public void setMusicVolume(int name, float volume) {
		musicList[name].setVolume(volume);
	}
	
	/*
	 * Pauses 'name' if it is playing.  Place in the track is preserved;
	 * when playMusic(name) is called again it will resume from the same
	 * place.
	 */
	public void pauseMusic(int name) {
		musicList[name].pause();
	}
	
	/*
	 * Stops 'name' if it is playing.  Place in the track is not
	 * preserved.
	 */
	public void stopMusic(int name) {
		musicList[name].stop();
	}
	
	
	/*
	 * Frees the assets for disposal.
	 */
	public void dispose() {
		for (Sound s : soundList)
			if (s != null)
				s.dispose();
		for (Music m : musicList)
			if (m != null)
				m.dispose();
	}
	
	public void init() {
		addMusic(menu_background_music, Gdx.files.internal("data/sounds/menu/title.mp3"));
		addMusic(game_background_music, Gdx.files.internal("data/sounds/menu/game.mp3"));
		
		addSound(mlevel, Gdx.files.internal("data/sounds/monster/level.mp3"));
		addSound(monster1_1_death, Gdx.files.internal("data/sounds/monster/m1death.mp3"));
		addSound(monster1_2_death, Gdx.files.internal("data/sounds/monster/m2death.mp3"));
		addSound(monster1_3_death, Gdx.files.internal("data/sounds/monster/m3death.mp3"));
		addSound(monster1_1_bite, Gdx.files.internal("data/sounds/monster/m1bite.mp3"));
		addSound(monster1_2_bite, Gdx.files.internal("data/sounds/monster/m2bite.mp3"));
		addSound(monster1_3_attack, Gdx.files.internal("data/sounds/monster/m3attack.mp3"));
		addSound(monster1_2_damage, Gdx.files.internal("data/sounds/monster/m2damage.mp3"));
		addSound(monster1_3_damage, Gdx.files.internal("data/sounds/monster/m3damage.mp3"));
		addSound(monster1_2_charge, Gdx.files.internal("data/sounds/monster/m2charge.mp3"));
		addSound(monster1_2_collide, Gdx.files.internal("data/sounds/monster/m2collide.mp3"));
		addSound(monster1_3_roar, Gdx.files.internal("data/sounds/monster/m3roar.mp3"));
		addSound(monster1_3_slam, Gdx.files.internal("data/sounds/monster/m3slam.mp3"));
		
		addSound(nuke, Gdx.files.internal("data/sounds/items/nuke.mp3"));
		addSound(mine, Gdx.files.internal("data/sounds/items/mine.mp3"));
		addSound(vortex, Gdx.files.internal("data/sounds/items/vortex.mp3"));
		
		addSound(xp, Gdx.files.internal("data/sounds/pickup/xp.mp3"));
		
		addSound(hit, Gdx.files.internal("data/sounds/game/hit.mp3"));
		addSound(spawn, Gdx.files.internal("data/sounds/game/spawn.mp3"));
		addSound(plevel,Gdx.files.internal("data/sounds/pickup/level.mp3"));
		addSound(smack0, Gdx.files.internal("data/sounds/game/smack0.mp3"));
		addSound(smack1, Gdx.files.internal("data/sounds/game/smack1.mp3"));
		addSound(smack2, Gdx.files.internal("data/sounds/game/smack2.mp3"));
		addSound(smack3, Gdx.files.internal("data/sounds/game/smack3.mp3"));
		addSound(smack4, Gdx.files.internal("data/sounds/game/smack4.mp3"));
		addSound(smack5, Gdx.files.internal("data/sounds/game/smack5.mp3"));
		addSound(smack6, Gdx.files.internal("data/sounds/game/smack6.mp3"));
		addSound(smack7, Gdx.files.internal("data/sounds/game/smack7.mp3"));
		
		addSound(laugh, Gdx.files.internal("data/sounds/gameover/laugh.mp3"));
		
		addSound(buttonh,Gdx.files.internal("data/sounds/buttonh.mp3"));
		addSound(buttonl,Gdx.files.internal("data/sounds/buttonl.mp3"));
	}
}