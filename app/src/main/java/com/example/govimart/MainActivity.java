package com.example.govimart;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.transition.Visibility;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    LinearLayout topNavigationView;
    RelativeLayout mainInterfaceLayout;
    FrameLayout bottomNavBarUNIVERSAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: DIFFERENT BOTTOM BARS FOR DIFFERENT USERS , POPULATE THE FRAMELAYOUT WITH RELEVANT FRAGMENT
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_seller);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // No use yet
        bottomNavigationView = bottomNav;
        topNavigationView = findViewById(R.id.top_navigation);
        mainInterfaceLayout = findViewById(R.id.main_interface_layout);
        bottomNavBarUNIVERSAL = findViewById(R.id.bottom_navigation_bar);



        //added this if statement to keep the selected fragment when initial loading happen
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CategoriesTabFragment()).commit();
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new CategoriesTabFragment();
                            break;
                        case R.id.nav_feed:
                            selectedFragment = new FeedTabFragment();
                            break;
                        case R.id.nav_add:
                            selectedFragment = new AddTabFragment();
                            break;
                        case R.id.nav_transport:
                            selectedFragment = new TransportTabFragment();
                            break;
                    }

                    // Changing of screen
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                            .replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void onProfileTapped(View view) {

        //setContentView(R.layout.profile_layout);  //Ignore

        // If we need to get some info from mainActivity -> the fragment when Its created.Replace with below line
        //ProfileFragment profileFragment1 = ProfileFragment.newInstance("this is text ", 123);

        ProfileFragment profileFragment = new ProfileFragment();

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_fade_exit, R.anim.fragment_open_enter, R.anim.fragment_close_exit)
                .replace(R.id.master_fragment_container,
                profileFragment)
                .addToBackStack(null)
                .commit();
        //hideBottomNavBarUNIVERSAL();
        //hideTopNavBar();
        //hideMainInterface();



    }

    public void onNotificationsTapped(View view) {

        // If we need to get some info from mainActivity -> the fragment when Its created.Replace with below line
        //NotificationsFragment notificationsFragment1 = NotificationsFragment.newInstance("this is text ", 123);

        NotificationsFragment notificationsFragment = new NotificationsFragment();

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_fade_exit, R.anim.fragment_open_enter, R.anim.fragment_close_exit)
                .replace(R.id.master_fragment_container,
                        notificationsFragment)
                .addToBackStack(null)
                .commit();
    }

    public void onMessagesTapped(View view) {

        // If we need to get some info from mainActivity -> the fragment when Its created.Replace with below line
        //MessagesFragment messagesFragment1 = MessagesFragment.newInstance("this is text ", 123);

        MessagesFragment messagesFragment = new MessagesFragment();

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_fade_exit, R.anim.fragment_open_enter, R.anim.fragment_close_exit)
                .replace(R.id.master_fragment_container,
                        messagesFragment)
                .addToBackStack(null)
                .commit();
    }


    // Hiding UI elements
    public void hideBottomNavBar(){
        bottomNavigationView.setVisibility(View.GONE);
    }

    public void hideBottomNavBarUNIVERSAL(){
        bottomNavBarUNIVERSAL.setVisibility(View.GONE);
    }

    public void hideTopNavBar(){
        topNavigationView.setVisibility(View.GONE);
    }

    public void hideMainInterface(){
        mainInterfaceLayout.setVisibility(View.GONE);
    }



}
