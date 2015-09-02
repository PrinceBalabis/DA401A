package princetronics.assignment3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class IncomeFragment extends Fragment {
    private static final String TAG = "IncomeFragment";

    DBController dbController;

    private ListView incomeListView;
    private IncomeAdapter adapter;

    public IncomeFragment() {
    }

    public static Fragment newInstance() {
        return new IncomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbController = new DBController(getActivity());

        View root =  inflater.inflate(R.layout.fragment_income, container, false);

        incomeListView = (ListView) root.findViewById(R.id.list_income);
        incomeListView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbController.open();

        Cursor c = dbController.getIncomes();
        adapter = new IncomeAdapter(getActivity(), c, true);
        incomeListView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        dbController.close();
    }

    private class IncomeAdapter extends CursorAdapter {

        public IncomeAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View root = LayoutInflater.from(context).inflate(R.layout.row_income, parent, false);

            ViewHolder holder = new ViewHolder();

            holder.date = (TextView) root.findViewById(R.id.tv_income_date);
            holder.amount = (TextView) root.findViewById(R.id.tv_income_amount);
            holder.title = (TextView) root.findViewById(R.id.tv_income_title);

            root.setTag(holder);

            return root;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
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
