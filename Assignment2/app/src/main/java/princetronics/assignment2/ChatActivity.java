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

import princetronics.assignment2.Data.ChatMessage;

/**
 * Created by Prince on 11/11/2014.
 */
public class ChatActivity extends Activity implements SignOutCallback {

    private static final String TAG = "ChatActivity";

    private ChatAdapter chatAdapter;

    private String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Store group-ID to private variable
        Intent intent = getIntent();
        groupID = intent.getStringExtra("id");
        Log.d(TAG, "Group-ID: " + groupID + "Name: " + intent.getStringExtra("name"));
        Toast.makeText(getApplicationContext(), "Group-Name: " + intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(android.R.id.content) == null) {
            ChatListFragment list = new ChatListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    @Override
    public void executeSignOut() {
        Intent intent = new Intent(ChatActivity.this, SignInActivity.class);
        startActivity(intent);
        finish(); // Returns to sign in activity
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
            SignOutDialogFragment signOutDFragment = new SignOutDialogFragment(ChatActivity.this);
            // Show DialogFragment
            signOutDFragment.show(fm, "Dialog Fragment");

            return true;
        } else if (id == R.id.about) {
            Intent intent = new Intent(ChatActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ChatListFragment extends ListFragment {

        private ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Firebase mFirebaseChatMessages = new Firebase("https://peepy.firebaseio.com/messages/" + groupID);

            //Add new test chat message to chosen group
            Firebase newChatMessageRef = mFirebaseChatMessages.push();
            String id = newChatMessageRef.getKey();
            ChatMessage testChatMessage = new ChatMessage(id,
                    "from",
                    "message",
                    "time");
            newChatMessageRef.setValue(testChatMessage);


            mFirebaseChatMessages.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String s) {
                    // Read ChatMessage object received from Firebase
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);

                    Log.d(TAG, "---------------------------");
                    Log.d(TAG, "New Chat Message: " + chatMessage.getMessage());
                    Log.d(TAG, "From: " + chatMessage.getFrom());
                    Log.d(TAG, "ID: " + chatMessage.getId() +
                            ", Time: " + chatMessage.getTimestamp());
                    Log.d(TAG, "---------------------------");

//                    // Add children to the list
                    chatAdapter.add(new ChatMessage(chatMessage.getId(),
                            chatMessage.getFrom(),
                            chatMessage.getMessage(),
                            chatMessage.getTimestamp()));

                    // Notify the adapter of the changes
                    chatAdapter.notifyDataSetChanged();
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

            chatAdapter = new ChatAdapter(
                    inflater.getContext(), R.layout.item_chatmessage,
                    chatMessages);
            setListAdapter(chatAdapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }


    public class ChatAdapter extends ArrayAdapter<ChatMessage> {

        // declaring our ArrayList of items
        private ArrayList<ChatMessage> objects;

        /* here we must override the constructor for ArrayAdapter
        * the only variable we care about now is ArrayList<Item> objects,
        * because it is the list of objects we want to display.
        */
        public ChatAdapter(Context context, int textViewResourceId, ArrayList<ChatMessage> objects) {
            super(context, textViewResourceId, objects);
            this.objects = objects;
        }

        /*
         * we are overriding the getView method here - this is what defines how each
         * list item will look.
         */
        public View getView(int position, View convertView, ViewGroup parent) {

            // assign the view we are converting to a local variable
            View v = convertView;

            // first check to see if the view is null. if so, we have to inflate it.
            // to inflate it basically means to render, or show, the view.
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_chatmessage, null);
            }

		/*
         * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
            ChatMessage i = objects.get(position);

            if (i != null) {

                // This is how you obtain a reference to the TextViews.
                // These TextViews are created in the XML files we defined.

                TextView chat_message = (TextView) v.findViewById(R.id.chat_message);
                TextView chat_messagedata = (TextView) v.findViewById(R.id.chat_messagedata);
                TextView chat_from = (TextView) v.findViewById(R.id.chat_from);
                TextView chat_fromdata = (TextView) v.findViewById(R.id.chat_fromdata);
                TextView chat_time = (TextView) v.findViewById(R.id.chat_time);
                TextView chat_timedata = (TextView) v.findViewById(R.id.chat_timedata);

                // check to see if each individual textview is null.
                // if not, assign some text!
                if (chat_message != null) {
                    chat_message.setText("Message: ");
                }
                if (chat_messagedata != null) {
                    chat_messagedata.setText(i.getMessage());
                }
                if (chat_from != null) {
                    chat_from.setText("From: ");
                }
                if (chat_fromdata != null) {
                    chat_fromdata.setText(i.getFrom());
                }
                if (chat_time != null) {
                    chat_time.setText("Time: ");
                }
                if (chat_timedata != null) {
                    chat_timedata.setText(i.getTimestamp());
                }
            }

            // the view must be returned to our activity
            return v;

        }
    }
}

