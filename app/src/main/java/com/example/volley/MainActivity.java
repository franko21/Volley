package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    String url="http://localhost:8082/api/clientes";

    List<String> datos=new ArrayList<>();

    ListView lstDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        queue = Volley.newRequestQueue(this);

        GetApioData();

        lstDatos=(ListView) findViewById(R.id.lstDatos);

    }

    private void GetApioData(){

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET,url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int x=0;
                if (response.length()>0){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject obj=response.getJSONObject(i);
                            Cliente c=new Cliente();

                            c.setNombre(obj.get("nombre").toString());
                            c.setApellido(obj.get("apellido").toString());
                            c.setEmail(obj.get("email").toString());

                            datos.add(c.getNombre());
                            datos.add(c.getApellido());
                            datos.add(c.getEmail());

                            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,datos);
                            lstDatos.setAdapter(adapter);


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int x=0;
            }
        });

        queue.add(request);
    }
}