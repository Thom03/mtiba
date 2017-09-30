package ke.co.carepay.mtiba.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ke.co.carepay.mtiba.R;
import ke.co.carepay.mtiba.models.AccountHolder;
import ke.co.carepay.mtiba.services.MtibaRequests;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private EditText firstName;
    private EditText middleName;
    private EditText lastName;
    private EditText dateOfBirth;
    private Spinner sex;
    private EditText nationalIdNumber;
    private Button createAccountButton;
    private String selectedGender;
    private int userRef;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        firstName = (EditText) findViewById(R.id.firstName);
        middleName = (EditText) findViewById(R.id.middleName);
        lastName = (EditText) findViewById(R.id.lastName);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        nationalIdNumber = (EditText) findViewById(R.id.nationalIdNumber);
        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this);

        Spinner sex = (Spinner) findViewById(R.id.sex);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sex.setAdapter(adapter);

        userRef = Integer.parseInt(getIntent().getStringExtra("id"));
        phoneNumber = getIntent().getStringExtra("phoneNumber");
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        selectedGender = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        selectedGender = "M";
    }

    @Override
    public void onClick(View view) {
        if(view == createAccountButton){
            String FirstName = firstName.getText().toString().trim();
            String SecondName = middleName.getText().toString().trim();
            String LastName = lastName.getText().toString().trim();
            Long DateOfBirth = Long.parseLong(dateOfBirth.getText().toString());
            String NationalId = nationalIdNumber.getText().toString();


            MtibaRequests mtibaRequests = new MtibaRequests();
            HashMap<String, String> identification = new HashMap<>();
            identification.put("identificationType", "NATIONAL_ID");
            identification.put("idNumber", NationalId);
            List<String> ids = new ArrayList<>();

            AccountHolder accountHolder = new AccountHolder(FirstName, SecondName, LastName, DateOfBirth, selectedGender,identification, userRef, phoneNumber);
            mtibaRequests.createNewAccount(accountHolder, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("create acc",Integer.toString(response.code()));
                    Log.d("create acc ", response.body().string());
                }
            });
        }
    }
}
