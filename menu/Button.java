package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	
	Rectangle container;
	String text;
	float x, y, width, height;
	BaseState screen;
	Sprite s = new Sprite(new Texture("data/button.png"));
	
	public Button(float x, float y, float width, float height, BaseState screen){
		this.x = x;
		this.y = y;
		container = new Rectangle(x, (Gdx.graphics.getHeight()-y), s.getWidth(), s.getHeight() * .6f);
		this.screen = screen;
		s.setScale(.6f);
	}
	
	public void render(SpriteBatch batch){
		//s.draw(batch);
	}
	
	public boolean update(){
		if(Gdx.input.justTouched()){
			if(container.contains(Gdx.input.getX(), (Gdx.input.getY() + 68))){
				System.out.println("DONE");
				return true;
			}
		}
		return false;
	}
	
}
