package com.example.gravboulder;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class GridEntity extends Sprite
{
	public int gridX = 0;
	public int gridY = 0;
	public char type;

	public GridEntity(char type, int gridX, int gridY, ITextureRegion textureRegion, VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(0f, 0f, textureRegion, pVertexBufferObjectManager);
		this.gridX = gridX;
		this.gridY = gridY;
		this.type = type;
	}

	public void updateLocation(int blockSize)
	{
		super.setX(gridX * blockSize);
		super.setY(gridY * blockSize);
	}

	public void animateToLocation(int blockSize, int toX, int toY)
	{
		super.registerEntityModifier(new MoveModifier(0.05f, getX(), toX * blockSize, getY(), toY * blockSize));

		//super.setX(gridX * blockSize);
		//super.setY(gridY * blockSize);		
	}

}
