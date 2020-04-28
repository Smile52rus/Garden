package com.arzaapps.android.garden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class PeopleActivity extends SingleFragmentActivity {
    private static final String EXTRA_PEOPLE_ID = "com.arzaapps.android.garden.people_id";

    public static Intent newIntent(Context packageContext, UUID peopleId){
        Intent intent = new Intent(packageContext, PeopleActivity.class);
        intent.putExtra(EXTRA_PEOPLE_ID, peopleId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID peopleId = (UUID) getIntent().getSerializableExtra(EXTRA_PEOPLE_ID);
        return PeopleFragment.newInstance(peopleId);
    }

}
