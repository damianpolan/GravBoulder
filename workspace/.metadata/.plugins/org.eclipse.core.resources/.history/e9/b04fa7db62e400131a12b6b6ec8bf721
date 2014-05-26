package gravboulder.game;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import gravboulder.game.orientation.Vector;

public class Sprite
{
	public Vector position;
	public float rotation;
	public float scale;

	public Bitmap texture;

	public int layer = 0;

	public Sprite()
	{
		position = Vector.Zero3();
		rotation = 0;
		scale = 1f;
	}

	public float[] getTransformedVertices()
	{
		RectF base = new RectF(-texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2, -texture.getHeight() / 2);
		// Start with scaling
		float x1 = base.left * scale;
		float x2 = base.right * scale;
		float y1 = base.bottom * scale;
		float y2 = base.top * scale;

		// We now detach from our Rect because when rotating, 
		// we need the seperate points, so we do so in opengl order
		PointF one = new PointF(x1, y2);
		PointF two = new PointF(x1, y1);
		PointF three = new PointF(x2, y1);
		PointF four = new PointF(x2, y2);

		// We create the sin and cos function once, 
		// so we do not have calculate them each time.
		float s = (float) Math.sin(rotation);
		float c = (float) Math.cos(rotation);

		// Then we rotate each point
		one.x = x1 * c - y2 * s;
		one.y = x1 * s + y2 * c;
		two.x = x1 * c - y1 * s;
		two.y = x1 * s + y1 * c;
		three.x = x2 * c - y1 * s;
		three.y = x2 * s + y1 * c;
		four.x = x2 * c - y2 * s;
		four.y = x2 * s + y2 * c;

		// Finally we translate the sprite to its correct position.
		one.x += position.get(0);
		one.y += position.get(1);
		two.x += position.get(0);
		two.y += position.get(1);
		three.x += position.get(0);
		three.y += position.get(1);
		four.x += position.get(0);
		four.y += position.get(1);

		// We now return our float array of vertices.
		return new float[] { one.x, one.y, position.get(2), two.x, two.y, position.get(2), three.x, three.y, position.get(2), four.x, four.y,
				position.get(2), };
	}
}
