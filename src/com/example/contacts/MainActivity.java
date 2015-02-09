/*Group members    : Nisha Halyal, Rajesh Sajjan
 *Date 	  : 11/4/2014
 *Version : 1.0
 *Program : To maintain a contact list, which supports functionality to add, modify and save and delete a contact using files.
 */

package com.example.contacts;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

// Main activity responsible for showing all contacts in listview.
public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.Contacts.MESSAGE";
	public final static String EXTRA="com.example.Contacts.MESSAGE" ;
	reafile mAdapter;
	public int position=-1;
	@Override
	// First function that will be called which loads the listview.
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView mList = (ListView)findViewById(R.id.listView1);
		
		//Create Adapter
		System.out.println("here");
		mAdapter = new reafile(this, -1);
		
		//attach our Adapter to the ListView. This will populate all of the rows.
		mList.setAdapter(mAdapter);
		
		// Listener to get the position of the contact selected.
		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				position= pos;
				v.setSelected(true);
				System.out.println("position "+position);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
        int id = item.getItemId();
        switch (id) {
        case R.id.action_add:
        	position = -1;
            openADD(null);
            return true;
        case R.id.action_edit:
            openedit(null);
            return true;   	
        case R.id.action_delete:
        	System.out.println("postion  d"+position);
            opendelete(null);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}

	// To call the activity responsible for handling editing contact. - Nisha Halyal
	public void openedit(View view) {
    	if(position > -1)
    	{
		Intent intent = new Intent(this, AddContact.class);
		intent.putExtra(EXTRA, position);
	    startActivity(intent);
    	}
	}

	// To call the activity responsible for handling deleting contact.- Nisha Halyal
	public void opendelete(View view) {
		System.out.println(position);
		if(position > -1)
		{
		try {
			// Get input stream and Buffered Reader for our data file.
			String FILENAM = "Output";
			FileInputStream ileIn= openFileInput(FILENAM);
			InputStreamReader InputRead= new InputStreamReader(ileIn);
			BufferedReader reader = new BufferedReader(InputRead);
			String line;
			Vector<String> contactData = new Vector<String>();
			
			while ((line = reader.readLine()) != null) {
				contactData.addElement(line);
			}
			contactData.remove(position);
			String FILENAME = "Output";
	    	FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
	   
			for (int i = 0; i < contactData.size(); i++) {
				fos.write(contactData.elementAt(i).toString().getBytes());
				fos.write("\n".getBytes());
			}
	    	fos.close();
		} catch (Exception ex) {
			System.out.println("Exception caught during delete operation " + ex);
		}
		Intent intent = new Intent(this, MainActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
		}
	}

	// To call the activity responsible for handling adding contact.- Rajesh Sajjan
	public void openADD(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, AddContact.class);
    	startActivity(intent);
    }
}
