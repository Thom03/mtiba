package ke.co.carepay.mtiba.services;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import ke.co.carepay.mtiba.models.AccountHolder;
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
    JSONObject jsonFinal = new JSONObject();

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
    public void createNewAccount(AccountHolder accountHolder, Callback callback){

        Gson gson = new Gson();
        String json = gson.toJson(accountHolder);

//        JSONObject json2 = new JSONObject(jsonidentification);
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
