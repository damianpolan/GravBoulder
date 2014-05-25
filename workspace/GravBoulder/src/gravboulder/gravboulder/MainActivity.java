package gravboulder.gravboulder;


import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	// Our OpenGL Surfaceview
	private GLSurfaceView glSurfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
               
        // Super
		super.onCreate(savedInstanceState);
		
		// Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        glSurfaceView = new GLSurf(this);
        		
		setContentView(glSurfaceView);
				
	}

	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	}

}
