package com.haris.food_app;

import static com.haris.food_app.variable.API_VARIABLE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailProduk extends AppCompatActivity {
    int jumlah = 1;
    Button addbag;
    TextView jumlahText, titleProduk, deskripsiProduk, totalProduk;
    String id, iNama, iDeskripsi, iHarga, iUrlGambar;
    boolean delete = false;
    int iStok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        addbag = findViewById(R.id.addbag);
        jumlahText = findViewById(R.id.jumlah);
        titleProduk = findViewById(R.id.titleProduk);
        deskripsiProduk = findViewById(R.id.deskripsiProduk);
        totalProduk = findViewById(R.id.totalProduk);

        ImageButton minus = findViewById(R.id.minus);
        Button removebag = findViewById(R.id.removebag);


        ImageButton plus = findViewById(R.id.plus);
        Intent intent = new Intent(getIntent());
        id = intent.getStringExtra("id");;
        jumlahText.setText(String.valueOf(jumlah));
        minus.setOnClickListener( new View.OnClickListener(){
            @Override()
            public  void onClick(View v) {
                if(jumlah == 1) {
                    jumlah = 1;
                    jumlahText.setText(String.valueOf(jumlah));

                } else {
                    jumlah = jumlah - 1;
                    jumlahText.setText(String.valueOf(jumlah));

                }
            }
        });
        plus.setOnClickListener( new View.OnClickListener(){
            @Override()
            public  void onClick(View v) {
                if(delete) {
                    if(jumlah < iStok ) {
                        jumlah = jumlah + 1;
                        jumlahText.setText(String.valueOf(jumlah));
                    };
                } else {
                    jumlah = jumlah + 1;
                    jumlahText.setText(String.valueOf(jumlah));
                }


            }
        });

        removebag.setOnClickListener( new View.OnClickListener(){
            @Override()
            public  void onClick(View v) {
               if (delete ) {
                   jumlah = 1;
                   delete = false;
                   jumlahText.setText(String.valueOf(jumlah));
                   addbag.setText("Add Stock");
                   removebag.setText("Remove");

               } else {
                   jumlah = iStok;
                   jumlahText.setText(String.valueOf(jumlah));
                   delete = true;
                   addbag.setText("Remove Stock");
                   removebag.setText("Add Stock");
               }

            }
        });
        getData();
        addbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int total = iStok + jumlah;
                    if(delete) {
                        try {
                            postDataUsingVolley(id, iNama, iDeskripsi, iHarga,jumlah, iUrlGambar );
                            delete = false;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        postDataUsingVolley(id, iNama, iDeskripsi, iHarga, total, iUrlGambar );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }            }
        });

    }

    private void getData(){
        String url = "http://"+API_VARIABLE + "/android/index.php?function=get_produk_id&id=" + id;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("JUDUL",String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        titleProduk.setText(jsonObject.getString("nama"));
                        deskripsiProduk.setText(jsonObject.getString("deskripsi"));
                        totalProduk.setText(jsonObject.getString("stok"));
                        iNama = jsonObject.getString("nama");
                        iDeskripsi = jsonObject.getString("deskripsi");
                        iHarga = jsonObject.getString("harga");
                        iStok = Integer.parseInt( jsonObject.getString("stok"));
                        iUrlGambar = jsonObject.getString("url_gambar");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void postDataUsingVolley(String id ,String nama, String deskripsi, String harga, int stock, String url_gambar) throws JSONException {
        String URL = "http://"+API_VARIABLE + "/android/index.php?function=update_produk&id=" + id;

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
                        Toast.makeText(DetailProduk.this,response,Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DetailProduk.this, home.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailProduk.this,error.toString(),Toast.LENGTH_LONG).show();
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

    private void deletData(String id ) throws JSONException {
        String URL = "http://"+API_VARIABLE + "/android/index.php?function=delete_produk&id=" + id;

        JSONObject jsonBody = new JSONObject();

        final String mRequestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DetailProduk.this,response,Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DetailProduk.this, home.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailProduk.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

