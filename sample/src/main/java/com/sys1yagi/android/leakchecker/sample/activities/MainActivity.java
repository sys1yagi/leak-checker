package com.sys1yagi.android.leakchecker.sample.activities;

import com.sys1yagi.android.leakchecker.LeakChecker;
import com.sys1yagi.android.leakchecker.sample.fragments.LeakedFragment;
import com.sys1yagi.android.leakchecker.sample.fragments.MemoryUsageFragment;
import com.sys1yagi.android.leakchecker.sample.fragments.MenuListFragment;
import com.sys1yagi.android.leakchecker.sample.fragments.NotLeakedFragment;
import com.sys1yagi.android.leakchecker.sample.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity
        implements MenuListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MenuListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeakChecker.dump();
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(int pos) {
        switch (pos) {
            case 0:
                replace(LeakedFragment.newInstance());
                break;
            case 1:
                replace(NotLeakedFragment.newInstance());
                break;
            case 2:
                replace(MemoryUsageFragment.newInstance());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
