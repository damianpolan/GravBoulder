package com.example.gravboulder.resource;

import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.example.gravboulder.db.LevelDatabase;

public class Resource
{
	public SimpleBaseGameActivity activity;//the currently active activity. Set by activity constructor

	public MenuTextureHolder menuTextureHolder;
	public GameTextureHolder gameTextureHolder;

	public LevelDatabase levelDB;
	
	
	public Resource(SimpleBaseGameActivity activity)
	{
		this.activity = activity;
		menuTextureHolder = new MenuTextureHolder(activity);
		gameTextureHolder = new GameTextureHolder(activity);
		levelDB = new LevelDatabase(activity);
	}

	public void loadMenuRes()
	{
		menuTextureHolder.loadTextures();
	}

	public void unLoadMenuRes()
	{
		//
	}

	public void loadGameRes(int mapArtType)
	{
		gameTextureHolder.loadTextures(mapArtType);
	}

	public void unLoadGameRes()
	{
		//
	}
}
