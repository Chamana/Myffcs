package gdgvitvellore.myffcs.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import gdgvitvellore.myffcs.FRAGMENT.AddCoursesFragmet;
import gdgvitvellore.myffcs.FRAGMENT.RegisteredCourses;
import gdgvitvellore.myffcs.FRAGMENT.TimetableFragment;
import gdgvitvellore.myffcs.R;

/**
 * Created by chaman on 9/6/17.
 */

public class HomeActivity extends AppCompatActivity {
    ViewPager vp;
    TabLayout tabs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        isStoragePermissionGranted();
        init();
        setInit();
        setupTabIcons();
        vp.setCurrentItem(0);
        tabs.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#3677ed"),PorterDuff.Mode.SRC_IN);
    }

    private boolean isStoragePermissionGranted() {
        if(Build.VERSION.SDK_INT>=23){
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                return true;
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
                return false;
            }
        }
        else{
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 1 ) {
            manager.popBackStack();
        } else {
            // if there is only one entry in the backstack, show the home screen
            moveTaskToBack(true);
        }
    }

    private void setupTabIcons(){
        tabs.getTabAt(0).setIcon(R.drawable.home);
        tabs.getTabAt(1).setIcon(R.drawable.add);
        tabs.getTabAt(2).setIcon(R.drawable.timetable);
    }

    private void setInit() {
        vp.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));
        tabs.setupWithViewPager(vp);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(Color.parseColor("#3677ed"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#FFFFFF"),PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }
        });
    }

    private void init() {
        vp=(ViewPager)findViewById(R.id.homeViewPager);
        tabs=(TabLayout)findViewById(R.id.homeTabs);
    }

    private class CustomAdapter extends FragmentPagerAdapter {

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:return new RegisteredCourses();
                case 1:return new AddCoursesFragmet();
                case 2:return new TimetableFragment();
                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
