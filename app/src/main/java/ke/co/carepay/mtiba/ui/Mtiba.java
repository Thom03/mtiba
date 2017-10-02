package ke.co.carepay.mtiba.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ke.co.carepay.mtiba.R;
import ke.co.carepay.mtiba.services.MtibaRequests;
import ke.co.carepay.mtiba.utils.Constants;
import ke.co.carepay.mtiba.utils.NavigationItemSelector;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Mtiba extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private TextView nameOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtiba);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        String user_token = mSharedPreferences.getString(Constants.USER_TOKEN,null);
        if(user_token==null){
            Intent intent = new Intent(Mtiba.this, PhoneInputActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        MtibaRequests mtibaRequests = new MtibaRequests();

        int userRef = mSharedPreferences.getInt("id",0);
        nameOfUser = (TextView) findViewById(R.id.nameOfUser);
        mtibaRequests.getUserDetails(user_token, getApplicationContext(), userRef, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ArrayList<JSONObject> objects;
                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {


                        Gson gson = new Gson();
                        List stuff = gson.fromJson(jsonData, List.class);
                        JSONObject stuffneeded = (JSONObject) stuff.get(0);
                        JSONArray beneficiaries = stuffneeded.getJSONArray("beneficiaries");

                        JSONObject bene1 = beneficiaries.getJSONObject(0);
                        String fname = bene1.getString("firstName");
                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONArray jsonArray = jsonObject.getJSONArray(jsonData);
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//                        JSONArray jsonArray1 = jsonObject1.getJSONArray("beneficiaries");
//                        String fName = jsonArray1.getJSONObject(0).getString("firstName");
//                        String sName = jsonArray1.getJSONObject(0).getString("middleName");
//                        nameOfUser.setText(fName + " " + sName);
//                        Log.d("User Details", jsonArray.toString());
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mtiba, menu);
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
        return NavigationItemSelector.getSelectedMenus(item, drawer, getApplicationContext());
    }
    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.USER_TOKEN, location).apply();
    }
}