package com.example.Enutri;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yangfd on 2016/6/22.
 */
public class FoodDBHelper extends SQLiteOpenHelper{
    final  String  CREATE = "create table food(id integer primary key autoincrement, name, feature)";
    FoodDBHelper(Context c , String name, int ver){
        super(c,name,null, ver);
    }
    @Override
    public void onCreate(SQLiteDatabase sq){
        sq.execSQL(CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sq, int ver, int newVersion){
        System.out.println("OnUpgrade!! Version"+ newVersion );
    }
}
