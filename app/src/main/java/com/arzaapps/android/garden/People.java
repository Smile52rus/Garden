package com.arzaapps.android.garden;

import java.util.UUID;

public class People {

    private UUID mId;
    private String mNubmerArea;
    private String mName;
    private String mTelephoneNumber;
    private String mHomeAdress;

    public People() {
        this(UUID.randomUUID());
    }

    public People(UUID id) {
        mId = id;
    }
    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getNumberArea() {
        return mNubmerArea;
    }

    public void setNumberArea(String nubmerArea) {
        if (nubmerArea.contains(".")) {
            mNubmerArea = nubmerArea.substring(0, nubmerArea.indexOf('.'));
        } else {
            mNubmerArea = nubmerArea;
       }
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTelephoneNumber() {
        return mTelephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        mTelephoneNumber = telephoneNumber;
    }

    public String getHomeAdress() {
        return mHomeAdress;
    }

    public void setHomeAdress(String homeAdress) {
        mHomeAdress = homeAdress;
    }

}
