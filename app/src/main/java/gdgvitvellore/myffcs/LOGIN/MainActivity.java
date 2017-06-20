package gdgvitvellore.myffcs.LOGIN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import gdgvitvellore.myffcs.Activities.HomeActivity;
import gdgvitvellore.myffcs.R;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TabLayout tabs;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getApplicationContext().getSharedPreferences("Prefs",MODE_PRIVATE);
        boolean isLoggedIn=sharedPreferences.getBoolean("isLoggedIn",false);
            if (isLoggedIn == false) {
                setContentView(R.layout.activity_main);
                init();
                setInit();
            }
            else {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        }

    private boolean isOnline() {
        ConnectivityManager cm=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nw=cm.getActiveNetworkInfo();
        if(nw!=null && nw.isConnectedOrConnecting()){
            return true;
        }
        else{
            return false;
        }
    }


    private void setInit() {
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));
        tabs.setupWithViewPager(viewPager);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    private void init() {
        tabs=(TabLayout)findViewById(R.id.tabs);
        viewPager=(ViewPager)findViewById(R.id.login_viewPager);
    }


    private class CustomAdapter extends FragmentPagerAdapter {
        private String fragments[]={"Sign In","Sign Up"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new SigninFragment();
                case 1:
                    return new SignupFragment();
                default: return null;
            }


        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
