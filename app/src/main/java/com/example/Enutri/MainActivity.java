package com.example.Enutri;
/**
 * Created by yangfd on 2016/6/22.
 */
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TimePicker.OnTimeChangedListener;
//Date Formmat
//日  一  二  三  四  五  六
//1  2 3  4  5 6  7
public class MainActivity extends Activity 
{
	private View view1, view2;
    private String foodFeature;
	private ViewPager viewPager;
	private PagerTabStrip pagerTabStrip;//一个viewpager的指示器，效果就是一个横的粗的下划线  
    private List<View> viewList;//把需要滑动的页卡添加到这个list中  
    private List<String> titleList;//viewpager的标
	private ImageView arrawImageView;
	private SlidingDrawer slidingDrawer;
	private TextView textView ,text;
    private FoodDBHelper dbHelper;
	int hour = 100, minu = 100 , day;
	public static PendingIntent[] sender = new PendingIntent[7];
	Switch sunday , monday ,tuesday ,wednesday,thursday,friday,saturday;
	Switch spicy , sweet , protein , calorine ,fat , muslim , sour;
	Calendar calendar = Calendar.getInstance();
	int now , she;
	int[] bgcolor = new int[]{R.color.bg01 , R.color.bg02 ,R.color.bg03 ,R.color.bg04,R.color.bg05};
	int[] jz = new int[]{R.string.jz01,R.string.jz02,R.string.jz03 ,R.string.jz04 ,R.string.jz05,R.string.jz06,R.string.jz07,R.string.jz08,R.string.jz09};
	int[] img = new int[]{R.drawable.tu1,R.drawable.tu2,R.drawable.tu3,R.drawable.tu4,R.drawable.tu5};
    public void insertData(SQLiteDatabase sq, String food, String feature){
        sq.execSQL("insert into food values(null, ?, ?)", new String[]{food, feature});
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        dbHelper = new FoodDBHelper(this, "food.db3", 1);
        insertData(dbHelper.getReadableDatabase(), "dumpling", "0001100");
        insertData(dbHelper.getReadableDatabase(), "dumpling", "0001000");
        insertData(dbHelper.getReadableDatabase(), "dumpling", "0000000");
        insertData(dbHelper.getReadableDatabase(), "dumpling", "0000100");
        insertData(dbHelper.getReadableDatabase(), "dumpling with chili", "1001100");
        insertData(dbHelper.getReadableDatabase(), "dumpling with chili", "1000100");
        insertData(dbHelper.getReadableDatabase(), "dumpling with chili", "1000000");
        insertData(dbHelper.getReadableDatabase(), "dumpling with chili", "1000000");
        insertData(dbHelper.getReadableDatabase(), "dumpling with sugar", "0101100");
        insertData(dbHelper.getReadableDatabase(), "dumpling with sugar", "0100100");
        insertData(dbHelper.getReadableDatabase(), "dumpling with sugar", "0100000");
        insertData(dbHelper.getReadableDatabase(), "dumpling with sugar", "0100000");
        insertData(dbHelper.getReadableDatabase(), "egg", "0010010");
        insertData(dbHelper.getReadableDatabase(), "egg", "0011010");
        insertData(dbHelper.getReadableDatabase(), "egg", "0010110");
        insertData(dbHelper.getReadableDatabase(), "egg", "0011110");
        insertData(dbHelper.getReadableDatabase(), "egg", "0010000");
        insertData(dbHelper.getReadableDatabase(), "egg", "0011000");
        insertData(dbHelper.getReadableDatabase(), "egg", "0010100");
        insertData(dbHelper.getReadableDatabase(), "egg", "0011100");
        insertData(dbHelper.getReadableDatabase(), "spicy beef humburger", "1011010");
        insertData(dbHelper.getReadableDatabase(), "spicy beef humburger", "1011110");
        insertData(dbHelper.getReadableDatabase(), "spicy beef humburger", "1010010");
        insertData(dbHelper.getReadableDatabase(), "spicy pork humburger", "1011000");
        insertData(dbHelper.getReadableDatabase(), "spicy pork humburger", "1011100");
        insertData(dbHelper.getReadableDatabase(), "spicy pork humburger", "1010000");
        insertData(dbHelper.getReadableDatabase(), "sweet beef humburger", "0011010");
        insertData(dbHelper.getReadableDatabase(), "sweet beef humburger", "0011110");
        insertData(dbHelper.getReadableDatabase(), "sweet beef humburger", "0010010");
        insertData(dbHelper.getReadableDatabase(), "sweet pork humburger", "0011000");
        insertData(dbHelper.getReadableDatabase(), "sweet pork humburger", "0011100");
        insertData(dbHelper.getReadableDatabase(), "sweet pork humburger", "0010000");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.AppTheme);
		setContentView(R.layout.activity_main);
		RelativeLayout mainlay = (RelativeLayout)findViewById(R.id.mainlay);
		TextView showtext = (TextView)findViewById(R.id.showtext);
		ImageView showimg = (ImageView)findViewById(R.id.showimg);
		Random random = new Random();
		int bgnum = Math.abs(random.nextInt())%5;
		int jznum = Math.abs(random.nextInt())%9;
		mainlay.setBackgroundResource(bgcolor[bgnum]);
		showimg.setImageResource(img[bgnum]);
		showtext.setText(jz[jznum]);
            //System.out.println(Math.abs(random.nextInt())%10);
		init();
		text = (TextView)findViewById(R.id.textView1);
	}

	@SuppressWarnings("deprecation")
	private void init() 
	{
		foodFeature = "1111111";
        arrawImageView = (ImageView) findViewById(R.id.arrowImage);
		textView = (TextView) findViewById(R.id.textView);
		textView.setText("Open Me ");
		slidingDrawer = (SlidingDrawer) findViewById(R.id.myslidingDrawer);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab);
        pagerTabStrip.setTextSpacing(50);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.s_hui));
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.hui));
        pagerTabStrip.setDrawFullUnderline(false);
	     
        LayoutInflater lf =  getLayoutInflater();
        view1 = lf.inflate(R.layout.time, null);
        view2 = lf.inflate(R.layout.flavor, null);
	     
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
	     
        titleList = new ArrayList<String>();// 每个页面的Title数据
        titleList.add("Set breakfast time~");
        titleList.add("Set your preferrence~");
	     
	     PagerAdapter pagerAdapter = new PagerAdapter()
	     {
	    	public boolean isViewFromObject(View arg0, Object arg1) 
			{  
	                return arg0 == arg1;  
	        }
			public int getCount() 
			{
				 return viewList.size();
			}
			public void destroyItem(ViewGroup container, int position,  Object object) 
			{  
                container.removeView(viewList.get(position));  
            }
			public int getItemPosition(Object object) 
			{  
                return super.getItemPosition(object);  
            }
			public CharSequence getPageTitle(int position) 
			{  
                return titleList.get(position);  
            }
			public Object instantiateItem(ViewGroup container, int position) 
			{  
                container.addView(viewList.get(position));  
                return viewList.get(position);  
            }
	     };
	     viewPager.setAdapter(pagerAdapter);
			//////////////////////////////////
			slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() { // 打开抽屉

                @Override
                public void onDrawerOpened() {
                    TimePicker timePicker;

                    Button ok;
                    // TODO Auto-generated method stub
                    arrawImageView.setImageResource(R.drawable.down);
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.drawable.arrowrote);
                    arrawImageView.setAnimation(animation);
                    textView.setText("Close Me");
                    //sunday , monday ,tuesday ,wednesday,thursday,friday,saturday;
                    sunday = (Switch) findViewById(R.id.sunday);
                    monday = (Switch) findViewById(R.id.monday);
                    tuesday = (Switch) findViewById(R.id.tuesday);
                    wednesday = (Switch) findViewById(R.id.wednesday);
                    thursday = (Switch) findViewById(R.id.thursday);
                    friday = (Switch) findViewById(R.id.friday);
                    saturday = (Switch) findViewById(R.id.saturday);
                    spicy = (Switch) findViewById(R.id.spicy);
                    sweet = (Switch) findViewById(R.id.sweet);
                    protein = (Switch) findViewById(R.id.protein);
                    fat = (Switch) findViewById(R.id.fat);
                    calorine = (Switch) findViewById(R.id.calorine);
                    muslim = (Switch) findViewById(R.id.muslim);
                    sour = (Switch) findViewById(R.id.sour);
                    timePicker = (TimePicker) findViewById(R.id.timePicker);
                    timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
                        @Override
                        public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
                            // TODO Auto-generated method stub
                            hour = arg1;
                            minu = arg2;
                            //text.setText("时间" + arg1 + "time" + arg2);
                        }

                    });
                }
            });
		slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() { // 关闭抽屉

            @Override
            public void onDrawerClosed() {
                // TODO Auto-generated method stub
                arrawImageView.setImageResource(R.drawable.up);
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.drawable.arrowrote);
                arrawImageView.setAnimation(animation);
                textView.setText("Open Me");
                if (hour != 100 && minu != 100) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("(o=゜▽゜)")
                            .setMessage("Sure,Sir? ").setPositiveButton("Yes, please give me five!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Calendar cal = Calendar.getInstance();
                            now = cal.get(Calendar.DAY_OF_WEEK) * 10000 + cal.get(Calendar.HOUR_OF_DAY) * 100 + cal.get(Calendar.MINUTE);
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            //设置日历的小时和分钟
                            calendar.set(Calendar.HOUR_OF_DAY, hour);
                            calendar.set(Calendar.MINUTE, minu);
                            //将秒和毫秒设置为0
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            //建立Intent和PendingIntent来调用闹钟管理器
                            Intent intent = new Intent(MainActivity.this, BookReceiver.class);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            if (spicy.isChecked())
                                foodFeature = "0"+foodFeature.substring(1);
                            if (sweet.isChecked()) foodFeature = foodFeature.substring(0,1) + "0" + foodFeature.substring(2);
                            if (protein.isChecked())  foodFeature = foodFeature.substring(0,2) + "0" + foodFeature.substring(3);
                            if (fat.isChecked())  foodFeature = foodFeature.substring(0,3) + "0" + foodFeature.substring(4);
                            if (calorine.isChecked()) foodFeature = foodFeature.substring(0,4) + "0" + foodFeature.substring(5);
                            if (muslim.isChecked()) foodFeature = foodFeature.substring(0,5) + "0" + foodFeature.substring(6);
                            if (sour.isChecked()) foodFeature = foodFeature.substring(0,6) + "0";
                            intent.putExtra("foodFeature", foodFeature);
                            for (int i = 0; i < 7; i++) {
                                sender[i] = PendingIntent.getBroadcast(MainActivity.this, i, intent, 0);
                            }
                            showNotification(hour, minu);
                            //Setting the when to send short massage.
                            /////////////////////////////////////////////////////////////////////////////////////周日
                            if (sunday.isChecked()) {
                                day = 1;
                                she = day * 10000 + hour * 100 + minu;
                                calendar.set(Calendar.DAY_OF_WEEK, day);
                                if (she <= now) {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (7 * 24 * 60 * 60 * 1000), sender[0]);
                                } else {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender[0]);
                                }
                            } else {
                                alarmManager.cancel(sender[0]);
                            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////周一
                            if (monday.isChecked()) {
                                day = 2;
                                she = day * 10000 + hour * 100 + minu;
                                calendar.set(Calendar.DAY_OF_WEEK, day);
                                if (she <= now) {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (7 * 24 * 60 * 60 * 1000), sender[1]);
                                } else {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender[1]);
                                }
                            } else {
                                alarmManager.cancel(sender[1]);
                            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////周二
                            if (tuesday.isChecked()) {
                                day = 3;
                                she = day * 10000 + hour * 100 + minu;
                                calendar.set(Calendar.DAY_OF_WEEK, day);
                                if (she <= now) {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (7 * 24 * 60 * 60 * 1000), sender[2]);
                                } else {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender[2]);
                                }
                            } else {
                                alarmManager.cancel(sender[2]);
                            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////周三
                            if (wednesday.isChecked()) {
                                day = 4;
                                she = day * 10000 + hour * 100 + minu;
                                calendar.set(Calendar.DAY_OF_WEEK, day);
                                if (she <= now) {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (7 * 24 * 60 * 60 * 1000), sender[3]);
                                } else {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender[3]);
                                }
                            } else {
                                alarmManager.cancel(sender[2]);
                            }
                            //////////////////////////////////////////////////////////////////////////////周四
                            if (thursday.isChecked()) {
                                day = 5;
                                she = day * 10000 + hour * 100 + minu;
                                calendar.set(Calendar.DAY_OF_WEEK, day);
                                if (she <= now) {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (7 * 24 * 60 * 60 * 1000), sender[4]);
                                } else {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender[4]);
                                }
                            } else {
                                alarmManager.cancel(sender[4]);
                            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////周五
                            if (friday.isChecked()) {
                                day = 6;
                                she = day * 10000 + hour * 100 + minu;
                                calendar.set(Calendar.DAY_OF_WEEK, day);

                                if (she <= now) {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (7 * 24 * 60 * 60 * 1000), sender[5]);
                                } else {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender[5]);
                                }
                            } else {
                                alarmManager.cancel(sender[5]);
                            }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////周六
                            if (saturday.isChecked()) {
                                day = 7;
                                she = day * 10000 + hour * 100 + minu;
                                calendar.set(Calendar.DAY_OF_WEEK, day);
                                if (she <= now) {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (7 * 24 * 60 * 60 * 1000), sender[6]);
                                } else {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender[6]);
                                }
                            } else {
                                alarmManager.cancel(sender[6]);
                            }
                            Toast.makeText(MainActivity.this, "Set successfull!!ヾ(*`ω`)ノ", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            }
        });


	}
	
	private void showNotification(int hour , int min)
	{
		NotificationManager notificationManager = (NotificationManager)getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		Notification notification =new Notification(R.drawable.ic_launcher, "Enutri", System.currentTimeMillis());
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notification.flags = Notification.FLAG_NO_CLEAR;
		CharSequence contentTitle ="Breakfast Booking~"; // Notification Title
		CharSequence contentText ="Dear Sir, Your break fast will come at " + hour + ":" + min; // Notification Content
		Intent notificationIntent =new Intent(this, MainActivity.class);
		PendingIntent contentItent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(this, contentTitle, contentText, contentItent);
		notificationManager.notify(0, notification);
	}
}
