package com.example.epinect.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.epinect.Dashboard;
import com.example.epinect.R;
import com.example.epinect.Utils.Endpoints;
import com.example.epinect.Utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

  EditText inputField, passwordEt;
  Button submit_button;
  TextView signUpText;
  SharedPreferences sharedPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    inputField = findViewById(R.id.input_field); // This can be either email or number
    passwordEt = findViewById(R.id.password);
    submit_button = findViewById(R.id.submit_button);
    signUpText = findViewById(R.id.sign_up_text);

    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);

    if (rememberMe) {
      String savedInput = sharedPreferences.getString("input", "");
      String savedPassword = sharedPreferences.getString("password", "");
      inputField.setText(savedInput);
      passwordEt.setText(savedPassword);
    }
    signUpText.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
      }
    });

    submit_button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        inputField.setError(null);
        passwordEt.setError(null);
        String input = inputField.getText().toString();
        String password = passwordEt.getText().toString();
        if (isValid(input, password)) {
          login(input, password);

          sharedPreferences.edit()
                  .putBoolean("rememberMe", true)
                  .putString("input", input)
                  .putString("password", password)
                  .apply();
        }
      }
    });
  }

  private boolean isValid(String input, String password) {
    if (input.isEmpty()) {
      showMessage("Empty Email/Number");
      inputField.setError("Empty Email/Number");
      return false;
    } else if (password.isEmpty()) {
      showMessage("Empty Password");
      passwordEt.setError("Empty Password");
      return false;
    }
    return true;
  }

  private void login(final String input, final String password) {
    StringRequest stringRequest = new StringRequest(
            Method.POST, Endpoints.login_url, new Listener<String>() {
      @Override
      public void onResponse(String response) {
        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();

        if (response.equals("Invalid Credentials")) {
          // Credentials are incorrect, show an error message and do not start MainActivity
          Toast.makeText(LoginActivity.this, "Invalid Credentials. Please try again.", Toast.LENGTH_SHORT).show();
          LoginActivity.this.finish();
        } else {
          // Credentials are correct, start MainActivity
          startActivity(new Intent(LoginActivity.this, Dashboard.class));
          LoginActivity.this.finish();
        }
      }

    }, new ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Toast.makeText(LoginActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
        Log.d("VOLLEY", Objects.requireNonNull(error.getMessage()));
      }
    }) {
      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        // Check if the input is an email or phone number
        if (isEmailOrPhoneNumber(input)) {
          params.put("emailOrNumber", input);
        }
        params.put("password", password);
        return params;
      }
    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }

  private void showMessage(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }

  private boolean isEmailOrPhoneNumber(String input) {
    if (Patterns.EMAIL_ADDRESS.matcher(input).matches() || Patterns.PHONE.matcher(input).matches()) {
      return true; // It's a valid email or phone number
    } else {
      return false; // It's neither a valid email nor phone number
    }
  }
}
