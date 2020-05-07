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
    }

    public static PeopleLab get(Context context) {
        if (sPeopleLab == null) {
            sPeopleLab = new PeopleLab(context);
        }
        return sPeopleLab;
    }

    public void addPeople(People p) {
        mPeoples.add(p);
    }

    public void deletePeople(People p) {
        for (int i = 0; i < mPeoples.size(); i++){
            if (p.getId().equals(mPeoples.get(i).getId())){
                mPeoples.remove(i);
            }
        }
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
