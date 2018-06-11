package com.example.cora.sportverwaltung.activity.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.cora.sportverwaltung.R;
import com.example.cora.sportverwaltung.activity.account.LoginActivity;
import com.example.cora.sportverwaltung.activity.account.ProfileActivity;
import com.example.cora.sportverwaltung.activity.events.EventsSwipeActivity;
import com.example.cora.sportverwaltung.activity.settings.SettingsActivity;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncTaskHandler;
import com.example.cora.sportverwaltung.businesslogic.connection.AsyncWebserviceTask;

import static com.example.cora.sportverwaltung.businesslogic.misc.HttpMethod.GET;

/**
 * @kandut generalization from other activities
 */

public class BaseActivity extends ExposingActivity implements NavigationView.OnNavigationItemSelectedListener, AsyncTaskHandler {

    // UI references
    private FrameLayout contentContainer;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // set up views
        initToolbar();
        initDrawer();
        initNav();
        initContainer();
    }

    private void initDrawer() {
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initContainer() {
        contentContainer = findViewById(R.id.content_container);
    }

    private void initNav() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void setContent(@LayoutRes int layout) {
        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            layoutInflater.inflate(layout, contentContainer );
        } else {
            System.out.println("layoutInflater was null in setContent of BaseActivity");
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
        getMenuInflater().inflate(R.menu.menu_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.nav_profile:
                    startActivity(new Intent(this, ProfileActivity.class));
                    break;
                case R.id.nav_events:
                    startActivity(new Intent(this, EventsSwipeActivity.class));
                    break;
                case R.id.nav_logout:
                    AsyncWebserviceTask task = new AsyncWebserviceTask(GET, "logout", this, getApplicationContext());
                    task.execute();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPreExecute() {
        progDialog = new ProgressDialog(this);
        progDialog.setMessage("Processing...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    public void onSuccess(int statusCode, String content) {
        progDialog.dismiss();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onError(Error err) {
    progDialog.cancel();
        Toast.makeText(this, err.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
