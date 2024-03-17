package com.example.epinect.Activities;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.epinect.Dashboard;
import com.example.epinect.R;
import com.example.epinect.Utils.Endpoints;
import com.example.epinect.Utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
  private EditText nameEt, cityEt,emailEt, passwordEt, mobileEt;
  private Button userButton;

  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    nameEt = findViewById(R.id.name);
    emailEt = findViewById(R.id.email);
    cityEt = findViewById(R.id.city);
    passwordEt = findViewById(R.id.password);
    mobileEt = findViewById(R.id.number);
    userButton = findViewById(R.id.register_button);

    userButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String name,city, email, password, mobile;
        name = nameEt.getText().toString().trim();
        city = cityEt.getText().toString().trim();
        email = emailEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        mobile = mobileEt.getText().toString().trim();

        if (isValid(name, city,email, password, mobile)) {
          registerUser(name, city,email, password, mobile);
        }
      }
    });
  }

  private void registerUser(final String name, final String city,final String email,
                            final String password, final String mobile) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.register_user_url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                if (!response.equals("Success")) {
                  // Registration was successful
                  Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(RegisterActivity.this, Dashboard.class));
                  RegisterActivity.this.finish();
                } else {
                  // Registration was not successful
                  Toast.makeText(RegisterActivity.this, "Registration failed: " + response, Toast.LENGTH_SHORT).show();
                }
              }

            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
              }
            }) {
      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("city", city);
        params.put("email", email);
        params.put("password", password);
        params.put("number", mobile);
        return params;
      }
    };

    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }

  private boolean isValid(String name,  String city,String email, String password, String mobile) {
    if (name.isEmpty()) {
      showMessage("Name is empty");
      return false;
    } else if (email.isEmpty()) {
      showMessage("Email is required");
      return false;
    } else if (city.isEmpty()) {
      showMessage("City name is required");
      return false;
    } else if (mobile.length() != 10) {
      showMessage("Invalid mobile number, number should be 10 digits");
      return false;
    }

    return true;
  }

  private void showMessage(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }
}
