package ht.pq.khanh.multitask;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ht.pq.khanh.extension.ExtensionKt;
import ht.pq.khanh.multitask.forecast.ForecastFragment;
import ht.pq.khanh.multitask.weather.WeatherFragment;
import ht.pq.khanh.task.ReminderFragment;
import ht.pq.khanh.task.alarm.AlarmFragment;

public class MainActivity extends AppCompatActivity {
    private boolean isBackPress = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    navigateToFragment(new ReminderFragment());
                    return true;
                case R.id.navigation_dashboard:
                    navigateToFragment(new WeatherFragment());
                    return true;
                case R.id.navigation_notifications:
                    navigateToFragment(new ForecastFragment());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void navigateToFragment(Fragment fragment){
         FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()){
            transaction.show(fragment);
        }else{
            transaction.replace(R.id.container, fragment);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot() || isBackPress) {
            super.onBackPressed();
            return;
        }
        isBackPress = true;
        ExtensionKt.showToast(this, "press back to exit");
        new Handler().postDelayed(exitHandler, 1500);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Runnable exitHandler = () -> isBackPress = false;
}
