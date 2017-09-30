package ke.co.carepay.mtiba.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ke.co.carepay.mtiba.R;
import ke.co.carepay.mtiba.services.MtibaRequests;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PhoneInputActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText phoneNumberInput;
    private Button confirmPhoneNumberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_input);
        phoneNumberInput = (EditText) findViewById(R.id.phoneNumberInput);
        confirmPhoneNumberButton = (Button) findViewById(R.id.confirmPhoneNumberButton);
        confirmPhoneNumberButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MtibaRequests mtibaRequests = new MtibaRequests();
        if(view == confirmPhoneNumberButton){
            String phoneNumber = phoneNumberInput.getText().toString().trim();
            mtibaRequests.checkUserExists(phoneNumber, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //Something went wrong do something
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try{
                        String jsonData = response.body().string();
                        if (response.isSuccessful()){
                            JSONObject userObject = new JSONObject(jsonData);
                            boolean hasPassword = userObject.getBoolean("hasPassword");
                            if (!hasPassword){
                                Intent intent = new Intent(getApplicationContext(), CreatePasswordActivity.class);
                                String phoneNumber = phoneNumberInput.getText().toString().trim();
                                intent.putExtra("phoneNumber",phoneNumber);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                String phoneNumber = phoneNumberInput.getText().toString().trim();
                                intent.putExtra("phoneNumber",phoneNumber);
                                startActivity(intent);
                            }
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
