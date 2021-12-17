package com.taimoorsikander.cityguide.User;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.taimoorsikander.cityguide.Common.LoginSignup.Login;
import com.taimoorsikander.cityguide.Common.LoginSignup.RetailerStartUpScreen;
import com.taimoorsikander.cityguide.Databases.SessionManager;
import com.taimoorsikander.cityguide.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.taimoorsikander.cityguide.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.taimoorsikander.cityguide.HelperClasses.HomeAdapter.FeaturedAdpater;
import com.taimoorsikander.cityguide.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.taimoorsikander.cityguide.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.taimoorsikander.cityguide.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.taimoorsikander.cityguide.LocationOwner.RetailerDashboard;
import com.taimoorsikander.cityguide.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    static final float END_SCALE = 0.7f;

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menuIcon, loginSignUpBtn;
    LinearLayout contentView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        loginSignUpBtn = findViewById(R.id.login_signup);
      //  EditText Login = findViewById(R.id.nav_login);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        naviagtionDrawer();

        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();

        loginSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }


    private void naviagtionDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        drawerLayout.setDrawerElevation(0);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_all_categories:
                startActivity(new Intent(getApplicationContext(), AllCategories.class));
                break;
        }

        return true;
    }

    private void categoriesRecycler() {

        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.logo_homsa3, "Partes"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient2, R.drawable.logo_homsa3, "Ausencias"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient3, R.drawable.logo_homsa3, "asistencias"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient4, R.drawable.logo_homsa3, "gastos"));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.logo_homsa3, "Proyecto de Olarra"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.logo_homsa3, "Proyecto de Cotsco"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.logo_homsa3, "Proyecto ..."));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.logo_homsa3, "Proyecto"));

        adapter = new MostViewedAdapter(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);

    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.logo_homsa3, "Proyecto Olarra", "Descripcion del proyecto Olarra"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.logo_homsa3, "Proyecto Cotso", "Decripcion del proyecto de Cotso"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.logo_homsa3, "Proyecto Olarra 2", "Descripcion del proyecto de Olarra 2"));

        adapter = new FeaturedAdpater(featuredLocations);
        featuredRecycler.setAdapter(adapter);


    }

    public void callRetailerScreens(View view) {
        SessionManager sessionManager = new SessionManager(UserDashboard.this, SessionManager.SESSION_USERSESSION);
        if (sessionManager.checkLogin())
            startActivity(new Intent(getApplicationContext(), RetailerDashboard.class));
        else
            startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    public void login(View view){
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
    }
}
