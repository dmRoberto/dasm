package com.example.ws.example;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText dni;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dni = (EditText) findViewById(R.id.numDni);
	}

	public void consulta(View v) {
		new consultaBD().execute(dni.getText().toString());
	}

	private class consultaBD extends AsyncTask<String, Void, String> {
		private ProgressDialog pDialog;

		private Boolean pete;
		private final String URL = "http://demo.calamar.eui.upm.es/dasmapi/v1/miw08/fichas";
		

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pete = false;
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Conectando con la BBDD...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String dni = params[0];
			String url_final = URL;
			
			if (!dni.equals("")){
				url_final += "/"+ dni;
			}
			
			AndroidHttpClient cliente = AndroidHttpClient
					.newInstance("AndroidHttpClient");
			HttpGet httpGet = new HttpGet(url_final);
			String responseData = "";

			
			try {
				HttpResponse reponse = cliente.execute(httpGet);
				responseData = EntityUtils.toString(reponse.getEntity());
				cliente.close();
			} catch (IOException e) {
				pete = true;
				Log.e("Peto la operacion...", e.toString());
			}
			return responseData;
		}

		@Override
		protected void onPostExecute(String params) {
			pDialog.dismiss();
			String message = "";
			if (pete) {
				message = "Se produjo un error al realizar la consulta";
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
						.show();
				return;
			}
	
			try {
				JSONArray jsonData = new JSONArray(params);
				int regNum = jsonData.getJSONObject(0).getInt("NUMREG");
				switch (regNum) {
				case -1:
					message = "Se produjo un error al realizar la consulta";
					break;
				case 0:
					message = "No se encontraron datos";
					break;
				case 1:
					message = "Se encontró un registro en la BBDD";
					break;
				default:
					message = "Se encontraron " + regNum
							+ " regitros en la BBDD";
					break;
				}
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
						.show();
			} catch (JSONException e) {
				pete = true;
				Log.e("Peto la operacion...", e.toString());
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}
		}

	}
}
