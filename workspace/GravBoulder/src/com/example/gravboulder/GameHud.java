package com.example.gravboulder;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import android.graphics.RectF;

public class GameHud
{
	Sprite border_Top;
	Sprite border_Bottom;

	ButtonSprite button_Restart;
	ButtonSprite button_Home;
	ButtonSprite button_LevelSelect;

	Text text_score;
	Text text_level;

	public Camera camera;

	public GameHud(GameScene gameScene)
	{
		Camera sceneCam = gameScene.gameActivity.getEngine().getCamera();
		RectF camRec = new RectF(sceneCam.getXMin(), sceneCam.getYMin(), sceneCam.getXMax(), sceneCam.getYMax()); //gameScene.mapRelRectangle;

		border_Top = new Sprite(camRec.left, camRec.top, gameScene.gameActivity.resource.gameTextureHolder.textureRegion_hud_white,
				gameScene.gameActivity.getVertexBufferObjectManager());
		border_Top.setWidth(camRec.width());
		border_Top.setHeight(0.10f * camRec.height());
		border_Top.setColor(Color.BLUE);
		gameScene.attachChild(border_Top);

		border_Bottom = new Sprite(camRec.left, camRec.bottom - 0.10f * camRec.height(), gameScene.gameActivity.resource.gameTextureHolder.textureRegion_hud_white,
				gameScene.gameActivity.getVertexBufferObjectManager());
		border_Bottom.setWidth(camRec.width());
		border_Bottom.setHeight(0.10f * camRec.height());
		border_Bottom.setColor(Color.BLUE);
		gameScene.attachChild(border_Bottom);

		button_LevelSelect = new ButtonSprite(camRec.left + 0.01f * camRec.height(), camRec.top + 0.01f * camRec.height(),
				gameScene.gameActivity.resource.gameTextureHolder.textureRegion_hud_levelSelect, gameScene.gameActivity.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{

				if (pSceneTouchEvent.isActionDown())
				{
					button_LevelSelect.setColor(new Color(127f / 255f, 127f / 255f, 127f / 255f));
				}
				else
				{
					button_LevelSelect.setColor(Color.WHITE);
				}

				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		button_LevelSelect.setWidth(0.08f * camRec.height());
		button_LevelSelect.setHeight(0.08f * camRec.height());
		gameScene.registerTouchArea(button_LevelSelect);
		gameScene.attachChild(button_LevelSelect);

		button_Restart = new ButtonSprite(camRec.right - 0.09f * camRec.height(), camRec.top + 0.01f * camRec.height(),
				gameScene.gameActivity.resource.gameTextureHolder.textureRegion_hud_restart, gameScene.gameActivity.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{

				if (pSceneTouchEvent.isActionDown())
				{
					button_Restart.setColor(new Color(127f / 255f, 127f / 255f, 127f / 255f));
				}
				else
				{
					button_Restart.setColor(Color.WHITE);
				}

				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		button_Restart.setWidth(0.08f * camRec.height());
		button_Restart.setHeight(0.08f * camRec.height());
		gameScene.registerTouchArea(button_Restart);
		gameScene.attachChild(button_Restart);

		button_Home = new ButtonSprite(camRec.left + 0.11f * camRec.height(), camRec.top + 0.01f * camRec.height(),
				gameScene.gameActivity.resource.gameTextureHolder.textureRegion_hud_home, gameScene.gameActivity.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{

				if (pSceneTouchEvent.isActionDown())
				{
					button_Home.setColor(new Color(127f / 255f, 127f / 255f, 127f / 255f));
				}
				else
				{
					button_Home.setColor(Color.WHITE);
				}

				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		button_Home.setWidth(0.08f * camRec.height());
		button_Home.setHeight(0.08f * camRec.height());
		gameScene.registerTouchArea(button_Home);
		gameScene.attachChild(button_Home);

	}

}