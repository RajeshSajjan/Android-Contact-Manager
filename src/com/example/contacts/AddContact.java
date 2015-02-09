package com.example.contacts;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends Activity {
	Context ctx;
	public final static String EXTRA_MESSAGE = "com.example.Contacts.MESSAGE";
	public int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
	    position = intent.getIntExtra(MainActivity.EXTRA, -1);
	    System.out.println("Inside add contact "+position);
	    setContentView(R.layout.activity_add_contact);
	    if (position > -1)
	    {
	    	try {
				getdetails(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	//Loading all the required fields to perform editing of contact selected. - Nisha Halyal
	public void getdetails(View view) throws IOException  {
	     try {		
				// Get input stream and Buffered Reader for our data file.
				String FILENAM = "Output";
				FileInputStream ileIn= openFileInput(FILENAM);
				InputStreamReader InputRead= new InputStreamReader(ileIn);
				BufferedReader reader = new BufferedReader(InputRead);
				String line;
				int i=0;
				//Read each line
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					if (i == (position)) {
						String[] RowData = line.split(",");
						System.out.println(line);
						 EditText text1 = (EditText) findViewById(R.id.first_name);
					     EditText text2 = (EditText) findViewById(R.id.last_name);
					     EditText text3 = (EditText) findViewById(R.id.phone_number);
					     EditText text4 = (EditText) findViewById(R.id.email_id);
						text1.setText(RowData[0]);
						text2.setText(RowData[1]);
						text3.setText(RowData[2]);
						text4.setText(RowData[3]);
						break;
					}
					if (i > position) {
						break;
					} else {
						i++;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	//Function to handle adding of new contact which will be called on click of save button. - Rajesh Sajjan
	public void sendMessage(View view) throws IOException {
        // Do something in response to button
    	EditText firstname = (EditText) findViewById(R.id.first_name);
    	if(firstname.getText().toString().equals(""))
    	{
    		Toast.makeText(view.getContext(),"Firstname cannot be null", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	EditText lastname = (EditText) findViewById(R.id.last_name);
    	if(lastname.getText().toString().equals(""))
    		lastname.setText(" ");
    	EditText phonenumber = (EditText) findViewById(R.id.phone_number);
    	if(phonenumber.getText().toString().equals(""))
    		phonenumber.setText(" ");
    	EditText emailid = (EditText) findViewById(R.id.email_id);
    	if(emailid.getText().toString().equals(""))
    		emailid.setText(" ");
    	String message = firstname.getText().toString()+","+lastname.getText().toString()+","+phonenumber.getText().toString()+","+emailid.getText().toString()+"\n";
    	System.out.println(message);
    	if(position == -1)
    	{
    	String FILENAME = "Output";
    	FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
    	fos.write(message.getBytes());
    	fos.close();
    	}
    	else{
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
				fos.write(message.getBytes());
		    	fos.close();
			} catch (Exception ex) {
				System.out.println("Exception caught during save operation " + ex);
			}
    	}
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
}
