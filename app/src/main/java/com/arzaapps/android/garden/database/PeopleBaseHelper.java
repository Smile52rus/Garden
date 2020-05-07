package com.arzaapps.android.garden.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arzaapps.android.garden.database.PeopleDbSchema.PeopleTable;

public class PeopleBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "peopleBase.db";


    public PeopleBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PeopleTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                PeopleTable.Cols.UUID + ", " +
                PeopleTable.Cols.NUMBER_AREA + ", " +
                PeopleTable.Cols.PEOPLE_NAME + ", " +
                PeopleTable.Cols.NUMBER_TELEPHONE + ", " +
                PeopleTable.Cols.HOME_ADDRESS +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
