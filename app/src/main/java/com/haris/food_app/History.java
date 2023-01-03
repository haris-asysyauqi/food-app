package com.haris.food_app;

import static com.haris.food_app.variable.API_VARIABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Stock> stockList;
    private RecyclerView.Adapter adapter;
    TextView allHistory,allproduk;
    private String url = "http://"+API_VARIABLE + "/android/index.php?function=get_produk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mList = findViewById(R.id.historyList);
        allHistory = findViewById(R.id.allHistory);
        allproduk = findViewById(R.id.allproduk);
        stockList = new ArrayList<>();
        adapter = new StockAdapter(getApplicationContext(),stockList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        getData();
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
                        Stock stock = new Stock();
                        allHistory.setText(String.valueOf(response.length()));
                        allproduk.setText(String.valueOf(response.length()));
                        stock.setId(jsonObject.getString("id_produk"));
                        stock.setTitle(jsonObject.getString("nama"));
                        stock.setDeskripsi(jsonObject.getString("deskripsi"));
                        stock.setHarga(jsonObject.getString("harga"));
                        stock.setStock(jsonObject.getString("stok"));
                        stock.setUrlGambar(jsonObject.getString("url_gambar"));
                        stockList.add(stock);
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

}