package com.island.contactphotoeraser;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
public class MainActivity extends Activity 
{
	public static final String MIME_TYPE="text/x-vcard";
	public static final String SHOW_STORAGE="android.content.extra.SHOW_ADVANCED";
	public static final String SHOW_FANCY="android.content.extra.FANCY";
	public static final String SHOW_SIZE="android.content.extra.SHOW_FILESIZE";
	public static final String OPEN_FILE="Open File";
	public static final String CREATE_FILE="Create File";
	public static final int FILE_RESULT_CODE=1;
	public static final int NEW_FILE_CODE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		if(BuildConfig.DEBUG)
		{
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
		}
		Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.setType(MIME_TYPE);
		intent.putExtra(SHOW_STORAGE,true);
		intent.putExtra(SHOW_FANCY,true);
		intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
		intent.putExtra(SHOW_SIZE,true);
		try
		{
			startActivityForResult(Intent.createChooser(intent,OPEN_FILE),FILE_RESULT_CODE);
		}
		catch(ActivityNotFoundException e)
		{
			e.printStackTrace();
			finish();
		}
    }
	Uri uri;
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		if(requestCode==FILE_RESULT_CODE)
		{
			if(resultCode==Activity.RESULT_OK)
			{
				uri=data.getData();
				if(uri==null)finish();
				else
				{
					Intent intent=new Intent(Intent.ACTION_CREATE_DOCUMENT);
					intent.setType(MIME_TYPE);
					intent.putExtra(SHOW_STORAGE,true);
					intent.putExtra(SHOW_FANCY,true);
					intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
					intent.putExtra(SHOW_SIZE,true);
					try
					{
						startActivityForResult(Intent.createChooser(intent,CREATE_FILE),NEW_FILE_CODE);
					}
					catch(ActivityNotFoundException e)
					{
						e.printStackTrace();
						finish();
					}
				}
			}
		}
		else if(requestCode==NEW_FILE_CODE)
		{
			if(resultCode==Activity.RESULT_OK)
			{
				Uri newuri=data.getData();
				if(newuri!=null)new Eraser(this).execute(uri,newuri);
				finish();
			}
		}
	}
}
