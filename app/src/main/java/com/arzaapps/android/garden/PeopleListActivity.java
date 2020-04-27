package com.arzaapps.android.garden;

import androidx.fragment.app.Fragment;

public class PeopleListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new PeopleListFragment();
    }
}
