package com.island.contactphotoeraser;
import android.content.*;
import android.net.*;
import android.os.*;
import android.util.Log;

import java.io.*;
import java.lang.ref.WeakReference;

public class Eraser extends AsyncTask<Uri,Integer,Uri>
{
	Eraser(Context context)
	{
		//Create a weak reference to the contact to avoid memory leak
		reference=new WeakReference<>(context);
	}
	private final WeakReference<Context>reference;
	@Override
	protected Uri doInBackground(Uri[]uris)
	{
		try
		{
			//Open the input and output file and start the modification
			InputStream input=reference.get().getContentResolver().openInputStream(uris[0]);
			if(input==null)throw new IOException("Can't open input file");
			BufferedReader reader=new BufferedReader(new InputStreamReader(input));
			OutputStream output=reference.get().getContentResolver().openOutputStream(uris[1]);
			if(output==null)throw new IOException("Can't open output file");
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(output));
			FileEraser.erase(reader,writer);
			return uris[1];
		}
		catch(IOException exception)
		{
			throw new RuntimeException(exception);
		}
	}
	@Override
	protected void onPostExecute(Uri result)
	{
		super.onPostExecute(result);
		//Import the result vcf in a contact app
		if(result!=null)
		{
			Intent contactIntent=new Intent(Intent.ACTION_VIEW);
			contactIntent.setDataAndType(result,MainActivity.MIME_TYPE);
			contactIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Intent intent=Intent.createChooser(contactIntent,MainActivity.OPEN_FILE);
			try
			{
				reference.get().startActivity(intent);
			}
			catch(ActivityNotFoundException exception)
			{
				Log.e(MainActivity.LOG_TAG,"Missing activity to handle contact import",exception);
			}
		}
		else Log.w(MainActivity.LOG_TAG,"Result uri is null, skipping contact import");
	}
}
