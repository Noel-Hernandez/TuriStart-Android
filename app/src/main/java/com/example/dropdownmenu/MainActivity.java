package com.example.dropdownmenu;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String[] itemsCamino = {"Asfalto", "Lastre"};
    String[] itemsTipo = {"Ciudad", "Montana", "Playa"};
    String[] itemsProvincia = {"SanJose", "Heredia", "Cartago", "Alajuela", "Puntarenas", "Guanacaste", "Limon"};
    String[] itemsValoracion = {"Aceptable", "Bueno", "MuyBueno"};


    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    AutoCompleteTextView autoCompleteTxt2;
    ArrayAdapter<String> adapterItems2;

    AutoCompleteTextView autoCompleteTxt3;
    ArrayAdapter<String> adapterItems3;

    AutoCompleteTextView autoCompleteTxt4;
    ArrayAdapter<String> adapterItems4;
    Button btnBuscar;
    private String camino;
    private String tipo;
    private String valoracion;
    private String provincia;
    private String nombre;
    private String imagen;
    private ImageView imageView1;
    private String itemCamino, itemType, itemValue, itemprovincia;
    private TextView txtNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        autoCompleteTxt2 = findViewById(R.id.auto_complete2_txt);
        autoCompleteTxt3 = findViewById(R.id.auto_complete3_txt);
        autoCompleteTxt4 = findViewById(R.id.auto_complete4_txt);

        imageView1 = findViewById(R.id.imageView1);
        txtNombre = findViewById(R.id.txtNombre);





        btnBuscar = findViewById(R.id.btnBuscar);


        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, itemsCamino);
        autoCompleteTxt.setAdapter(adapterItems);

        adapterItems2 = new ArrayAdapter<String>(this, R.layout.list_item, itemsTipo);
        autoCompleteTxt2.setAdapter(adapterItems2);

        adapterItems3 = new ArrayAdapter<String>(this, R.layout.list_item, itemsValoracion);
        autoCompleteTxt3.setAdapter(adapterItems3);

        adapterItems4 = new ArrayAdapter<String>(this, R.layout.list_item, itemsProvincia);
        autoCompleteTxt4.setAdapter(adapterItems4);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemCamino = parent.getItemAtPosition(position).toString();
            }
        });

        autoCompleteTxt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemType = parent.getItemAtPosition(position).toString();
            }
        });

        autoCompleteTxt3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemValue = parent.getItemAtPosition(position).toString();
            }
        });

        autoCompleteTxt4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemprovincia = parent.getItemAtPosition(position).toString();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultingApi();
                //   finish();
            }
        });
    }

    private void consultingApi() {

        //se arma el url
        String url = "https://proyectoturistarexpertosweb.000webhostapp.com/?camino=" + itemCamino + "&tipo="+itemType+"&valoracion="+itemValue+"&provincia="+itemprovincia+"&";


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    System.out.println("este es 1 " + jsonObject );

                    //obtiene el url de la imagen a mostrar
                    String urlImg = jsonObject.getString("imagen");
                    //obtiene el nombre a mostrar
                    txtNombre.setText(jsonObject.getString("nombre"));


                    //carga la imagen con solo el url
                    Picasso.get().load(urlImg).into(imageView1);

                    System.out.println("este es  " + txtNombre.getText() + " espacio " + urlImg );

                    //cambia la visibilidad
                    txtNombre.setVisibility(View.VISIBLE);
                    imageView1.setVisibility(View.VISIBLE);


                } catch (Exception e){
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonObjReq);
    }
}