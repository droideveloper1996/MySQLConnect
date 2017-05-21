package connect.msql.com.mysqlconnect;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Abhishek on 20/05/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService

{

    public static final String TAG = "MyFirebaseInstanceId";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendRegistrationToServer(refreshedToken);
        storeToken(refreshedToken);
    }

    public void storeToken(String token) {
        MySharedPrefManager.getInstance(getApplicationContext()).token(token);
    }
}

