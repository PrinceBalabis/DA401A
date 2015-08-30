package princetronics.assignment2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Prince on 11/11/2014.
 */
public class GroupActivity extends Activity implements SignOutCallback {

    private static final String TAG = "GroupActivity";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            GroupListFragment list = new GroupListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }

    }

    @Override
    public void onBackPressed() {
        //Simulate Home Button
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {

            FragmentManager fm = getFragmentManager();
            SignOutDialogFragment signOutDFragment = new SignOutDialogFragment(GroupActivity.this);
            // Show DialogFragment
            signOutDFragment.show(fm, "Dialog Fragment");

            return true;
        } else if (id == R.id.about) {
            Intent intent = new Intent(GroupActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void executeSignOut() {
        Intent intent = new Intent(GroupActivity.this, SignInActivity.class);
        startActivity(intent);
        finish(); // Returns to sign in activity
    }

    public static class GroupListFragment extends ListFragment {
        // Defined Array values to show in ListView
        String[] values = new String[]{"Group 1",
                "Group 2",
                "Group 3",
                "Group 4",
                "Group 5",
                "Group 6",
                "Group 7",
        };

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();

            // Start Chat Activity
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra(TAG, position);
            startActivity(intent);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    inflater.getContext(), android.R.layout.simple_list_item_1,
                    values);
            setListAdapter(adapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
}

