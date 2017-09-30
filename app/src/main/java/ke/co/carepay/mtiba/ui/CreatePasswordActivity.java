package ke.co.carepay.mtiba.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ke.co.carepay.mtiba.R;
import ke.co.carepay.mtiba.services.MtibaRequests;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreatePasswordActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private TextView userPhoneNumber;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Button createPasswordButton;
    private String phoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        userPhoneNumber = (TextView) findViewById(R.id.userPhoneNumber);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmNewPassword = (EditText) findViewById(R.id.confirmNewPassword);
        createPasswordButton = (Button) findViewById(R.id.createPasswordButton);



        phoneNumber = getIntent().getStringExtra("phoneNumber");
        userPhoneNumber.setText(phoneNumber);
        createPasswordButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == createPasswordButton){
            Log.d("button", "you have pressed me");
            final MtibaRequests mtibaRequests = new MtibaRequests();
            String username = userPhoneNumber.getText().toString().trim();
            String password = newPassword.getText().toString().trim();
            String passwordConfirmation = confirmNewPassword.getText().toString().trim();



            boolean isvalidPassword = isPasswordValid(password, passwordConfirmation);
            if(!isvalidPassword){
                newPassword.setError("Password should be 6 characters or more");
            }else if(!password.equals(passwordConfirmation)){
                confirmNewPassword.setError("Passwords do not match");
            }else{
                mtibaRequests.createNewPassword(phoneNumber, password, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("createpassword", "error");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mtibaRequests.checkUserExists(phoneNumber, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                if (response.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    intent.putExtra("phoneNumber", phoneNumber);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                });
            }
        }
    }
    public boolean isPasswordValid(String password, String confirmPassword){
        if(password.length()<6){
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
