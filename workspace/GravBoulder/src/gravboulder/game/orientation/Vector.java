package gravboulder.game.orientation;

public class Vector
{
	public float[] vArray;
	
	public float[] getVArray()
	{
		return vArray;
	}
	
	public float get(int index)
	{
		return vArray[index];
	}
	
	public void set(int index, float value)
	{
		vArray[index] = value;
	}
	
	public void inc(float ... amount)
	{
		compare(amount);
		for(int i = 0; i < amount.length; i++)
		{
			vArray[i] += amount[i];
		}
	}
	
	public Vector(float... params)
	{
		vArray = params.clone();
	}

	public Vector add(Vector value)
	{
		compare(value);
		float[] newA = new float[vArray.length];

		for (int i = 0; i < newA.length; i++)
		{
			newA[i] = vArray[i] + value.vArray[i];
		}

		return new Vector(newA);
	}

	public Vector subtract(Vector value)
	{
		compare(value);
		float[] newA = new float[vArray.length];

		for (int i = 0; i < newA.length; i++)
		{
			newA[i] = vArray[i] - value.vArray[i];
		}

		return new Vector(newA);
	}

	public Vector divide(Vector value)
	{
		compare(value);
		float[] newA = new float[vArray.length];

		for (int i = 0; i < newA.length; i++)
		{
			newA[i] = vArray[i] / value.vArray[i];
		}

		return new Vector(newA);
	}

	public Vector multiply(Vector value)
	{
		compare(value);
		float[] newA = new float[vArray.length];

		for (int i = 0; i < newA.length; i++)
		{
			newA[i] = vArray[i] * value.vArray[i];
		}

		return new Vector(newA);
	}

	private void compare(float[] value)
	{
		if (vArray.length != value.length)
			throw new RuntimeException("Compared vector's must be same length.");
	}

	private void compare(Vector value)
	{
		compare(value.vArray);
	}
	
	public static Vector Zero1()
	{
		return new Vector(0);
	}

	public static Vector Zero2()
	{
		return new Vector(0, 0);
	}

	public static Vector Zero3()
	{
		return new Vector(0, 0, 0);
	}

	public static Vector Zero4()
	{
		return new Vector(0, 0, 0, 0);
	}

}