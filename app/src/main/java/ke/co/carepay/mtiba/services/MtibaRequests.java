package ke.co.carepay.mtiba.services;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ke.co.carepay.mtiba.utils.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by kingkong on 9/29/17.
 */

public class MtibaRequests {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    //check if a user
    public void checkUserExists(String phoneNumber, Callback callback){
        OkHttpClient client = new OkHttpClient();
        String url = Constants.MTIBA_BASE_URL+Constants.MTIBA_CHECK_USER_URL+phoneNumber;
        Request request = new Request.Builder()
                .header("Authorization", Constants.JWT_TOKEN)
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public void createNewAccount(String FirstName, String SecondName, String LastName,String NationalIdNumber,Long DateOfBirth, String gender, int userRef, String phoneNumber, Callback callback){
        JSONObject json = new JSONObject();
        JSONObject jsonNationalId = new JSONObject();
        try{
            jsonNationalId.put("identificationType", "NATIONAL_ID");
            jsonNationalId.put("idNumber", NationalIdNumber);
        }catch (JSONException e){
            e.printStackTrace();
        }

        try{
            json.put("@c",".AccountHolder");
            json.put("firstName", FirstName);
            json.put("middleName", SecondName);
            json.put("lastName", LastName);
            json.put("dateOfBirth", DateOfBirth);
            json.put("gender", gender);
            json.put("identification", jsonNationalId);
            json.put("userRef", userRef);
            json.put("phoneNumber",phoneNumber);
        }catch (JSONException e){
            e.printStackTrace();
        }
        String url = "http://program-service-test.ap-southeast-1.elasticbeanstalk.com/accountholders";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .header("Authorization", Constants.JWT_TOKEN)
                .header("Accept","*/*")
                .url(url)
                .method("POST", body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);


    }
    //create a new password
    public void createNewPassword(String phoneNumber, String password, Callback callback){
        JSONObject json = new JSONObject();
        try{
            json.put(Constants.API_USERNAME_KEY,phoneNumber);
            json.put(Constants.API_PASSWORD_KEY, "cc");
            json.put(Constants.API_NEW_PASSWORD_KEY, password);
        }catch (JSONException e){
            e.printStackTrace();
        }

        String url = Constants.MTIBA_BASE_URL+Constants.MTIBA_CREATE_PASSWORD_URL;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .header("Authorization", Constants.JWT_TOKEN)
                .header("Accept","*/*")
                .url(url)
                .method("POST", body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    //login user
    public void login(String username, String password, Callback callback){
        JSONObject json = new JSONObject();
        try{
            json.put(Constants.API_USERNAME_KEY,username);
            json.put(Constants.API_PASSWORD_KEY, password);
        }catch (JSONException e){
            e.printStackTrace();
        }
        String url = Constants.MTIBA_BASE_URL+Constants.MTIBA_LOGIN_URL;
        RequestBody body = RequestBody.create(JSON, json.toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("Authorization", Constants.JWT_TOKEN)
                .header("Accept","*/*")
                .url(url)
                .method("POST", body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    //logout
    public static void logout(){

    }
}
