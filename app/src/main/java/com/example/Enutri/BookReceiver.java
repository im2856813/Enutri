package com.example.Enutri;
/**
 * Created by yangfd on 2016/6/22.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BookReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Log.i("tag", "received");
		// TODO Auto-generated method stub
		Intent i=new Intent(context , BookAct.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bud = intent.getExtras();
		i.putExtra("foodFeature", bud.getString("foodFeature"));
        /*for (String s : bud.keySet()) {
            Log.i("par test", bud.get(s).toString());
            i.putExtra(s, intent.getExtras().get(s).toString());
        }*/
		context.startActivity(i);
	}

}
