package com.haris.food_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<produk> produkList;
    private RecyclerView.Adapter adapter;
    private String url = "http://"+ variable.API_VARIABLE + "/tugasakhir/tugasakhir/android/index.php?function=get_produk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        CardView cardFruit = findViewById(R.layout.card_item);
        Button detailSec = findViewById(R.id.detailSec);
        Button more = findViewById(R.id.more);
        Button addScreen = findViewById(R.id.addScreen);
        mList = findViewById(R.id.produkList);
        getSupportActionBar().hide();


        produkList = new ArrayList<>();
        adapter = new ProdukAdapter(getApplicationContext(),produkList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        storage storeManager = new storage(getApplicationContext());

        more.setOnClickListener( new View.OnClickListener(){
            @Override()
            public  void onClick(View v) {
                storeManager.setToken("");
                startActivity(new Intent(home.this, Login.class));
                finish();
            }
        });
        detailSec.setOnClickListener( new View.OnClickListener(){
            @Override()
            public  void onClick(View v) {
                startActivity(new Intent(home.this, History.class));
            }
        });
        addScreen.setOnClickListener( new View.OnClickListener(){
            @Override()
            public  void onClick(View v) {
                startActivity(new Intent(home.this, AddProduk.class));
            }
        });
        getData();

//        cardFruit.setOnClickListener( new View.OnClickListener(){
//            @Override()
//            public  void onClick(View v) {
//                startActivity(new Intent(home.this, DetailProduk.class));
//            }
//        });
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                for (int i=response.length()-1;i>=0;i--) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        produk produk = new produk();
                        produk.setId(jsonObject.getString("id_produk"));
                        produk.setTitle(jsonObject.getString("nama"));
                        produk.setDeskripsi(jsonObject.getString("deskripsi"));
                        produk.setHarga(jsonObject.getString("harga"));
                        produk.setStock(jsonObject.getString("stok"));
                        produk.setUrlGambar(jsonObject.getString("url_gambar"));
                        produkList.add(produk);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {

    }
}