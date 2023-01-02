package com.haris.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddProduk extends AppCompatActivity {
    private EditText nama, deskripsi, harga, stock, url_gambar;
    private Button postDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produk);

        nama = findViewById(R.id.nama);
        deskripsi = findViewById(R.id.deskripsi);
        harga = findViewById(R.id.harga);
        stock = findViewById(R.id.stok);
        postDataBtn = findViewById(R.id.btnAddProduk);
        url_gambar = findViewById(R.id.url_image);
        postDataBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    postDataUsingVolley(nama.getText().toString(), deskripsi.getText().toString(),  Integer.parseInt(String.valueOf(harga.getText())),Integer.parseInt(String.valueOf(stock.getText())), url_gambar.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void postDataUsingVolley(String nama, String deskripsi, int harga, int stock, String url_gambar) throws JSONException {
        String URL = "http://" +  variable.API_VARIABLE + "/tugasakhir/tugasakhir/android/index.php?function=insert_produk";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("nama", nama);
        jsonBody.put("deskripsi", deskripsi);
        jsonBody.put("harga", harga);
        jsonBody.put("stok", stock);
        jsonBody.put("url_gambar", url_gambar);

        final String mRequestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddProduk.this,response,Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddProduk.this, home.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddProduk.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("nama", nama);
                params.put("deskripsi", deskripsi);
                params.put("harga", String.valueOf(harga));
                params.put("stok", String.valueOf(stock));
                params.put("url_gambar", url_gambar);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}