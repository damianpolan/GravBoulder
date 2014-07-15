package com.example.gravboulder;

import org.andengine.entity.sprite.Sprite;

public class RelCoo
{
	public static float dimsX = 100;
	public static float dimsY = 100;

	public static void displaceToCentre(Sprite s)
	{
		s.setX(s.getX() - s.getWidth() / 2f);
		s.setY(s.getY() - s.getHeight() / 2f);
	}
	
	public static float getRelX(float percent, boolean fromLeft)
	{
		if(fromLeft)
		{
			return dimsX * percent;
		}
		else
		{
			return dimsX - dimsX * percent;
		}
	}

	public static float getRelY(float percent, boolean fromTop)
	{
		if(fromTop)
		{
			return dimsY * percent;
		}
		else
		{
			return dimsY - dimsY * percent;
		}
	}
}
