package princetronics.assignment2;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Prince on 11/11/2014.
 */
public class RepeatPasswordDialogFragment extends DialogFragment {

    private static final String TAG = "RepeatPasswordDialogFragment";

    private SignInCallback callerActivity;

    private Button btnConfirmPassword;

    public RepeatPasswordDialogFragment(Activity context) {
        callerActivity = (SignInCallback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.repeatpassword_dfragment, container, false);
        getDialog().setTitle(R.string.tv_repeatPassword);

        //Toggle keyboard
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        btnConfirmPassword = (Button) rootView.findViewById(R.id.btnConfirmPassword);
        btnConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Confirmed password button");

                EditText etRepeatedPassword = (EditText) getView().findViewById(R.id.etRepeatedPassword);
                callerActivity.returnConfirmedPassword(etRepeatedPassword.getText().toString());

            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");

        //Toggle keyboard
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }
}
