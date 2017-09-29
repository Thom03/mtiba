package ke.co.carepay.mtiba.services;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ke.co.carepay.mtiba.utils.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by kingkong on 9/29/17.
 */

public class MtibaRequests {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
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
    public void createNewPassword(String phoneNumber, String password, Callback callback){
        JSONObject json = new JSONObject();
        try{
            json.put(Constants.API_USERNAME_KEY,phoneNumber);
            json.put(Constants.API_PASSWORD_KEY, "cc");
            json.put(Constants.API_NEW_PASSWORD_KEY, password);
        }catch (JSONException e){

        }

        Log.d("json object", json.toString());
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MTIBA_BASE_URL+Constants.MTIBA_CREATE_PASSWORD_URL).newBuilder();
//        urlBuilder.addQueryParameter(Constants.API_USERNAME_KEY,phoneNumber);
//        urlBuilder.addQueryParameter(Constants.API_PASSWORD_KEY, "");
//        urlBuilder.addQueryParameter(Constants.API_NEW_PASSWORD_KEY, password);

//        String url = urlBuilder.build().toString();
        String url = Constants.MTIBA_BASE_URL+Constants.MTIBA_CREATE_PASSWORD_URL;
        Log.d("url", url);
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
}
