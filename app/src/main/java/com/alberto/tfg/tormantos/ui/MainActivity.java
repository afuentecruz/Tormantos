package com.alberto.tfg.tormantos.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alberto.tfg.tormantos.R;
import com.alberto.tfg.tormantos.utils.Strings;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main Activity controller.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /** Keys */
    private static final String TAG = "TORMANTOS MAIN ACTIVITY";

    /** UI widgets */
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;
//
//    @BindView(R.id.mainButton)
//    FloatingActionButton mainFloatingButton;




    /**
     * Setup the configuration of view elements
     */
    private void setupElements() {

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupElements();
        this.checkForAppPermissions();
    }

    private void checkForAppPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_ACCESSIBILITY_SERVICE)
                != PackageManager.PERMISSION_GRANTED) {

            startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1);

        }else{
            System.out.println("esto dice que guay");
        }
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DBContentFragment dbContentFragment = new DBContentFragment();
        int id = item.getItemId();
        if (id == R.id.whatsapp) {
            // Show the WhatsApp stored content
            dbContentFragment.setContentDescription(Strings.PACKAGE_WHATSAPP);
        } else if (id == R.id.gmail) {
            // Show the GMail stored content
            dbContentFragment.setContentDescription(Strings.PACKAGE_GMAIL);

        } else if (id == R.id.sms) {
            // Show the Sms stored content
            dbContentFragment.setContentDescription(Strings.PACKAGE_SMS);

        } else if (id == R.id.firefox) {
            // Show the firefox stored content
            dbContentFragment.setContentDescription(Strings.PACKAGE_FIREFOX);
        } else if (id == R.id.notifications) {
            // Show the notifications related stored content
            dbContentFragment.setContentDescription(Strings.CLASS_NOTIFICATION);
        } else if (id == R.id.nav_send) {
        }

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content, dbContentFragment, DBContentFragment.TAG).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Widgets events
     */
//
//    @OnClick(R.id.mainButton)
//    public void doThing(View view) {
//        startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1);
//    }

    /**
     * Checks if the accessibility service 'MonitorService' is enabled in the
     * user accessibility settings
     * @param mContext application context
     * @return true is enabled, false in other case
     */
    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;

        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);

        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            Log.d(TAG, "Accessibility service is enabled");

            return true;
        } else {
            Log.d(TAG, "Accessibility service is DISABLED");
        }

        return false;
    }
}
