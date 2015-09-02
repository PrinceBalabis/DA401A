package princetronics.assignment3;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Prince on 02-Sep-15.
 */
public class AddFragment extends DialogFragment {

    DBController dbController;

    private static final String TAG = "AddFragment";

    private Button btnAdd;

    public AddFragment(Activity context) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbController = new DBController(getActivity());

        View rootView = inflater.inflate(R.layout.dfragment_add, container, false);
        getDialog().setTitle(R.string.addNew);

        //Toggle keyboard
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        btnAdd = (Button) rootView.findViewById(R.id.btnConfirmPassword);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Confirmed password button");

                EditText etAmount = (EditText) getView().findViewById(R.id.et_Amount);
                EditText etTitle = (EditText) getView().findViewById(R.id.et_Title);

                //dbController.addIncome()

                //callerActivity.returnConfirmedPassword(etAmount.getText().toString());

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbController.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        dbController.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbController.close();
        Log.d(TAG, "onDestroy");

        //Toggle keyboard
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }
}
