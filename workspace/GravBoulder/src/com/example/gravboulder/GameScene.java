package com.example.gravboulder;

import java.util.HashMap;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.graphics.RectF;
import android.util.DisplayMetrics;

import com.example.gravboulder.menu.GameActivity;

public class GameScene extends Scene
{
	public static int BLOCK_SIZE = 256;
	public static int BORDER_WIDTH_X = 50;
	GameInfo info;
	GameActivity gameActivity;

	GameHud gameHud;

	HashMap<String, GridEntity> entities;

	public RectF mapRelRectangle;

	public GameScene(GameInfo info, GameActivity gameActivity)
	{
		super();
		//initialize background
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

		gameActivity.resource.gameTextureHolder.loadTextures(info.mapArtType);

		this.gameActivity = gameActivity;
		entities = new HashMap<String, GridEntity>();
		this.info = info;

		setupCamera();
		generateGridEntities();

		sortChildren();

		//register handlers
		registerUpdateHandler(new UpdateHandler());
		setOnSceneTouchListener(new TouchListener());
	}

	protected void setupCamera()
	{

		DisplayMetrics metrics = gameActivity.getResources().getDisplayMetrics();

		mapRelRectangle = new RectF();
		mapRelRectangle.right = info.baseGrid.length * BLOCK_SIZE + BORDER_WIDTH_X;
		mapRelRectangle.left = -BORDER_WIDTH_X;
		mapRelRectangle.bottom = info.baseGrid[0].length * BLOCK_SIZE * metrics.heightPixels / (float) metrics.widthPixels;
		mapRelRectangle.top = 0;

		//camera
		ZoomCamera camera = ((ZoomCamera) gameActivity.getEngine().getEngineOptions().getCamera());

		camera.setZoomFactor(1f);
		camera.setXMax(mapRelRectangle.right);
		camera.setXMin(mapRelRectangle.left);
		camera.setYMax(mapRelRectangle.bottom);
		camera.setYMin(mapRelRectangle.top);
		camera.setCenter(info.baseGrid.length * BLOCK_SIZE / 2f, info.baseGrid[0].length * BLOCK_SIZE / 2f);

		gameHud = new GameHud(this);
		//		gameHubScene = new GameHudScene(this);
		//		camera.setHUD(gameHubScene);
		//
		//		gameHubScene.setCamera(gameHubScene.camera);
	}

	protected void generateGridEntities()
	{
		entities.clear();
		for (int x = 0; x < info.baseGrid.length; x++)
		{
			for (int y = 0; y < info.baseGrid[0].length; y++)
			{
				GridEntity newE = createGridEntity(x, y);
				if (newE != null)
					entities.put(x + "_" + y, newE);
			}
		}
	}

	protected GridEntity createGridEntity(int gridX, int gridY)
	{
		GridEntity newE = null;

		if (info.baseGrid[gridX][gridY] == 'b')//wall block
		{
			ITextureRegion region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp0;
			float rotation = 0;
			
			boolean above = false;
			boolean below = false;
			boolean left = false;
			boolean right = false;

			if (gridY - 1 >= 0)
				above = info.baseGrid[gridX][gridY - 1] == 'b';

			if (gridY + 1 < info.baseGrid[0].length)
				below = info.baseGrid[gridX][gridY + 1] == 'b';

			if (gridX - 1 >= 0)
				left = info.baseGrid[gridX - 1][gridY] == 'b';

			if (gridX + 1 < info.baseGrid.length)
				right = info.baseGrid[gridX + 1][gridY] == 'b';

			//
			//   B b
			//
			if (!above && !left && !below && right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp1;
				rotation = 0;
			}

			//   b
			//   B
			//
			else if (above && !left && !below && !right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp1;
				rotation = -90;
			}

			//       
			//   B   
			//   b   
			else if (!above && !left && below && !right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp1;
				rotation = 90;
			}

			//       
			// b B   
			//      
			else if (!above && left && !below && !right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp1;
				rotation = 180;
			}

			//       
			// b B b 
			//      
			else if (!above && left && !below && right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp2R;
				rotation = 0;
			}

			//   b   
			//   B   
			//   b  
			else if (above && !left && below && !right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp2R;
				rotation = 90;
			}

			//   b   
			//   B b 
			//      
			else if (above && !left && !below && right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp2;
				rotation = -90;
			}

			//   b   
			// b B   
			//      
			else if (above && left && !below && !right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp2;
				rotation = 180;
			}

			//       
			// b B   
			//   b  
			else if (!above && left && below && !right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp2;
				rotation = 90;
			}

			//       
			//   B b 
			//   b 
			else if (!above && !left && below && right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp2;
				rotation = 0;
			}

			//   b   
			// b B b 
			//     
			else if (above && left && !below && right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp3;
				rotation = 180;
			}

			//   b   
			// b B   
			//   b 
			else if (above && left && below && !right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp3;
				rotation = 90;
			}

			//       
			// b B b 
			//   b 
			else if (!above && left && below && right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp3;
				rotation = 0;
			}

			//   b   
			//   B b 
			//   b 
			else if (above && !left && below && right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp3;
				rotation = -90;
			}

			//   b   
			// b B b 
			//   b 
			else if (above && left && below && right)
			{
				region = gameActivity.resource.gameTextureHolder.textureRegion_Block_exp4;
				rotation = 0;
			}

			newE = new GridEntity(info.baseGrid[gridX][gridY], gridX, gridY, region, gameActivity.getVertexBufferObjectManager());
			newE.setRotation(rotation);
			newE.updateLocation(BLOCK_SIZE);
			newE.setZIndex(0);
			super.attachChild(newE);
		}
		else if (info.baseGrid[gridX][gridY] == '1')//boulder block
		{
			newE = new GridEntity(info.baseGrid[gridX][gridY], gridX, gridY, gameActivity.resource.gameTextureHolder.textureRegion_Boulder,
					gameActivity.getVertexBufferObjectManager());
			newE.updateLocation(BLOCK_SIZE);
			newE.setZIndex(1);
			super.attachChild(newE);
		}
		else if (info.baseGrid[gridX][gridY] == '2')//boulder block
		{
			newE = new GridEntity(info.baseGrid[gridX][gridY], gridX, gridY, gameActivity.resource.gameTextureHolder.textureRegion_Boulder,
					gameActivity.getVertexBufferObjectManager());
			newE.updateLocation(BLOCK_SIZE);
			newE.setRotation(90);
			newE.setZIndex(1);
			super.attachChild(newE);
		}
		else if (info.baseGrid[gridX][gridY] == '3')//boulder block
		{
			newE = new GridEntity(info.baseGrid[gridX][gridY], gridX, gridY, gameActivity.resource.gameTextureHolder.textureRegion_Boulder,
					gameActivity.getVertexBufferObjectManager());
			newE.updateLocation(BLOCK_SIZE);
			newE.setRotation(270);
			newE.setZIndex(1);
			super.attachChild(newE);
		}
		else if (info.baseGrid[gridX][gridY] == '4')//boulder block
		{
			newE = new GridEntity(info.baseGrid[gridX][gridY], gridX, gridY, gameActivity.resource.gameTextureHolder.textureRegion_Boulder,
					gameActivity.getVertexBufferObjectManager());
			newE.updateLocation(BLOCK_SIZE);
			newE.setRotation(180);
			newE.setZIndex(1);
			super.attachChild(newE);
		}
		else if (info.baseGrid[gridX][gridY] == '5')//ender block
		{
			newE = new GridEntity(info.baseGrid[gridX][gridY], gridX, gridY, gameActivity.resource.gameTextureHolder.textureRegion_Ender,
					gameActivity.getVertexBufferObjectManager());
			newE.updateLocation(BLOCK_SIZE);
			super.attachChild(newE);
		}
		else if (info.baseGrid[gridX][gridY] == '6')//ender block
		{
			newE = new GridEntity(info.baseGrid[gridX][gridY], gridX, gridY, gameActivity.resource.gameTextureHolder.textureRegion_Ender,
					gameActivity.getVertexBufferObjectManager());
			newE.updateLocation(BLOCK_SIZE);
			newE.setRotation(90);
			super.attachChild(newE);
		}
		else if (info.baseGrid[gridX][gridY] == '7')//ender block
		{
			newE = new GridEntity(info.baseGrid[gridX][gridY], gridX, gridY, gameActivity.resource.gameTextureHolder.textureRegion_Ender,
					gameActivity.getVertexBufferObjectManager());
			newE.updateLocation(BLOCK_SIZE);
			newE.setRotation(270);
			super.attachChild(newE);
		}
		else if (info.baseGrid[gridX][gridY] == '8')//ender block
		{
			newE = new GridEntity(info.baseGrid[gridX][gridY], gridX, gridY, gameActivity.resource.gameTextureHolder.textureRegion_Ender,
					gameActivity.getVertexBufferObjectManager());
			newE.updateLocation(BLOCK_SIZE);
			newE.setRotation(180);
			super.attachChild(newE);
		}

		super.sortChildren();//move?
		
		return newE;
	}

	protected void move(int startX, int startY, int toX, int toY)
	{
		if (info.baseGrid[toX][toY] != 'b' && info.baseGrid[toX][toY] != '1' && info.baseGrid[toX][toY] != '2' && info.baseGrid[toX][toY] != '3' && info.baseGrid[toX][toY] != '4')
		{
			info.baseGrid[toX][toY] = info.baseGrid[startX][startY];
			info.baseGrid[startX][startY] = '0';

			GridEntity ge = entities.get(startX + "_" + startY);
			//ge.gridX = toX;
			//ge.gridY = toY;
			ge.animateToLocation(BLOCK_SIZE, toX, toY);
			entities.remove(startX + "_" + startY);
			entities.put(toX + "_" + toY, ge);
		}
	}

	/**
	 * NESW
	 * @param direction
	 */
	protected void shift(char direction)
	{
		if (direction == 'N')
		{
			for (int x = 0; x < info.baseGrid.length; x++)
			{
				for (int y = 0; y < info.baseGrid[0].length; y++)
				{
					GridEntity ge = entities.get(x + "_" + y);
					if (ge != null)
					{
						char t = ge.type;
						if (t == '1' || t == '2' || t == '3' || t == '4')
						{
							move(x, y, x, y - 1);
						}
					}
				}
			}
		}
		else if (direction == 'E')
		{
			for (int x = info.baseGrid.length - 1; x >= 0; x--)
			{
				for (int y = 0; y < info.baseGrid[0].length; y++)
				{
					GridEntity ge = entities.get(x + "_" + y);
					if (ge != null)
					{
						char t = ge.type;
						if (t == '1' || t == '2' || t == '3' || t == '4')
						{
							move(x, y, x + 1, y);
						}
					}
				}
			}
		}
		else if (direction == 'S')
		{
			for (int x = 0; x < info.baseGrid.length; x++)
			{
				for (int y = info.baseGrid[0].length - 1; y >= 0; y--)
				{
					GridEntity ge = entities.get(x + "_" + y);
					if (ge != null)
					{
						char t = ge.type;
						if (t == '1' || t == '2' || t == '3' || t == '4')
						{
							move(x, y, x, y + 1);
						}
					}
				}
			}
		}
		else if (direction == 'W')
		{
			for (int x = 0; x < info.baseGrid.length; x++)
			{
				for (int y = info.baseGrid[0].length - 1; y >= 0; y--)
				{
					GridEntity ge = entities.get(x + "_" + y);
					if (ge != null)
					{
						char t = ge.type;
						if (t == '1' || t == '2' || t == '3' || t == '4')
						{
							move(x, y, x - 1, y);
						}
					}
				}
			}
		}
	}

	private class TouchListener implements IOnSceneTouchListener
	{
		float startX = 0;
		float startY = 0;
		boolean swiperegistered = false;

		@Override
		public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
		{
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
			{
				startX = pSceneTouchEvent.getX();
				startY = pSceneTouchEvent.getY();
				swiperegistered = false;
			}
			else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP)
			{
				if (swiperegistered == false)
				{
					double angleDegrees = Math.toDegrees(Math.atan2(pSceneTouchEvent.getY() - startY, pSceneTouchEvent.getX() - startX));
					double length = Math.hypot(pSceneTouchEvent.getY() - startY, pSceneTouchEvent.getX() - startX);

					if (length > 50)
					{
						if (angleDegrees >= -135 && angleDegrees < -45)
						{
							shift('N');
						}
						else if (angleDegrees >= 45 && angleDegrees < 135)
						{
							shift('S');
						}
						else if (angleDegrees >= 135 || angleDegrees < -135)
						{
							shift('W');
						}
						else if (angleDegrees < 45 || angleDegrees >= -45)
						{
							shift('E');
						}
						swiperegistered = true;
					}
				}
			}

			return false;
		}
	}

	private class UpdateHandler implements IUpdateHandler
	{
		public UpdateHandler()
		{
		}

		@Override
		public void onUpdate(float pSecondsElapsed)
		{

		}

		@Override
		public void reset()
		{

		}
	}

}
