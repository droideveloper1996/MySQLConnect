package connect.msql.com.mysqlconnect;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abhishek on 20/05/2017.
 */

public class MySharedPrefManager {

    private static final String SHARED_PREF_NAME="mySharedPref";
    private static  final  String KEY_ACCESS_TOKEN="mySharedToken";
    private static Context mContex;
    private static MySharedPrefManager mySharedPrefManager;
    private MySharedPrefManager(Context context)
    {
        this.mContex=context;
    }

    public static  MySharedPrefManager getInstance(Context context)
    {
        if(mySharedPrefManager ==null)
        {
            mySharedPrefManager=new MySharedPrefManager(context);
        }
        return mySharedPrefManager;
    }
    public boolean token(String token)
    {
        SharedPreferences sharedPreferences=mContex.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN,token);
        editor.commit();
        return true;
    }
    public String getToken()
    {
        SharedPreferences sharedPreferences=mContex.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        return sharedPreferences.getString(KEY_ACCESS_TOKEN,null);
    }
}
