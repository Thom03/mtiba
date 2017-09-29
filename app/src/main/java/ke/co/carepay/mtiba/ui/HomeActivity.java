package ke.co.carepay.mtiba.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ke.co.carepay.mtiba.R;
import ke.co.carepay.mtiba.utils.Constants;

public class HomeActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        String user_token = mSharedPreferences.getString(Constants.USER_TOKEN,null);
        if(user_token==null){
            Intent intent = new Intent(HomeActivity.this, PhoneInputActivity.class);
            startActivity(intent);
        }
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.USER_TOKEN, location).apply();
    }
}
