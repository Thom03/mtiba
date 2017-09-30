package ke.co.carepay.mtiba.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ke.co.carepay.mtiba.R;
import ke.co.carepay.mtiba.services.MtibaRequests;
import ke.co.carepay.mtiba.utils.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText loginUserName;
    private EditText loginPassword;
    private Button loginButton;
    private String username;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginUserName = (EditText) findViewById(R.id.loginUserName);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        username = getIntent().getStringExtra("phoneNumber").toString();
        loginUserName.setText(username);
        loginButton.setOnClickListener(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
    }

    @Override
    public void onClick(View view) {
        if(view == loginButton){
            MtibaRequests mtibaRequests = new MtibaRequests();
<<<<<<< HEAD
=======
            final String username = loginUserName.getText().toString().trim();
>>>>>>> 96a4f7f04766ed2a1500b54f7f0c7a5319e697bd
            String password = loginPassword.getText().toString().trim();
            if(password==null){
                loginPassword.setError("Password cannot be empty");
            }

            mtibaRequests.login(username, password, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()){
                        try {
                            String jsonData = response.body().string();
                            if (response.isSuccessful()) {
                                JSONObject jsonObject = new JSONObject(jsonData);
                                String token = jsonObject.getString("token");
                                mEditor.putString(Constants.USER_TOKEN, token);
                                mEditor.commit();

                                String userType = jsonObject.getString("@c");
                                String phoneNumber = jsonObject.getString("name");
                                String id = jsonObject.getString("id");
                                Log.d("status", userType);
                                if(userType.equals(".AccountHolder")){

                                    Intent intent = new Intent(LoginActivity.this, Mtiba.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                                    intent.putExtra("phoneNumber", phoneNumber);
                                    intent.putExtra("id", id);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        }catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                }
            });


        }
    }
}
