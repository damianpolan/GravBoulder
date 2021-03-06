package com.example.gravboulder.menu;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;

import android.view.Display;

import com.example.gravboulder.GameInfo;
import com.example.gravboulder.GameScene;
import com.example.gravboulder.RelCoo;

public class MainMenuScene extends Scene
{

	public MainMenuScene(final GameActivity gameActivity)
	{
		Display display = gameActivity.getWindowManager().getDefaultDisplay();
		float ratio = (float) display.getHeight() / (float) display.getWidth();

		gameActivity.getEngine().getCamera().setXMax(300);
		gameActivity.getEngine().getCamera().setXMin(0);
		gameActivity.getEngine().getCamera().setYMax(300 * ratio);
		gameActivity.getEngine().getCamera().setYMin(0);

		RelCoo.dimsX = 300;
		RelCoo.dimsY = 300 * ratio;

		final ButtonSprite playSprite = new ButtonSprite(RelCoo.getRelX(0.5f, true), RelCoo.getRelY(0.4f, true),
				gameActivity.resource.menuTextureHolder.textureRegion_Play, gameActivity.getVertexBufferObjectManager());
		RelCoo.displaceToCentre(playSprite);
		playSprite.setOnClickListener(new ButtonSprite.OnClickListener()
		{
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{
				//playSprite.setColor(new Color(0.5f, 0.5f, 0.5f));
				GameInfo gameI = new GameInfo();
				gameI.load(gameActivity, "maps/" + gameActivity.highestUnlocked + ".map");
				gameActivity.changeScene(new GameScene(gameI, gameActivity));
			}
		});
		registerTouchArea(playSprite);
		attachChild(playSprite);
 
		final ButtonSprite levelSprite = new ButtonSprite(RelCoo.getRelX(0.5f, true), RelCoo.getRelY(0.5f, true),
				gameActivity.resource.menuTextureHolder.textureRegion_LevelSelect, gameActivity.getVertexBufferObjectManager());
		RelCoo.displaceToCentre(levelSprite);
		registerTouchArea(levelSprite);
		attachChild(levelSprite);

	}
}
