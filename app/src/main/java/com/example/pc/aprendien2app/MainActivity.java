package com.example.pc.aprendien2app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.pc.aprendien2app.Settings.SettingsActivity;
import com.example.pc.aprendien2app.Tabs.InicioFragment;
import com.example.pc.aprendien2app.Tabs.PagerAdapter;

import java.util.ArrayList;

public class MainActivity extends BasicActivity implements  InicioFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView.Adapter rAdapter;
    private ArrayList<Book> newBooks;
    private TabLayout tab;
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private  NavigationView navigationView;

    private DrawerLayout mDrawerLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(v.getContext(), AddAdvert.class);
                startActivity(c);
            }
        });

        setupDrawerLayout();
        initList();
        initTabs();
    }

    @Override
    public String getActivityText() {
        return getString(R.string.start) + "\n"  + getString(R.string.new_exchanges) + "\n" + getString(R.string.news) + "\n" + getString(R.string.Events);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent c;

        switch (item.getItemId()) {
            case R.id.action_settings:
                c = new Intent(this, SettingsActivity.class);
                startActivity(c);
                finish();

                return true;

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.voice_bar:
                promptSpeechInput();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void initList() {
        newBooks = new ArrayList<>();

        /*newBooks.add(new Book("Harry Potter", R.drawable.portada));
        newBooks.add(new Book("Harry Potter", R.drawable.portada));
        newBooks.add(new Book("Harry Potter", R.drawable.portada));
        newBooks.add(new Book("Harry Potter", R.drawable.portada));
        newBooks.add(new Book("Harry Potter", R.drawable.fotonoticia_20160410121623_1280));
        newBooks.add(new Book("Harry Potter", R.drawable.portada));


        recyclerView = (RecyclerView) findViewById(R.id.cardView);
        recyclerView.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(rLayoutManager);
        rAdapter = new BookAdapter(newBooks);
        recyclerView.setAdapter(rAdapter);*/
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void initTabs() {
        tab = (TabLayout) findViewById(R.id.tabs);
        tab.addTab(tab.newTab().setText(R.string.start));
        tab.addTab(tab.newTab().setText(R.string.new_exchanges));
        tab.addTab(tab.newTab().setText(R.string.news));
        tab.addTab(tab.newTab().setText(R.string.Events));

        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tab.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void gotoQR () {
        Intent c = new Intent(this,ReadQR.class);

    }

    private void setupDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
               Intent c;

               Log.d("sddsd","sdsdsdsdsdsdsdsds");
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.menu_receive:
                        c = new Intent(getApplicationContext(),ReadQR.class);
                        startActivity(c);
                        finish();
                        return true;
                    case R.id.menu_deliver:
                        c = new Intent(getApplicationContext(),Deliver.class);
                        startActivity(c);
                        finish();
                        return true;

                }
                return true;
            }
        });
    }
}
