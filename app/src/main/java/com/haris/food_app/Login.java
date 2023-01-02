package com.haris.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity implements View.OnClickListener {
    public static final String PREFS_NAME ="MyPrefsName";
    private Button button;
    private EditText username, password;
    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for(byte b : mdArray) {
            int v = b & 0xff;
            if(v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }

        return sb.toString();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.btnLogin);
        String strInput = "";
        strInput = username.getText().toString();
        getSupportActionBar().hide();

        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        button.setOnClickListener(new View.OnClickListener(){
            @Override()
            public  void onClick(View v) {
               storage storeManager = new storage(getApplicationContext());
                try {
                    if(username.getText().toString().equals("kaftapus") && password.getText().toString().equals("haris123")){
                        storeManager.setToken(hashPassword(password.getText().toString()+username.getText().toString()));
                        startActivity(new Intent(Login.this,home.class));
                        finish();
                        Toast.makeText(Login.this, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(Login.this,"username atau password salah",Toast.LENGTH_SHORT).show();


                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }}});
                   /*


                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

        });*/
    }

    @Override
    public void onClick(View view) {

    }
}