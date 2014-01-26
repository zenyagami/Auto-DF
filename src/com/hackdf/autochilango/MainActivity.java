/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hackdf.autochilango;

import com.hackdf.autochilango.fragments.FragmentBluetooth;
import com.hackdf.autochilango.fragments.FragmentInfoAire;
import com.hackdf.autochilango.fragments.FragmentInfoPlaca;
import com.hackdf.autochilango.fragments.FragmentSetPlate;
import com.hackdf.autochilango.fragments.FragmentlInfoEstacionamiento;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hackdf.autochilango.fragments.FragmentInfoNoCircula;
import com.hackdf.autochilango.fragments.FragmentInfoVerificentro;

import com.hackdf.autochilango.preferences.AppPreferences;
import com.hackdf.autochilango.service.ServiceBluetoothReceiver;


public class MainActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int NAVIGATION_INFO_PLACA=0;
    private static final int NAVIGATION_ROBO=1;
    private static final int NAVIGATION_VERIFICENTRO=2;
    private static final int NAVIGATION_ESTACIONAMIENTO=3;
    private static final int NAVIGATION_HOY_NO_CIRCULA=4;
    private static final int NAVIGATION_CALIDAD_AIRE=5;
    private static final int NAVIGATION_ENCUENTRA_LLAVES=6;
    
    
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(AppPreferences.getCurrentPlate(getApplicationContext()).equals(""))
        {
        	//si no tiene placa mandamos a "verificar" su placa
        	//Actividad de verificar placa :)
        	startActivity(new Intent(getApplicationContext(), ActivitySetPlate.class));
        	finish();
        	return;
        }
        
        startService(new Intent(getApplicationContext(), ServiceBluetoothReceiver.class));
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        
        switch (position) {
		case NAVIGATION_INFO_PLACA:
			fragment  = new FragmentInfoPlaca();
			break;
		case NAVIGATION_ROBO: 
			fragment = FragmentSetPlate.newInstance(true);
			break;
		case NAVIGATION_VERIFICENTRO:
			fragment= new FragmentInfoVerificentro();
			break;
		case NAVIGATION_ESTACIONAMIENTO: 
			fragment= new FragmentlInfoEstacionamiento();
			break;
		case NAVIGATION_HOY_NO_CIRCULA:
			fragment= new FragmentInfoNoCircula();
			break;
		case NAVIGATION_ENCUENTRA_LLAVES: 
			fragment  = new  FragmentBluetooth();
			break;
		case NAVIGATION_CALIDAD_AIRE:
			fragment= new FragmentInfoAire();
			break;
		default:
			fragment = new FragmentSetPlate();
			break;
		}
        FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
	protected void onDestroy() {
    	stopService(new Intent(getApplicationContext(), ServiceBluetoothReceiver.class));
		super.onDestroy();
	}

	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

   
}
