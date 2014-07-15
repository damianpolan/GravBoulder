package com.example.gravboulder.menu;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import android.view.Display;

public class SplashScene extends Scene
{

	public SplashScene(final GameActivity gameActivity)
	{
		Sprite splashSprite = new Sprite(0, 0, gameActivity.resource.menuTextureHolder.textureRegion_SplashScreen, gameActivity
				.getVertexBufferObjectManager());
		attachChild(splashSprite);

		Display display = gameActivity.getWindowManager().getDefaultDisplay();
		float ratio = (float) display.getHeight() / (float) display.getWidth();

		gameActivity.getEngine().getCamera().setXMax(splashSprite.getWidth());
		gameActivity.getEngine().getCamera().setXMin(0);
		gameActivity.getEngine().getCamera().setYMax(splashSprite.getWidth() * ratio);
		gameActivity.getEngine().getCamera().setYMin(0);
		gameActivity.getEngine().getCamera().setCenter(splashSprite.getWidth() / 2f, splashSprite.getHeight() / 2f);

		TimerHandler splashTimer = new TimerHandler(1, new ITimerCallback()
		{
			@Override
			public void onTimePassed(TimerHandler pTimerHandler)
			{
				//ensures you cannot go back to the splash scene

				//gameActivity.startActivity(new Intent(gameActivity, MainMenuActivity.class));

				//gameActivity.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);

				gameActivity.changeScene(new MainMenuScene(gameActivity));
			}
		});
		this.registerUpdateHandler(splashTimer);

	}
}
