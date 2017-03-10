package com.pxl.opdrachten.securityapp.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ccele on 1/24/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AliceFragment tabAlice = new AliceFragment();
                return tabAlice;
            case 1:
                BobFragment tabBob = new BobFragment();
                return tabBob;
            case 2: ShowFilesFragment tabFiles = new ShowFilesFragment();
                return  tabFiles;
            case 3: SecretImageFragment tabImage = new SecretImageFragment();
                return  tabImage;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
