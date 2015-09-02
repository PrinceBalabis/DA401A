package princetronics.assignment3;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Prince on 02-Sep-15.
 */
public class AddFragment extends DialogFragment {

    DBController dbController;

    private static final String TAG = "AddFragment";

    private Button btnAdd;

    private String type;

    public AddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        type = getArguments().getString("type");

        dbController = new DBController(getActivity());

        View rootView = inflater.inflate(R.layout.dfragment_add, container, false);
        getDialog().setTitle("Add new "+type);

        //Toggle keyboard
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        btnAdd = (Button) rootView.findViewById(R.id.btn_Add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etAmount = (EditText) getView().findViewById(R.id.et_Amount);
                EditText etTitle = (EditText) getView().findViewById(R.id.et_Title);

                //Check if valid input
                if(etAmount.getText().length() > 0 && etTitle.getText().length() > 0){
                    //Perform actions based on add type
                    if(type.equals("income")){
                        Log.d(TAG, "Added new income!");
                        dbController.addIncome(etTitle.getText().toString(), etAmount.getText().toString());
                    } else if (type.equals("expense")){
                        Log.d(TAG, "Added new income!");
                        dbController.addExpense(etTitle.getText().toString(), etAmount.getText().toString());
                    }
                    getDialog().dismiss(); // Close dialog
                } else { // Message user that they havent inputed anything!
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please input first",
                            Toast.LENGTH_LONG).show();
                }
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

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
