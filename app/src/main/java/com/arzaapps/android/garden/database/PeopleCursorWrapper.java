package com.arzaapps.android.garden.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.arzaapps.android.garden.People;

import java.util.UUID;

import static com.arzaapps.android.garden.database.PeopleDbSchema.*;

public class PeopleCursorWrapper extends CursorWrapper {
    public PeopleCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public People getPeople() {
        String uuidString = getString(getColumnIndex(PeopleTable.Cols.UUID));
        String numberArea = getString(getColumnIndex(PeopleTable.Cols.NUMBER_AREA));
        String peopleName = getString(getColumnIndex(PeopleTable.Cols.PEOPLE_NAME));
        String numberTelephone = getString(getColumnIndex(PeopleTable.Cols.NUMBER_TELEPHONE));
        String homeAddress = getString(getColumnIndex(PeopleTable.Cols.HOME_ADDRESS));

        People people = new People(UUID.fromString(uuidString));
        people.setNumberArea(numberArea);
        people.setName(peopleName);
        people.setTelephoneNumber(numberTelephone);
        people.setHomeAdress(homeAddress);

        return people;
    }
}
