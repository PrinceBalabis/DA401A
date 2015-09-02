package princetronics.assignment3;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExpensesFragment extends Fragment {
    private static final String TAG = "ExpensesFragment";

    DBController dbController;

    private ListView expensesListView;
    private ExpenseAdapter adapter;

    public ExpensesFragment() {
    }

    public static Fragment newInstance() {
        return new ExpensesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbController = new DBController(getActivity());

        View root =  inflater.inflate(R.layout.fragment_expenses, container, false);

        setHasOptionsMenu(true);

        expensesListView = (ListView) root.findViewById(R.id.list_expense);
        expensesListView.setAdapter(adapter);

        return root;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.income_expenses_actions, menu);
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
        } else if(id == R.id.action_add) {
            FragmentManager fm = getFragmentManager();
            AddFragment addDFragment = new AddFragment();
            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putString("type", "expense");
            addDFragment.setArguments(args);
            addDFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Cursor c = dbController.getExpenses();
                    adapter = new ExpenseAdapter(getActivity(), c, true);
                    expensesListView.setAdapter(adapter);
                }
            });
            addDFragment.show(fm, "Dialog Fragment");// Show DialogFragment
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        dbController.open();

        Cursor c = dbController.getExpenses();
        adapter = new ExpenseAdapter(getActivity(), c, true);
        expensesListView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        dbController.close();
    }

    private class ExpenseAdapter extends CursorAdapter {

        private static final String TAG = "ExpenseAdapter";

        public ExpenseAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
            Log.d(TAG, "Constructor");
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            Log.d(TAG, "newView");
            View root = LayoutInflater.from(context).inflate(R.layout.row_expense, parent, false);

            ViewHolder holder = new ViewHolder();

            holder.date = (TextView) root.findViewById(R.id.tv_expense_date);
            holder.amount = (TextView) root.findViewById(R.id.tv_expense_amount);
            holder.title = (TextView) root.findViewById(R.id.tv_expense_title);

            root.setTag(holder);

            return root;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Log.d(TAG, "bindView");
            ViewHolder holder = (ViewHolder) view.getTag();

            holder.date.setText(cursor.getString(1));
            holder.amount.setText(cursor.getString(2) + " kr");
            holder.title.setText(cursor.getString(3));
        }

        private class ViewHolder {
            TextView date;
            TextView amount;
            TextView title;
        }
    }
}
