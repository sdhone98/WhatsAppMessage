package com.example.whatsappmessage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText phoneNumber;
    private Button submitButton;
    private TextInputEditText userMessage;
    private CountryCodePicker countryCodePicker;
    private Context mContext;


    private String message, countryCode, userPhoneNumber;

    MyDialogFragment myDialog = new MyDialogFragment();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.whatsapp_color));

        mContext = MainActivity.this;

        if (!ConnectivityChecker.isConnected(this)) {
            myDialog.show(getSupportFragmentManager(), "dialog");
        }

        init();
    }

    private void init() {
        phoneNumber = (TextInputEditText) findViewById(R.id.phone_number_input);
        submitButton = (Button) findViewById(R.id.submit_button);
        userMessage = (TextInputEditText) findViewById(R.id.message_input);
        countryCodePicker = (CountryCodePicker) findViewById(R.id.country_code_picker);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phoneNumber.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter phone number first", Toast.LENGTH_SHORT).show();
                } else {
                    message = userMessage.getText().toString();
                    if (userMessage.getText().toString().isEmpty()) {
                        message = "Hello...";
                    }

                    countryCode = countryCodePicker.getSelectedCountryCode();
                    userPhoneNumber = phoneNumber.getText().toString();
//                String link = "https://api.whatsapp.com/send/?phone=91phoneNumber&text=hello&app_absent=0";
//
                    String url = "https://api.whatsapp.com/send/?phone=" + countryCode + userPhoneNumber + "&text=" + message + "&app_absent=0";


                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }


            }
        });
    }
}