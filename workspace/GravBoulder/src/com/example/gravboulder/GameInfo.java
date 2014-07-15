package com.example.gravboulder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import android.content.Context;
import android.util.Log;

public class GameInfo
{
	public char[][] baseGrid;
	public int mapArtType;
	public String mapName;

	public GameInfo()
	{
	}

	public void load(Context context, String assetPath)
	{
		String loadS = "";
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(assetPath)));

			String line = reader.readLine();
			while (line != null)
			{
				loadS += line + "\n";

				line = reader.readLine();
			}
			reader.close();

		} catch (IOException e)
		{

			Log.e("GameInfo.load()", e.toString());

			return;
		}

		String[] splitS = loadS.split("\n");

		mapArtType = Integer.parseInt(splitS[0]);
		mapName = splitS[1];

		String[] mapS = Arrays.copyOfRange(splitS, 2, splitS.length);

		baseGrid = new char[mapS[0].length()][mapS.length];

		for (int y = 0; y < mapS.length; y++)
		{
			for (int x = 0; x < mapS[0].length(); x++)
			{
				baseGrid[x][y] = mapS[y].charAt(x);
			}
		}
	}

}
