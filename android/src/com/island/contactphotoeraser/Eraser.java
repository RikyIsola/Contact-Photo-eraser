package com.island.contactphotoeraser;
import android.content.*;
import android.net.*;
import android.os.*;
import java.io.*;
public class Eraser extends AsyncTask<Uri,Integer,Uri>
{
	Eraser(Context context)
	{
		this.context=context;
	}
	private final Context context;
	@Override
	protected Uri doInBackground(Uri[]p1)
	{
		ContentResolver resolver=context.getContentResolver();
		try
		{
			FileEraser.erase(new BufferedReader(new InputStreamReader(resolver.openInputStream(p1[0]))),new BufferedWriter(new OutputStreamWriter(resolver.openOutputStream(p1[1]))));
			return p1[1];
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	@Override
	protected void onPostExecute(Uri result)
	{
		super.onPostExecute(result);
		if(result!=null)
		{
			Intent target=new Intent(Intent.ACTION_VIEW);
			target.setDataAndType(result,MainActivity.MIME_TYPE);
			target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Intent intent=Intent.createChooser(target,MainActivity.OPEN_FILE);
			context.startActivity(intent);
		}
	}
}
