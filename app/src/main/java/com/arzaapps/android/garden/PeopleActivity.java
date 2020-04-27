package com.arzaapps.android.garden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class PeopleActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PeopleFragment();
    }

}
