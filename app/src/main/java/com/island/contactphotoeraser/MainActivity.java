package com.island.contactphotoeraser;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.util.Log;

public class MainActivity extends Activity
{
	public static final String MIME_TYPE="text/x-vcard";
	public static final String SHOW_STORAGE="android.content.extra.SHOW_ADVANCED";
	public static final String SHOW_FANCY="android.content.extra.FANCY";
	public static final String SHOW_SIZE="android.content.extra.SHOW_FILESIZE";
	public static final String OPEN_FILE="Open File";
	public static final String CREATE_FILE="Create File";
	public static final int INPUT_FILE_RESULT_CODE=1;
	public static final int OUTPUT_FILE_RESULT_CODE=2;
	public static final String LOG_TAG="Contact Photo Eraser";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	    Log.i(LOG_TAG,"Creating activity");
        //If debugging uses strict mode
		if(BuildConfig.DEBUG)
		{
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
		}
		//Start the storage access framework to select the vcf file
		Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.setType(MIME_TYPE);
		intent.putExtra(SHOW_STORAGE,true);
		intent.putExtra(SHOW_FANCY,true);
		intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
		intent.putExtra(SHOW_SIZE,true);
	    startActivityForResult(Intent.createChooser(intent,OPEN_FILE),INPUT_FILE_RESULT_CODE);
	    Log.i(LOG_TAG,"Started file selector");
    }
	Uri inputUri;
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		if(requestCode==INPUT_FILE_RESULT_CODE)
		{
			//Get the input uri
			if(resultCode==Activity.RESULT_OK)
			{
				inputUri=data.getData();
				if(inputUri==null)throw  new NullPointerException("Input uri is null");
				else
				{
					Intent intent=new Intent(Intent.ACTION_CREATE_DOCUMENT);
					intent.setType(MIME_TYPE);
					intent.putExtra(SHOW_STORAGE,true);
					intent.putExtra(SHOW_FANCY,true);
					intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
					intent.putExtra(SHOW_SIZE,true);
					startActivityForResult(Intent.createChooser(intent,CREATE_FILE),OUTPUT_FILE_RESULT_CODE);
				}
			}
		}
		else if(requestCode==OUTPUT_FILE_RESULT_CODE)
		{
			//Get the output uri and start the async task
			if(resultCode==Activity.RESULT_OK)
			{
				Uri outputUri=data.getData();
				new Eraser(this).execute(inputUri,outputUri);
				finish();
			}
		}
	}
}
