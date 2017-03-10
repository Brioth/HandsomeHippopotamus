package com.pxl.opdrachten.securityapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.services.FileHandler;
import com.pxl.opdrachten.securityapp.services.RSA.GenerateRSAKeys;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadNavigation();
        CreateKeys();

    }

    public void CreateKeys(){

        GenerateRSAKeys rsaGeneratorAlice = new GenerateRSAKeys();
        rsaGeneratorAlice.CreateRSAKeys();
        FileHandler.WriteBytesToFile(this, getString(R.string.private_A), rsaGeneratorAlice.GetPrivateKey().getEncoded());
        FileHandler.WriteBytesToFile(this, getString(R.string.public_A), rsaGeneratorAlice.GetPublicKey().getEncoded() );

        GenerateRSAKeys rsaGeneratorBob = new GenerateRSAKeys();
        rsaGeneratorBob.CreateRSAKeys();
        FileHandler.WriteBytesToFile(this,getString(R.string.private_B), rsaGeneratorBob.GetPrivateKey().getEncoded() );
        FileHandler.WriteBytesToFile(this, getString(R.string.public_B), rsaGeneratorBob.GetPublicKey().getEncoded() );

    }

    public void loadNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Alice"));
        tabLayout.addTab(tabLayout.newTab().setText("Bob"));
        tabLayout.addTab(tabLayout.newTab().setText("Files"));
        tabLayout.addTab(tabLayout.newTab().setText("Image"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        ArrayList<File> filesToDelete = new ArrayList<File>();

        File dir = getFilesDir();

        filesToDelete.add(new File(dir, getString(R.string.encryptedText_A)));
        filesToDelete.add(new File(dir, getString(R.string.encryptedAESKey_A)));
        filesToDelete.add(new File(dir, getString(R.string.hash_A)));
        filesToDelete.add(new File(dir, getString(R.string.encryptedText_B)));
        filesToDelete.add(new File(dir, getString(R.string.encryptedAESKey_B)));
        filesToDelete.add(new File(dir, getString(R.string.hash_B)));

        for (File file : filesToDelete){
            if(file.exists()){
                file.delete();
            }
        }




    }

}
