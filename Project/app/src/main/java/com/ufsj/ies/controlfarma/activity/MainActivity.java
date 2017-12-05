package com.ufsj.ies.controlfarma.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufsj.ies.controlfarma.R;
import com.ufsj.ies.controlfarma.adapter.TabAdapter;
import com.ufsj.ies.controlfarma.config.ConfiguracaoFirebase;
import com.ufsj.ies.controlfarma.helper.Preferencias;
import com.ufsj.ies.controlfarma.helper.SlidingTabLayout;

public class MainActivity extends AppCompatActivity{
    private FirebaseAuth firebaseAuth;
    private FragmentManager fragmentManager;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceAux;
    private Toolbar toolbar;
    private Integer numVeiculos;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    //private Preferencias preferencias;
    private String[] permissoesNecessarias = new String[]{
            android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ControlFarma");
        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.pagina_view);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorGreen));

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_drawer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_logout:
                logoutUsuario();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void logoutUsuario(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }
}
