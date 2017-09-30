package ke.co.carepay.mtiba.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import ke.co.carepay.mtiba.R;
import ke.co.carepay.mtiba.ui.PhoneInputActivity;

/**
 * Created by kingkong on 9/30/17.
 */

public class NavigationItemSelector  {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    public static boolean getSelectedMenus(MenuItem menuItem, DrawerLayout drawer, Context context){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_email_statements) {

        } else if (id == R.id.nav_moneyadd) {

        } else if (id == R.id.nav_loc_clinic) {

        } else if (id == R.id.nav_dependants) {

        } else if (id == R.id.nav_benefits) {

        }else if(id == R.id.nav_settings){

        }else if(id == R.id.nav_love){

        }else if(id == R.id.nav_logout){
            mEditor.putString(Constants.USER_TOKEN, null);
            mEditor.commit();

            Intent intent = new Intent(context, PhoneInputActivity.class);
            context.startActivity(intent);
//            ((Activity)(context)).finish();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
