package princetronics.assignment2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import princetronics.assignment2.Data.Group;

/**
 * Created by Prince on 11/11/2014.
 */
public class GroupActivity extends Activity implements SignOutCallback {

    private static final String TAG = "GroupActivity";

    private GroupAdapter groupAdapter;


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

    public class GroupListFragment extends ListFragment {

        private ArrayList<Group> groups = new ArrayList<Group>();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Firebase mFirebaseGroups = new Firebase("https://peepy.firebaseio.com/groups");

            //Add new test groups to firebase
//            Firebase newGroupRef = mFirebaseGroups.push();
//            String id = newGroupRef.getKey();
//            Group testgroup = new Group(id, "Test Group");
//            newGroupRef.setValue(testgroup);

            mFirebaseGroups.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String s) {
                    // Read group object received from Firebase
                    Group group = snapshot.getValue(Group.class);

                    Log.d(TAG, "Group added!: "+ group.getName() + " ID: " + group.getId());

                    // Add children to the list
                    groups.add(new Group(group.getId(), group.getName()));
                    // Notify the adapter of the changes
                    groupAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot snapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot snapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot snapshot, String s) {
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });

            groupAdapter = new GroupAdapter(
                    inflater.getContext(), R.layout.item_group,
                    groups);
            setListAdapter(groupAdapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.d(TAG, "Position: " + position);

            // Start Chat Activity
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("name", groups.get(position).getName()); // Send group id to ChatActivity
            intent.putExtra("id", groups.get(position).getId()); // Send group id to ChatActivity
            startActivity(intent);
        }
    }

    public class GroupAdapter extends ArrayAdapter<Group> {

        // declaring our ArrayList of items
        private ArrayList<Group> objects;

        /* here we must override the constructor for ArrayAdapter
        * the only variable we care about now is ArrayList<Item> objects,
        * because it is the list of objects we want to display.
        */
        public GroupAdapter(Context context, int textViewResourceId, ArrayList<Group> objects) {
            super(context, textViewResourceId, objects);
            this.objects = objects;
        }

        /*
         * we are overriding the getView method here - this is what defines how each
         * list item will look.
         */
        public View getView(int position, View convertView, ViewGroup parent){

            // assign the view we are converting to a local variable
            View v = convertView;

            // first check to see if the view is null. if so, we have to inflate it.
            // to inflate it basically means to render, or show, the view.
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_group, null);
            }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
            Group i = objects.get(position);

            if (i != null) {

                // This is how you obtain a reference to the TextViews.
                // These TextViews are created in the XML files we defined.

                TextView group_name = (TextView) v.findViewById(R.id.group_name);
                TextView group_namedata = (TextView) v.findViewById(R.id.group_namedata);
                TextView group_id = (TextView) v.findViewById(R.id.group_id);
                TextView group_iddata = (TextView) v.findViewById(R.id.group_iddata);

                // check to see if each individual textview is null.
                // if not, assign some text!
                if (group_name != null){
                    group_name.setText("Name: ");
                }
                if (group_namedata != null){
                    group_namedata.setText(i.getName());
                }
                if (group_id != null){
                    group_id.setText("ID: ");
                }
                if (group_iddata != null){
                    group_iddata.setText(i.getId());
                }
            }

            // the view must be returned to our activity
            return v;

        }
    }
}

