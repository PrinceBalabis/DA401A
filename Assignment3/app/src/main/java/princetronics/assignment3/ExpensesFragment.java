package princetronics.assignment3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExpensesFragment extends Fragment {
    private static final String TAG = "ExpensesFragment";

    DBController dbController;

    public ExpensesFragment() {
    }

    public static Fragment newInstance() {
        return new IncomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbController = new DBController(getActivity());

        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        dbController.open();

//        Cursor c = dbController.getPeople();
//        adapter = new PeopleAdapter(getActivity(), c, true);
//        people.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        dbController.close();
    }
}
