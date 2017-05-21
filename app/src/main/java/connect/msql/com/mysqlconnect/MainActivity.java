package connect.msql.com.mysqlconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText row_id;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.register);
        final EditText editText = (EditText) findViewById(R.id.email);
        TextView textView = (TextView) findViewById(R.id.token);
        textView.setText(MySharedPrefManager.getInstance(getApplicationContext()).getToken());
        result = (TextView) findViewById(R.id.result);
        final Button getDataButton = (Button) findViewById(R.id.getData);
        row_id = (EditText) findViewById(R.id.row_id);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToken(editText.getText().toString().trim(), MySharedPrefManager.getInstance(getApplicationContext()).getToken());

            }
        });
        getDataButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata(row_id.getText().toString().trim());
            }
        });
    }

    private void sendToken(final String string, final String token) {
        if (string == null) {
            Toast.makeText(getApplicationContext(), "Missing Params", Toast.LENGTH_SHORT).show();

        } else if (token != null) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.109/fcmtoken.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject j = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), j.optString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", string);
                    params.put("token", token);
                    return params;
                }
            };
            RequestQueue re = Volley.newRequestQueue(this);
            re.add(stringRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Token Not generated", Toast.LENGTH_SHORT).show();
        }

    }

    public void getdata(String id) {
        if (id != null) {
            String url = "http://192.168.0.109/getData.php?id=" + id;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT)
                            .show();
                    JSONObject res = null;
                    try {
                        res = new JSONObject(response);
                        String email = res.optString("email");
                        String tok = res.optString("token");
                        result.setText("email : " + email + '\n' + "token : " + tok);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}


