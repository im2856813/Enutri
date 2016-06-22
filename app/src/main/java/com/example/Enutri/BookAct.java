package com.example.Enutri;
/**
 * Created by yangfd on 2016/6/22.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class BookAct extends Activity
{
    MediaPlayer alarmMusic;
	RelativeLayout alarmlay;
    FoodDBHelper dbHelper;
    String SMS ;
	int[] bgcolor = new int[]{R.color.bg01 , R.color.bg02 ,R.color.bg03 ,R.color.bg04,R.color.bg05};

	public String FetchFood(String feature){
        Random r = new Random();
        Cursor c =dbHelper.getReadableDatabase().query("food", new String[]{"name"}, "feature = ?",new String[] {feature}, null, null, null);
        ArrayList<String> list = new ArrayList<String>();
        while (c.moveToNext()){
            list.add(c.getString(0));
        }
        if (list.isEmpty())return "noodles";
        else return list.get(Math.abs(r.nextInt() % list.size()));
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        //show options
        dbHelper = new FoodDBHelper(this, "food.db3", 1);
        Bundle bud = getIntent().getExtras();
        System.out.println(bud.getString("foodFeature"));
        String fd =  FetchFood(bud.getString("foodFeature"));
        Log.i("f test", FetchFood(bud.getString("foodFeature")));
        //send booing SSM
        SMS = "Sir, I need a " + fd +". My address is WHU Dormitary C4-510 ";
        send("15884796616", SMS);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AppTheme);
		setContentView(R.layout.alarmactl);
		alarmlay = (RelativeLayout)findViewById(R.id.alarmlay);
		Random random = new Random();
		int bgnum = Math.abs(random.nextInt())%5;
		alarmlay.setBackgroundResource(bgcolor[bgnum]);
		/*ok = (Button)findViewById(R.id.button1)
		ok.setOnClickListener(new View.OnClickListener()
		{
			@Override
		    public void onClick(View arg0)
			{
				alarmMusic.stop();
				BookAct.this.finish();
				Toast.makeText(BookAct.this, "验证码错误 ヾ(*`ω`)ノ", Toast.LENGTH_SHORT).show();
			}

		});
		/*
		// 创建一个对话框
		new AlertDialog.Builder(BookAct.this).setTitle("闹钟")
			.setMessage("闹钟响了啊喂(ノ ゜Д゜)ノ ")
			.setPositiveButton("确定", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// 停止音乐
					alarmMusic.stop();
					//AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);  
					//alarm.cancel(MainActivity.sender[1]);
					// 结束该Activity
					BookAct.this.finish();
				}
			}).show();*/
        // Send message start!!!
	}
    private void send(String number, String message){
        String SENT = "sms_sent";
        String DELIVERED = "sms_delivered";
        PendingIntent sentPI = PendingIntent.getActivity(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getActivity(this, 0, new Intent(DELIVERED), 0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.i("====>", "Activity.RESULT_OK");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Log.i("====>", "RESULT_ERROR_GENERIC_FAILURE");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Log.i("====>", "RESULT_ERROR_NO_SERVICE");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Log.i("====>", "RESULT_ERROR_NULL_PDU");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Log.i("====>", "RESULT_ERROR_RADIO_OFF");
                        break;
                }
            }
        }, new IntentFilter(SENT));
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent){
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Log.i("====>", "RESULT_OK");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("=====>", "RESULT_CANCELED");
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, message, sentPI, deliveredPI);
    }
}

