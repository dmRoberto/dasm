package com.example.actividad1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ActividadDos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_dos);
		Intent intent = getIntent();
		
		Bundle extras = intent.getExtras();
		String mensaje = extras.getString("mensaje");
		
		Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
	}
	
    public void lanzarActividadUno (View v){
    	Intent intent = new Intent();
    	intent.putExtra("Respuesta", "Todo va como dios manda");
    	setResult(RESULT_OK, intent);
    	finish();
    }
    
    
}
