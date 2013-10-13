package game.controls;


import java.util.HashSet;
import java.util.Iterator;

import com.badlogic.gdx.InputProcessor;

public class Controller implements InputProcessor
{
	public boolean isPause = false;
	private float[] renderInfo;
	private HashSet<Controllable> controllables = new HashSet<Controllable>(); //The things to be controlled by input.
	
	/* Constructor */
	public Controller(float[] renderInfo)
	{
		this.renderInfo = renderInfo;
	}//END Controller
	
	/* Controllables */
	/**
	 * Adds an object that is controlled via user inputs into a list.
	 * 
	 * @param controllable object controlled via user input.
	 */
	public void add_controllable(Controllable controllable)
	{
		this.controllables.add(controllable);
	}//END add_controllable
	
	/* Input Processor */
	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if(!isPause)
			{
			float touchX = x / this.renderInfo[2];
			float touchY = this.renderInfo[1] - (y / this.renderInfo[2]);
			
			Iterator<Controllable> iter = this.controllables.iterator();
			while(iter.hasNext())
			{
				Controllable controllable = iter.next();
				controllable.input_touchDown(touchX, touchY, pointer, button);
			}//elihw
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if(!isPause)
		{
			float touchX = x / this.renderInfo[2];
			float touchY = this.renderInfo[1] - (y / this.renderInfo[2]);
			
			Iterator<Controllable> iter = this.controllables.iterator();
			while(iter.hasNext())
			{
				Controllable controllable = iter.next();
				controllable.input_touchDrag(touchX, touchY, pointer);
			}//elihw
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if(!isPause)
		{
			float touchX = x / this.renderInfo[2];
			float touchY = this.renderInfo[1] - (y / this.renderInfo[2]);
	
			Iterator<Controllable> iter = this.controllables.iterator();
			while(iter.hasNext())
			{
				Controllable controllable = iter.next();
				controllable.input_touchUp(touchX, touchY, pointer, button);
			}//elihw
		}
		
		return false;
	}

}//END Controller

//EOF
