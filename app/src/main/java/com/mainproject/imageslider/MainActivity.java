package com.mainproject.imageslider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    //for viewpager
    private ViewPager viewPager;
    private LinearLayout sliderDots;
    private int dotsCount;
    private ImageView[] dots;

   /* List of Districts : Constants*/

   private Button districtButton;
   private LinearLayout districtLinearList;
   private ListView districtListView;
    private ArrayList<String> districtList;
   private ArrayAdapter listAdapter;
   View layoutView;



   //function to populate the list of Districts
    public void populateList()
    {

        districtList=new ArrayList<>(Arrays.asList("Agra","Aligarh", "Allahabad", "Ambedkar Nagar", "Auraiya", "Azamgarh", "Bagpat", "Bahraich", "Balrampur", "Ballia", "Banda", "Barabanki", "Bareilly",
                "Basti", "Bijnor", "Budaun", "Bulandshahr", "Chandauli", "Chitrakoot", "Deoria", "Etah",
                "Etawah", "Faizabad", "Farrukhabad", "Fatehpur", "Firozabad", "Gautam Buddha Nagar", "Ghaziabad", "Ghazipur",
                "Gonda", "Gorakhpur", "Hamirpur", "Hardoi", "Hathras", "Jalaun", "Jaunpur", "Jhansi",
                "Jyotiba Phule Nagar", "Kannauj", "Kanpur Dehat", "Kanpur Nagar", "Kaushambi", "Kheri", "Kushinagar",
                "Lalitpur", "Lucknow", "Mahobaaharajganjnj", "Mathura", "Mainpuri", "Meerut", "Mirzapur",
                "Moradabad", "Muzaffarnagaragar", "Pilibhit", "Pratapgarhh", "RaeBareli", "Rampur", "Saharanpur", "Sant Kabir Nagar",
                "Sant Ravidas Nagar", "Shravasti", "Siddharthnagar", "Sitapur", "Sonbhadra", "Sultanpurnasi", "Unnao", "Manyavar Kanshiram Nagar",
                "Prabuddha Nagar", "Shamli", "Panchsheel Nagar", "Hapur", "Bhim Nagar", "Chandausi"));

        Collections.sort(districtList);
    }
    //timer task for viewpager to auto slide images
    public class MyTimerTask extends TimerTask
    {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem()==0)
                    {
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem()==1)
                    {
                        viewPager.setCurrentItem(2);
                    }else if (viewPager.getCurrentItem()==2)
                    {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //for implementation of View Pager
        viewPager=findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.ReturnMonth();
        int month=viewPagerAdapter.getMonth();
        if (month!=0)
        {
            if (month==2)
            {
                Integer [] images={R.drawable.month21,R.drawable.month22,R.drawable.month23};
                viewPagerAdapter.setImages(images);
            }
        }

        sliderDots=findViewById(R.id.sliderDots);
        dotsCount=viewPagerAdapter.getCount();
        dots=new ImageView[dotsCount];

        for (int i=0;i<dotsCount;i++)
        {
            dots[i]= new ImageView(MainActivity.this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDots.addView(dots[i],params);

        }



        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i=0;i<dotsCount;i++)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


       /* for displaying the list of districts*/

       districtButton=findViewById(R.id.districtButton);
       districtLinearList=findViewById(R.id.displayLinearList);
       populateList();
       listAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,districtList);

       LayoutInflater layoutInflater=getLayoutInflater();
       layoutView= layoutInflater.inflate(R.layout.custom_list_layout,null);
       districtListView=layoutView.findViewById(R.id.customDistrictList);
       districtListView.setAdapter(listAdapter);
       TextView customTextView= layoutView.findViewById(R.id.customTextView);

       customTextView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_down_animation);
               animation.setStartOffset(0);
               districtLinearList.startAnimation(animation);
               districtLinearList.removeView(layoutView);
               districtButton.setVisibility(View.VISIBLE);
           }
       });

       districtListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

           }
       });

        districtButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               districtLinearList.removeAllViews();
               districtLinearList.addView(layoutView);
               districtButton.setVisibility(View.INVISIBLE);

               Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.slide_up_animation);
               animation.setStartOffset(0);
               districtLinearList.startAnimation(animation);

           }
       });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(districtLinearList!=null)
        {
            districtLinearList.removeView(layoutView);
            districtButton.setVisibility(View.VISIBLE);
        }
        else {
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
