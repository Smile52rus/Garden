package com.arzaapps.android.garden;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arzaapps.android.garden.database.PeopleBaseHelper;
import com.arzaapps.android.garden.database.PeopleCursorWrapper;
import com.arzaapps.android.garden.database.PeopleDbSchema.PeopleTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PeopleLab {
    private static PeopleLab sPeopleLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private PeopleLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new PeopleBaseHelper(mContext)
                .getWritableDatabase();
    }

    public static PeopleLab get(Context context) {
        if (sPeopleLab == null) {
            sPeopleLab = new PeopleLab(context);
        }
        return sPeopleLab;
    }

    public void addPeople(People p) {
        ContentValues values = getContentValues(p);
        mDatabase.insert(PeopleTable.NAME, null, values);
    }

    public void deletePeople(People p) {
        ContentValues values = getContentValues(p);
        mDatabase.delete(PeopleTable.NAME, PeopleTable.Cols.UUID + " = ?",
                new String[]{p.getId().toString()});
    }

    public List<People> getPeoples() {
        List<People> peoples = new ArrayList<>();

        PeopleCursorWrapper cursor = queryPeoples(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                peoples.add(cursor.getPeople());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return peoples;
    }

    public People getPeople(UUID id) {
        PeopleCursorWrapper cursor = queryPeoples(PeopleTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
                );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPeople();
        } finally {
            cursor.close();
        }
    }

    public void updatePeople(People people) {
        String uuidString = people.getId().toString();
        ContentValues values = getContentValues(people);

        mDatabase.update(PeopleTable.NAME, values,
                PeopleTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private PeopleCursorWrapper queryPeoples(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PeopleTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new PeopleCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(People people) {
        ContentValues values = new ContentValues();
        values.put(PeopleTable.Cols.UUID, people.getId().toString());
        values.put(PeopleTable.Cols.NUMBER_AREA, people.getNumberArea().toString());
        values.put(PeopleTable.Cols.PEOPLE_NAME, people.getName().toString());
        values.put(PeopleTable.Cols.NUMBER_TELEPHONE, people.getTelephoneNumber().toString());
        values.put(PeopleTable.Cols.HOME_ADDRESS, people.getHomeAdress().toString());

        return values;
    }
}
