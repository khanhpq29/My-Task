package ht.pq.khanh.setting;

import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ht.pq.khanh.multitask.R;
import ht.pq.khanh.util.Common;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String theme = getSharedPreferences(Common.INSTANCE.getTHEME_PREFERENCES(), MODE_PRIVATE)
                .getString(Common.INSTANCE.getTHEME_SAVED(), Common.INSTANCE.getLIGHTTHEME());
        if(theme.equals(Common.INSTANCE.getLIGHTTHEME())){
            setTheme(R.style.AppTheme_LightTheme);
        }
        else{
            setTheme(R.style.AppTheme_DarkTheme);
        }
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Drawable backArrow = getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
        if(backArrow!=null){
            backArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        }

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(backArrow);
        }

        FragmentManager fm= getFragmentManager();
        fm.beginTransaction().replace(R.id.mycontent, new SettingFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this)!=null){
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
