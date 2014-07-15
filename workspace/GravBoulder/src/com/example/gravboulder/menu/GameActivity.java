package com.example.gravboulder.menu;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.example.gravboulder.resource.Resource;

public class GameActivity extends SimpleBaseGameActivity
{
	public Resource resource;
	private Scene currentScene;

	public String highestUnlocked = "0-0"; //todo, update so that on game startup it is set to the highest unlocked level

	public GameActivity()
	{
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		//Resource.get().getCurrActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
	}

	public void changeScene(Scene newScene)
	{
		currentScene = newScene;
		getEngine().setScene(newScene);
	}

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		ZoomCamera camera = new ZoomCamera(0, 0, 200, 200);

		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), camera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources()
	{
		resource = new Resource(this);
		resource.loadMenuRes();
		highestUnlocked = resource.levelDB.getHighestUnlocked();
		
		currentScene = new SplashScene(this); //move?
	}

	@Override
	protected Scene onCreateScene()
	{
		return currentScene;
	}
}
