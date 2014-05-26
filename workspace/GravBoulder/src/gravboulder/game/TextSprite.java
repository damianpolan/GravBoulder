package gravboulder.game;

import android.graphics.PointF;

public class TextSprite {
    
    public String text;
    public PointF position;
    public float[] color;
     
    public TextSprite()
    {
        text = "default";
        position.x = 0f;
        position.y = 0f;
        color = new float[] {1f, 1f, 1f, 1.0f};
    }
     
    public TextSprite(String txt, float x, float y)
    {
        text = txt;
        position.x = x;
        position.y = y;
        color = new float[] {1f, 1f, 1f, 1.0f};
    }
}