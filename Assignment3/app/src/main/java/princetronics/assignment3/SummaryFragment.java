package princetronics.assignment3;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SummaryFragment extends Fragment {
    private static final String TAG = "SummaryFragment";

    DBController dbController;

    public SummaryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle("Summary");

        View root = inflater.inflate(R.layout.fragment_summary, container, false);
        //Print data to view
        TextView tv_totalIncome = (TextView) root.findViewById(R.id.tv_income_data);
        TextView tv_totalExpense = (TextView) root.findViewById(R.id.tv_expense_data);
        TextView tv_summary = (TextView) root.findViewById(R.id.tv_summary_data);

        dbController = new DBController(getActivity());

        //Read database
        String totalIncome = addUpAmount(dbController.getIncomeAmount());
        String totalExpense = addUpAmount(dbController.getExpenseAmount());
        String summary = String.valueOf(Integer.parseInt(totalIncome)-Integer.parseInt(totalExpense));

        //Log.d(TAG, String.valueOf(incomeTotal));
        tv_totalIncome.setText(totalIncome +" kr"); // Print total income
        tv_totalExpense.setText("-"+totalExpense +" kr"); // Print total expense
        tv_summary.setText(summary +" kr"); // Print total income

        return root;

    }

    private String addUpAmount(Cursor cursor){
        int totalAmount = 0;

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                String amount = cursor.getString(0);

                totalAmount += Integer.parseInt(amount);
                cursor.moveToNext();
            }
        }
        return String.valueOf(totalAmount);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        dbController.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        dbController.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.summary_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent main_activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_income) {
            Log.d("Menu", "Pressed Income button!");
            final FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new IncomeFragment(), "IncomeFragment");
            fragmentTransaction.commit();
            return true;
        } else if (id == R.id.menu_expenses) {
            Log.d("Menu", "Pressed Expenses button!");
            final FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new ExpensesFragment(), "ExpensesFragment");
            fragmentTransaction.commit();
            return true;
        } else if (id == R.id.menu_summary) {
            Log.d("Menu", "Pressed Summary button!");
            final FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SummaryFragment(), "SummaryFragment");
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
