package com.princetronics.peepy;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";

    private EditText etUserName, etPassword;
    private Button btnSignIn, btnCreateAccount;
    private boolean etUserNameFilled = false, etPasswordFilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "onCreate");

        initButtons();
        initEditTextListeners();
        initFontGraphics();
    }

    private void initButtons() {
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        View.OnClickListener oclBtnSignIn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Later add new Intent
                Log.d(TAG, "Username: " + etUserName.getText().toString() +
                        " Password: " + etPassword.getText().toString());
            }
        };
        btnSignIn.setOnClickListener(oclBtnSignIn);

        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        View.OnClickListener oclBtnCreateAccount = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Create Account button pressed");
                // If the username or password field is empty then run toast error message
                if(!etUserNameFilled || !etPasswordFilled){
                    Toast.makeText(getApplicationContext(),
                            "Please fill in both Peepy Name and Password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Later add new Intent
                    Log.d(TAG, "Username: " + etUserName.getText().toString() +
                            " Password: " + etPassword.getText().toString());
                }
            }
        };
        btnCreateAccount.setOnClickListener(oclBtnCreateAccount);
    }

    // Setup custom font
    private void initFontGraphics() {
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/font200.ttf");

        TextView myTextView = (TextView) findViewById(R.id.tv_appName);
        myTextView.setTypeface(typeFace);

        etUserName.setTypeface(typeFace);
        etPassword.setTypeface(typeFace);

        btnSignIn.setTypeface(typeFace);
        btnCreateAccount.setTypeface(typeFace);

    }

    private void initEditTextListeners() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etUserName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "Username characters: " + s.length());

                //If username and password has at least one character then enable sign in button
                if (s.length() == 1) {
                    etUserNameFilled = true;
                    if (etPasswordFilled == true) {
                        btnSignIn.setEnabled(true);
                        Log.d(TAG, "Enabled sign in button");
                    }
                } else if (s.length() == 0) {
                    etUserNameFilled = false;
                    btnSignIn.setEnabled(false);
                    Log.d(TAG, "Disabled sign in button");
                }
            }
        });

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "Password characters: " + s.length());

                //If username and password has at least one character then enable sign in button
                if (s.length() == 1) {
                    etPasswordFilled = true;
                    if (etUserNameFilled == true) {
                        btnSignIn.setEnabled(true);
                        Log.d(TAG, "Enabled sign in button");
                    }
                } else if (s.length() == 0) {
                    etPasswordFilled = false;
                    btnSignIn.setEnabled(false);
                    Log.d(TAG, "Disabled sign in button");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }
}
