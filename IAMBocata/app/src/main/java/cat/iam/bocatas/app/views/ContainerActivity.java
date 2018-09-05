package cat.iam.bocatas.app.views;




import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import cat.iam.bocatas.app.R;
import cat.iam.bocatas.app.model.AllInfoObject;
import cat.iam.bocatas.app.model.AllInfoObjectContainer;
import cat.iam.bocatas.app.serverconnection.HistorialDownloader;
import cat.iam.bocatas.app.views.fragments.AboutFragment;
import cat.iam.bocatas.app.views.fragments.CheckOutFragment;
import cat.iam.bocatas.app.views.fragments.FavouritesFragment;
import cat.iam.bocatas.app.views.fragments.HistorialCompresFragment;
import cat.iam.bocatas.app.views.fragments.MainFragment;
import cat.iam.bocatas.app.views.fragments.ProductsFragment;
import cat.iam.bocatas.app.views.fragments.QRFragment;
import cat.iam.bocatas.app.views.settings.SettingsActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Main Activity of the app.
 */

public class ContainerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String INFO = "info";
    Toolbar toolbar;

    ProductsFragment productsFragment;
    QRFragment qrFragment;
    MainFragment mainFragment;
    FavouritesFragment favouritesFragment;
    CheckOutFragment checkOutFragment;

    AllInfoObject allInfoObject;

    String lastTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Boolean checkout = getIntent().getBooleanExtra("checkout", false);

        if (savedInstanceState != null) {
            allInfoObject = (AllInfoObject)savedInstanceState.getSerializable(INFO);
            AllInfoObjectContainer.setAllInfoObject(allInfoObject);
        } else {
            allInfoObject = AllInfoObjectContainer.getAllInfoObject();
        }

        lastTitle = getString(R.string.app_name);

        // ------------------------ Toolbar ---------------------------------------------------------
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ------------------------ Navigation Drawer -----------------------------------------------
        prepareNavigationDrawer();

        // ------------------------ Fragments -------------------------------------------------------
        productsFragment = new ProductsFragment();
        productsFragment.setProducts(allInfoObject.getProductList());

        mainFragment = new MainFragment();
        qrFragment = new QRFragment();
        qrFragment.setUrlQr(allInfoObject.getUser().getFullQrUrl());
        favouritesFragment = new FavouritesFragment();
        checkOutFragment = new CheckOutFragment();

        if (checkout) {
            loadThisFragment(checkOutFragment);
        } else {
            loadThisFragment(mainFragment);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        lastTitle = toolbar.getTitle().toString();

        if (id == R.id.nav_main) {
            toolbar.setTitle(getString(R.string.app_name));
            loadThisFragment(mainFragment);
        } else if (id == R.id.nav_products) {
            toolbar.setTitle(getString(R.string.titleProductsFragment));
            loadThisFragment(productsFragment);
        } else if (id == R.id.nav_history) {
            toolbar.setTitle(getString(R.string.titleHistoryFragment));
            new HistorialDownloader(this).execute();
        } else if (id == R.id.nav_favorites) {
            toolbar.setTitle(getString(R.string.titleFavouritesFragment));
            loadThisFragment(favouritesFragment);
        } else if (id == R.id.nav_cart) {
            toolbar.setTitle(getString(R.string.titleCheckOutFragment));
            loadThisFragment(checkOutFragment);
        } else if (id == R.id.nav_money) {
            toolbar.setTitle(getString(R.string.titleAddMoneyFragment));
            loadThisFragment(qrFragment);
        } else if (id == R.id.nav_config) {
            goToActivity(SettingsActivity.class);
        } else if (id == R.id.nav_about) {
            loadThisFragment(AboutFragment.newInstance());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToActivity(Class className){
        Intent intent = new Intent(getApplicationContext(), className);
        startActivity(intent);
    }

    public void loadThisFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }

    public void prepareNavigationDrawer() {
        // ------------------------ Mierda Navigation Drawer ----------------------------------------
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView navigationDrawerUsername = (TextView) header.findViewById(R.id.profileUserTextView);
        navigationDrawerUsername.setText(allInfoObject.getUser().getName());

        TextView navigationDrawerUserMoney = (TextView) header.findViewById(R.id.profileMoneyTextView);
        navigationDrawerUserMoney.setText(
                new DecimalFormat("#0.00").format(allInfoObject.getUser().getMoney()) + " €");

        CircleImageView circleImageView = (CircleImageView) header.findViewById(R.id.profileImageView);
        Glide.with(getApplicationContext()).load(allInfoObject.getUser().getUrlPhoto()).into(circleImageView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        toolbar.setTitle(lastTitle);

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(INFO, allInfoObject);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

}
