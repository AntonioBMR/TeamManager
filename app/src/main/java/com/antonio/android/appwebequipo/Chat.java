package com.antonio.android.appwebequipo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class Chat extends Activity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private AdaptadorChat mAdapter;
    private Client mClient;
    private String user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        arrayList = new ArrayList<String>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("user");
        }
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button) findViewById(R.id.send_button);
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new AdaptadorChat(this, arrayList);
        mList.setAdapter(mAdapter);
        // connect to the server
        new connectTask().execute("");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();

                if (mClient != null) {
                    mClient.sendMessage(message);
                }
                mAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    public class connectTask extends AsyncTask<String, String, Client> {
        @Override
        protected Client doInBackground(String... message) {
            mClient = new Client(new Client.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    publishProgress(message);
                }
            });
            mClient.run();
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            arrayList.add(values[0]);
            mAdapter.notifyDataSetChanged();
        }
    }

}
