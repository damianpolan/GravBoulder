package gravboulder.game;

import gravboulder.gravboulder.riGraphicTools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.opengl.Matrix;

public class GLRenderer implements Renderer
{
	private final float[] mtrxProjection = new float[16];
	private final float[] mtrxView = new float[16];
	private final float[] mtrxProjectionAndView = new float[16];

	ArrayList<Sprite> renderBatch;

	// Our screenresolution
	float mScreenWidth = 1280;
	float mScreenHeight = 768;

	// Misc
	Context mContext;
	long mLastTime;
	int mProgram;

	public GLRenderer(Context c)
	{
		mContext = c;

		renderBatch = new ArrayList<Sprite>();

		Sprite s1 = new Sprite();
		Sprite s2 = new Sprite();

		s1.texture = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/orange", null, mContext.getPackageName()));
		s2.texture = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources().getIdentifier("drawable/ic_launcher", null, mContext.getPackageName()));
		s1.position = new PointF(400, 400);
		s2.position = new PointF(100, 100);
		s1.layer = -1;
		renderBatch.add(s1);
		renderBatch.add(s2);

		mLastTime = System.currentTimeMillis() + 100;
	}

	public void onPause()
	{
		/* Do stuff to pause the renderer */
	}

	public void onResume()
	{
		/* Do stuff to resume the renderer */
		mLastTime = System.currentTimeMillis();
	}

	@Override
	public void onDrawFrame(GL10 unused)
	{
		// Get the current time
		long now = System.currentTimeMillis();

		// We should make sure we are valid and sane
		if (mLastTime > now)
			return;

		Collections.sort(renderBatch, new Comparator<Sprite>()
		{
			@Override
			public int compare(Sprite arg0, Sprite arg1)
			{
				return arg0.layer - arg1.layer;
			}
		});

		preRender();
		for (int i = 0; i < renderBatch.size(); i++)
		{
			renderSprite(mtrxProjectionAndView, renderBatch.get(i));
		}

		// Save the current time to see how long it took :). 
		mLastTime = now;
	}

	private void preRender()
	{
		// clear Screen and Depth Buffer, we have set the clear color as black.
		GLES20.glClearColor(0, 0, 100, 0);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
	}

	private void renderSprite(float[] m, Sprite sprite)
	{
		FloatBuffer vertexBuffer;
		ShortBuffer drawListBuffer;
		FloatBuffer uvBuffer;

		float[] vertices = sprite.getTransformedVertices();
		// The vertex buffer.
		ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		short[] indices = new short[] { 0, 1, 2, 0, 2, 3 };
		// initialize byte buffer for the draw list
		ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
		dlb.order(ByteOrder.nativeOrder());
		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(indices);
		drawListBuffer.position(0);

		// Create our UV coordinates.
		float[] uvs = new float[] { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f };
		// The texture buffer
		bb = ByteBuffer.allocateDirect(uvs.length * 4);
		bb.order(ByteOrder.nativeOrder());
		uvBuffer = bb.asFloatBuffer();
		uvBuffer.put(uvs);
		uvBuffer.position(0);

		//

		// Generate Textures, if more needed, alter these numbers.
		int[] texturenames = new int[1];
		GLES20.glGenTextures(1, texturenames, 0);
		// Bind texture to texturename
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);
		// Set filtering
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		// Load the bitmap into the bound texture.
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, sprite.texture, 0);

		//

		// get handle to vertex shader's vPosition member
		int mPositionHandle = GLES20.glGetAttribLocation(riGraphicTools.sp_Image, "vPosition");
		// Enable generic vertex attribute array
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		// Prepare the triangle coordinate data
		GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

		//allows transparency
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		// Get handle to texture coordinates location
		int mTexCoordLoc = GLES20.glGetAttribLocation(riGraphicTools.sp_Image, "a_texCoord");

		// Enable generic vertex attribute array
		GLES20.glEnableVertexAttribArray(mTexCoordLoc);

		// Prepare the texturecoordinates
		GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

		// Get handle to shape's transformation matrix
		int mtrxhandle = GLES20.glGetUniformLocation(riGraphicTools.sp_Image, "uMVPMatrix");

		// Apply the projection and view transformation
		GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

		// Get handle to textures locations
		int mSamplerLoc = GLES20.glGetUniformLocation(riGraphicTools.sp_Image, "s_texture");

		// Set the sampler texture unit to 0, where we have saved the texture.
		GLES20.glUniform1i(mSamplerLoc, 0);

		// Draw the triangle
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		// Disable vertex array
		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTexCoordLoc);

		//
		GLES20.glDeleteTextures(1, texturenames, 0);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// We need to know the current width and height.
		mScreenWidth = width;
		mScreenHeight = height;

		// Redo the Viewport, making it fullscreen.
		GLES20.glViewport(0, 0, (int) mScreenWidth, (int) mScreenHeight);

		// Clear our matrices
		for (int i = 0; i < 16; i++)
		{
			mtrxProjection[i] = 0.0f;
			mtrxView[i] = 0.0f;
			mtrxProjectionAndView[i] = 0.0f;
		}

		// Setup our screen width and height for normal sprite translation.
		Matrix.orthoM(mtrxProjection, 0, 0f, mScreenWidth, 0.0f, mScreenHeight, 0, 50);

		// Set the camera position (View matrix)
		Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

		// Calculate the projection and view transformation
		Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// Set the clear color to black
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);

		// Create the shaders, solid color
		int vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_SolidColor);
		int fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_SolidColor);

		riGraphicTools.sp_SolidColor = GLES20.glCreateProgram(); // create empty OpenGL ES Program
		GLES20.glAttachShader(riGraphicTools.sp_SolidColor, vertexShader); // add the vertex shader to program
		GLES20.glAttachShader(riGraphicTools.sp_SolidColor, fragmentShader); // add the fragment shader to program
		GLES20.glLinkProgram(riGraphicTools.sp_SolidColor); // creates OpenGL ES program executables

		// Create the shaders, images
		vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_Image);
		fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_Image);

		riGraphicTools.sp_Image = GLES20.glCreateProgram(); // create empty OpenGL ES Program
		GLES20.glAttachShader(riGraphicTools.sp_Image, vertexShader); // add the vertex shader to program
		GLES20.glAttachShader(riGraphicTools.sp_Image, fragmentShader); // add the fragment shader to program
		GLES20.glLinkProgram(riGraphicTools.sp_Image); // creates OpenGL ES program executables

		// Set our shader programm
		GLES20.glUseProgram(riGraphicTools.sp_Image);
	}

	public void addSprite(Sprite sprite)
	{
		renderBatch.add(sprite);
	}

	public void removeSprite(Sprite sprite)
	{
		renderBatch.remove(sprite);
	}

}
