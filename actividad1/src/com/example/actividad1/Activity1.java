package com.example.actividad1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Activity1 extends Activity {
	
	private final int ACTIVIDAD_DOS = 002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity1);
    }
    
    public void lanzarActividadDos (View v){
    	Intent intent = new Intent(this, ActividadDos.class);
    	intent.putExtra("mensaje", "desde la uno");
    	startActivityForResult(intent, ACTIVIDAD_DOS);
    }
    
    @Override
    public void onActivityResult (int actividad, int resultado, Intent datos){
    	if (actividad == ACTIVIDAD_DOS){
    		if(resultado == RESULT_OK){
    			String respuesta = datos.getStringExtra("Respuesta");
    			Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
    		}else{
    			Toast.makeText(this, "Resultado KK", Toast.LENGTH_LONG).show();
    		}
    	}
    }
}
