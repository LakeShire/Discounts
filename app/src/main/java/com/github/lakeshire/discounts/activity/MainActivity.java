package com.github.lakeshire.discounts.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.github.lakeshire.discounts.R;
import com.github.lakeshire.discounts.fragment.MainFragment;
import com.github.lakeshire.lemon.activity.base.BaseActivity;
import com.github.lakeshire.lemon.fragment.base.BaseFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStatusBar2Translucent();

        addMainFragment();
    }

    private void addMainFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    private void setStatusBar2Translucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        BaseFragment f = (BaseFragment) fm.findFragmentById(R.id.container);
        if (!f.onBackPressed()) {
            super.onBackPressed();
        }
	}
}
