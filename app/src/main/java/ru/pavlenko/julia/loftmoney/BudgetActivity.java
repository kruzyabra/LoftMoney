package ru.pavlenko.julia.loftmoney;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BudgetActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.view_pager);

        mViewPager.setAdapter(mViewPagerAdapter);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return BudgetFragment.newInstance();
                case 1:
                    return BudgetFragment.newInstance();
            }
            return null;
        }

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
    }
}
