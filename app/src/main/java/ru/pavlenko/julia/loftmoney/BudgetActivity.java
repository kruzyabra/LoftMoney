package ru.pavlenko.julia.loftmoney;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BudgetActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mToolbar   = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));

        mTabLayout.getTabAt(0).setText(R.string.outcome);
        mTabLayout.getTabAt(1).setText(R.string.income);
        mTabLayout.getTabAt(2).setText(R.string.balance);

        mFloatingActionButton = findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                for (Fragment fragment : fragmentManager.getFragments()) {
                    if (fragment.getUserVisibleHint()) {
                        Intent intent = new Intent(BudgetActivity.this, AddItemActivity.class);
                        fragment.startActivityForResult(intent, BudgetFragment.REQUEST_CODE);
                        overridePendingTransition(R.anim.from_right, R.anim.to_left);
                    }
                }
            }
        });

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return BudgetFragment.newInstance(FragmentType.expense);
                case 1:
                    return BudgetFragment.newInstance(FragmentType.income);
                case 2:
                    return BalanceFragment.newInstance();
            }
            return null;
        }

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);

        setActionModeColors(ActionModeColors.actionModeOn);
        mFloatingActionButton.hide();
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);

        setActionModeColors(ActionModeColors.actionModeOff);
        mFloatingActionButton.show();
    }

    private void setActionModeColors(ActionModeColors actionModeColors) {
        mToolbar.setBackgroundColor(getResources().getColor(actionModeColors.getBackgroundColor()));
        mTabLayout.setBackgroundColor(getResources().getColor(actionModeColors.getBackgroundColor()));
        mTabLayout.setTabTextColors(
                getResources().getColor(actionModeColors.getTabsNormalColor()),
                getResources().getColor(actionModeColors.getTabsSelectedColor()));
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 2) {
            mFloatingActionButton.hide();
        } else
        {
            mFloatingActionButton.show();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
