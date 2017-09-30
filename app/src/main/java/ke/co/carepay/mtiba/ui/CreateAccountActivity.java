package ke.co.carepay.mtiba.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import ke.co.carepay.mtiba.R;
import ke.co.carepay.mtiba.services.MtibaRequests;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView userPhoneNumber;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Button createAccountButton;
    private String phoneNumber;
//    private EditText;
//    private EditText;
//    private EditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        userPhoneNumber = (TextView) findViewById(R.id.userPhoneNumber);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmNewPassword = (EditText) findViewById(R.id.confirmNewPassword);
        createAccountButton = (Button) findViewById(R.id.createPasswordButton);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        userPhoneNumber.setText(phoneNumber);
        createAccountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == createAccountButton){
            Log.d("button", "you have pressed me");
            MtibaRequests mtibaRequests = new MtibaRequests();
            String username = userPhoneNumber.getText().toString().trim();
            String password = newPassword.getText().toString().trim();
            String passwordConfirmation = confirmNewPassword.getText().toString().trim();

//            String firstName =;
//            String secondName =;
//            String lastName=;
//            String idNumber =;
//            Long dateOfBirth=;






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
                        Log.d("createpassword", Integer.toString(response.code()));
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
}
