package com.spsigt.kalender4g3s2015;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;





@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class MainActivity extends Activity {

	String[] menutitles;
	TypedArray menuIcons;
	String[] pageUrl;
	Menu menu;

	// nav drawer title
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private List<RowItem> rowItems;
	private CustomAdapter adapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		
		mTitle = mDrawerTitle = getTitle();

		menutitles = getResources().getStringArray(R.array.titles);
		menuIcons = getResources().obtainTypedArray(R.array.icons);
		pageUrl = getResources().getStringArray(R.array.pageurl);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.slider_list);

		rowItems = new ArrayList<RowItem>();

		for (int i = 0; i < menutitles.length; i++) {
			RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(
					i, -1), pageUrl[i]);
			rowItems.add(items);
		}

		menuIcons.recycle();

		adapter = new CustomAdapter(getApplicationContext(), rowItems);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new SlideitemListener());

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			updateDisplay(0);

		}
	}

	class SlideitemListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			updateDisplay(position);
		}

	}

	
	private void updateDisplay(int position) {

		String url = rowItems.get(position).getPageUrl();

		Fragment fragment = new MyWebViewFragment();

		Bundle bundle = new Bundle();
		bundle.putString("url", url);

		fragment.setArguments(bundle);

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();

		setTitle(menutitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		
		this.menu = menu;
    	
    	menu.add(0, 1, 0, "Catatan Lemburan").setIcon(android.R.drawable.btn_plus);
        menu.add(0, 2, 0, "Exit").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
    	return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected((android.view.MenuItem) item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case 1:
			Intent Catatan = new Intent (this, Catatan.class);
			startActivity(Catatan);
			return true;
		case 2:
            finish();
            return true;
		default:
			return super.onOptionsItemSelected((android.view.MenuItem) item);
		}
	}

	
	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */

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
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
