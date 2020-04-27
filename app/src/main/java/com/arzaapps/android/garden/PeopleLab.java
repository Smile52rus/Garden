package com.arzaapps.android.garden;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PeopleLab {
    private static PeopleLab sPeopleLab;
    private List<People> mPeoples;

    private PeopleLab(Context context) {
        mPeoples = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            People people = new People();
            people.setHomeAdress("г. Арзамас, ул Мира, д.5");
            people.setTelephoneNumber("89150550633");
            people.setName("Иванов Иван Иванович");
            people.setNumberArea("13A");
        }
    }

    public static PeopleLab get(Context context) {
        if (sPeopleLab == null) {
            sPeopleLab = new PeopleLab(context);
        }
        return sPeopleLab;
    }

    public List<People> getPeoples() {
        return mPeoples;
    }

    public People getPeople(UUID id) {
        for (People people : mPeoples) {
            if (people.getId().equals(id)) {
                return people;
            }
        }
        return null;
    }
}
