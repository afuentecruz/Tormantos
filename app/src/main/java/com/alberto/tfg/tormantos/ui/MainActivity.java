package com.alberto.tfg.tormantos.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alberto.tfg.tormantos.R;
import com.alberto.tfg.tormantos.service.sensor.SensorListenerService;
import com.intentfilter.androidpermissions.PermissionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.util.Collections.singleton;

/**
 * Main Activity controller.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Keys
     */
    private static final String TAG = "TORMANTOS MAIN ACTIVITY";

    /**
     * UI widgets
     */
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.TextViewTormantosHome)
    TextView TextViewAndroidUUID;

    @BindView(R.id.TextViewAccess)
    TextView textViewAccess;

    @BindView(R.id.follow_me_to_accessibility)
    Button launchAccessibilitySettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupElements();
        requestPermissions();
    }

    @Override
    public void onResume(){
        super.onResume();
        if (isAccessibilitySettingsOn(this.getApplicationContext())) {
            this.textViewAccess.setVisibility(View.GONE);
            this.launchAccessibilitySettingsButton.setVisibility(View.GONE);
            Log.d(TAG, "Accessibility service is enabled");
        } else {
            this.textViewAccess.setVisibility(View.VISIBLE);
            this.launchAccessibilitySettingsButton.setVisibility(View.VISIBLE);
            Log.d(TAG, "Accessibility service is DISABLED");
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
        int id = item.getItemId();
        if (id == R.id.communication) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, new CommunicationFragment()).commit();
        } else if (id == R.id.instant_messaging) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, new MessagingFragment()).commit();

        } else if (id == R.id.browsers) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, new BrowsersFragment()).commit();
        } else if (id == R.id.system) {
            // Show the notifications related stored content
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, new SystemFragment()).commit();
        }

        // android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // fragmentTransaction.add(R.id.main_content, dbContentFragment, DBContentFragment.TAG).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Widgets events
     */
    @OnClick(R.id.follow_me_to_accessibility)
    public void launchAccessibilitySettings(View view) {
        startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1);
    }


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

        loadSmartphoneData();
    }

    /**
     * Checks if the accessibility service 'MonitorService' is enabled in the
     * user accessibility settings
     *
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
            return true;
        }
        return false;
    }

    private void requestPermissions() {

        final Context context = this.getApplicationContext();
        PermissionManager permissionManager = PermissionManager.getInstance(context);
        permissionManager.checkPermissions(singleton(Manifest.permission.ACCESS_FINE_LOCATION), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                startService(new Intent(context, SensorListenerService.class));
                Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(context, "Permissions Denied", Toast.LENGTH_SHORT).show();
            }
        });

        if (isAccessibilitySettingsOn(context)) {
            this.textViewAccess.setVisibility(View.GONE);
            this.launchAccessibilitySettingsButton.setVisibility(View.GONE);
            Log.d(TAG, "Accessibility service is enabled");
        } else {
            this.textViewAccess.setVisibility(View.VISIBLE);
            this.launchAccessibilitySettingsButton.setVisibility(View.VISIBLE);
            Log.d(TAG, "Accessibility service is DISABLED");
        }
    }

    private void loadSmartphoneData() {

        // --load the android uniqued identifier
        // String androidUUID= Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
        //Settings.Secure.ANDROID_ID);
        // this.androidUUID.setText(androidUUID);
    }


}
