package com.example.postdatajson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout all_view;
    EditText user_name, password;
    Button login_btn;
    Snackbar ErrorSnackBar;
    String user_name_data, password_data = "";
    String url = "http://14.99.214.220/drbookingapp/bookingapp.asmx/UserLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        all_view = findViewById(R.id.all_view);
        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);

        // Error message
        ErrorSnackBar = Snackbar.make(all_view, "User name and pwd error!", BaseTransientBottomBar.LENGTH_LONG)
        .setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorSnackBar.dismiss();
            }
        });

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name_data = user_name.getText().toString();
                password_data = password.getText().toString();
                SendDataToServer(url, user_name_data, password_data);
            }
        });
    }

    private void SendDataToServer(String url, String userName, String password){
        StringRequest obj = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Api", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorSnackBar.show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError{
                HashMap <String, String> hMap = new HashMap<>();
                hMap.put("username", userName);
                hMap.put("pwd", password);
                return hMap;
            }
        };

        Volley.newRequestQueue(MainActivity.this).add(obj);
    }
}