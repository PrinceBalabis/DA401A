package princetronics.assignment2;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
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

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignInActivity extends Activity implements SignInCallback {

    private static final String TAG = "LoginActivity";

    private EditText etEmail, etPassword;
    private Button btnSignIn, btnCreateAccount;
    private boolean etEmailFilled = false, etPasswordFilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Log.d(TAG, "onCreate");

        //You need to set the Android context using Firebase.setAndroidContext() before using Firebase.
        Firebase.setAndroidContext(this);

        initButtons();
        initEditTextListeners();
        initFontGraphics();
    }

    private void initButtons() {
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        View.OnClickListener oclBtnSignIn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Firebase authenticate user
                Firebase mFirebase = new Firebase("https://peepy.firebaseio.com/");
                mFirebase.authWithPassword(etEmail.getText().toString(), etPassword.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Log.d(TAG, "E-mail: " + etEmail.getText().toString() +
                                " Password: " + etPassword.getText().toString());
                        Toast.makeText(getApplicationContext(),
                                "Welcome back to Peepy, "+etEmail.getText().toString()+"!",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignInActivity.this, GroupActivity.class);
//                        intent.putExtra(TAG, etEmail.getText().toString());
//                        intent.putExtra(TAG, etPassword.getText().toString());
                        startActivity(intent);
                        finish(); // Destroy Activity
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        Toast.makeText(getApplicationContext(),
                                error.getMessage()+"("+etEmail.getText().toString()+")",
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG, error.getMessage());
                    }
                });
            }
        };
        btnSignIn.setOnClickListener(oclBtnSignIn);

        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        View.OnClickListener oclBtnCreateAccount = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Create Account button pressed");
                // If the username or password field is empty then run toast error message
                if (!etEmailFilled || !etPasswordFilled) {
                    Toast.makeText(getApplicationContext(),
                            "Please fill in both Peepy Name and Password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Username: " + etEmail.getText().toString() +
                            " Password: " + etPassword.getText().toString());

                    FragmentManager fm = getFragmentManager();
                    RepeatPasswordDialogFragment repeatPasswordDFragment = new RepeatPasswordDialogFragment(SignInActivity.this);
                    // Show DialogFragment
                    repeatPasswordDFragment.show(fm, "Dialog Fragment");


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

        etEmail.setTypeface(typeFace);
        etPassword.setTypeface(typeFace);

        btnSignIn.setTypeface(typeFace);
        btnCreateAccount.setTypeface(typeFace);

    }

    private void initEditTextListeners() {
        etEmail = (EditText) findViewById(R.id.etUserName);
        etEmail.addTextChangedListener(new TextWatcher() {

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
                    etEmailFilled = true;
                    if (etPasswordFilled == true) {
                        btnSignIn.setEnabled(true);
                        btnSignIn.getBackground().setAlpha(255);
                        Log.d(TAG, "Enabled sign in button");
                    }
                } else if (s.length() == 0) {
                    etEmailFilled = false;
                    btnSignIn.setEnabled(false);
                    btnSignIn.getBackground().setAlpha(64);
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
                    if (etEmailFilled == true) {
                        btnSignIn.setEnabled(true);
                        btnSignIn.getBackground().setAlpha(255);
                        Log.d(TAG, "Enabled sign in button");
                    }
                } else if (s.length() == 0) {
                    etPasswordFilled = false;
                    btnSignIn.setEnabled(false);
                    btnSignIn.getBackground().setAlpha(64);
                    Log.d(TAG, "Disabled sign in button");
                }
            }
        });
    }

    // Method is run by RepeatPasswordDialog to compare the password written and the repeated one
    public void returnConfirmedPassword(String confirmedPassword) {
        if (etPassword.getText().toString().equals(confirmedPassword)) {

            //Firebase create user
            final Firebase mFirebaseUsers = new Firebase("https://peepy.firebaseio.com/users");
            mFirebaseUsers.createUser(etEmail.getText().toString(), etPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {

                    // Set child of user to as firebase object, in order to make sure only
                    // the user-child is used in this login instance.
                    Firebase thisUserFirebase = mFirebaseUsers.child((String)result.get("uid"));
                    // set ID stored as a child in the users-child
                    thisUserFirebase.child("id").setValue((String)result.get("uid"));
                    // set email stored as a child in the users-child
                    thisUserFirebase.child("email").setValue(etEmail.getText().toString());

                    Toast.makeText(getApplicationContext(),
                            "Welcome to Peepy, " + etEmail.getText().toString(),
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, etEmail.getText().toString());
                    Intent intent = new Intent(SignInActivity.this, GroupActivity.class);
//                    intent.putExtra(TAG, etEmail.getText().toString());
//                    intent.putExtra(TAG, etPassword.getText().toString());

                    startActivity(intent);
                    finish(); // Destroy Activity
                }

                @Override
                public void onError(FirebaseError error) {
                    // Handle errors
                    Toast.makeText(getApplicationContext(),
                            error.getMessage()+"("+etEmail.getText().toString()+")",
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.getMessage());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(),
                    "Wrong password repeated!",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//
//        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
//
//        Log.d(TAG, "onResume");
//
//        setPassword.setText("");
//        btnSignIn.setEnabled(false);
//        btnSignIn.getBackground().setAlpha(64);
    }

    //
    @Override
    protected void onDestroy() {
        super.onDestroy();
//
//        Log.d(TAG, "onDestroy");
    }
}
