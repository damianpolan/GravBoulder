package com.example.gravboulder.resource;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class MenuTextureHolder
{
	public BaseGameActivity gameActivity;

	public BitmapTextureAtlas textureAtlas1;
	public ITextureRegion textureRegion_SplashScreen;
	public ITextureRegion textureRegion_Play;
	public ITextureRegion textureRegion_LevelSelect;

	public boolean loaded = false;;


	public Font font;

	public MenuTextureHolder(BaseGameActivity gameActivity)
	{
		this.gameActivity = gameActivity;
	}

	public void loadTextures()
	{
		loaded = true;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Menu/");

		textureAtlas1 = new BitmapTextureAtlas(gameActivity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureRegion_SplashScreen = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas1, gameActivity, "SplashScreen.png", 0, 0); // 180, 123
		textureRegion_Play = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas1, gameActivity, "Play.png", 200, 0);
		textureRegion_LevelSelect = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas1, gameActivity, "Level Select.png", 0, 200);
		textureAtlas1.load();

		FontFactory.setAssetBasePath("fonts/");
		font = FontFactory.createFromAsset(gameActivity.getFontManager(), gameActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA,
				gameActivity.getAssets(), "High School USA Sans.ttf", 12, true, Color.BLACK.getARGBPackedInt());
		font.load();

	}

}