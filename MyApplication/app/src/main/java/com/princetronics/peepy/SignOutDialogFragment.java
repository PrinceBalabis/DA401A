package com.princetronics.peepy;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Prince on 11/11/2014.
 */
public class SignOutDialogFragment extends DialogFragment {

    private static final String TAG = "SignOutDialogFragment";

    private SignOutCallback callerActivity;
    private Button btn_yes;

    public SignOutDialogFragment(Activity context) {
        callerActivity = (SignOutCallback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.logout_dfragment, container, false);
        getDialog().setTitle(R.string.tv_logoutquestion);

        btn_yes = (Button) rootView.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Confirmed password button");

                EditText etRepeatedPassword = (EditText) getView().findViewById(R.id.etRepeatedPassword);
                callerActivity.executeSignOut();

            }
        });


        return rootView;
    }
}
