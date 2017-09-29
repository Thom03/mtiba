package ke.co.carepay.mtiba.services;

import android.util.Log;

import ke.co.carepay.mtiba.utils.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by kingkong on 9/29/17.
 */

public class MtibaRequests {
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
        RequestBody formBody = new FormBody.Builder()
                .add(Constants.API_USERNAME_KEY,phoneNumber)
                .add(Constants.API_PASSWORD_KEY, "")
                .add(Constants.API_NEW_PASSWORD_KEY, password)
                .build();

//        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MTIBA_BASE_URL+Constants.MTIBA_CREATE_PASSWORD_URL).newBuilder();
//        urlBuilder.addQueryParameter(Constants.API_USERNAME_KEY,phoneNumber);
//        urlBuilder.addQueryParameter(Constants.API_PASSWORD_KEY, "");
//        urlBuilder.addQueryParameter(Constants.API_NEW_PASSWORD_KEY, password);

//        String url = urlBuilder.build().toString();
        String url = Constants.MTIBA_BASE_URL+Constants.MTIBA_CREATE_PASSWORD_URL;
        Log.d("url", url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("Authorization", Constants.JWT_TOKEN)
                .url(url)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
