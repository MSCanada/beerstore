package muhammadsuhail.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;


public class MainActivity extends Activity {
EditText username;
    EditText password;
TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);


    }
    public void register(View view){
Intent i=new Intent(getApplicationContext(),register.class);
        startActivity(i);



    }
public void myfunc(View view)
{
    String username1=URLEncoder.encode(username.getText().toString());
    String password1=URLEncoder.encode(password.getText().toString());
    new RequestTask().execute("http://msuhail.hostingsiteforfree.com/login.php?username="+username1+"&password="+password1);
}
    class RequestTask extends AsyncTask<String, String, String> {

String responseString="";

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;



            try {

                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();

                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }


            String[] result12=responseString.split(":end");
            Log.i("result123",result12[0]);



            return result12[0];
        }

        @Override
        protected void onPostExecute(String result) {
            String name="";
            // ArrayList<Recipe> result_json=new ArrayList<Recipe>();
            try {
                JSONObject obj=new JSONObject(result);
                name=obj.optString("value");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TextView result1=(TextView)findViewById(R.id.result);


            result1.setText(name);


    if(name.equals("correct")){
        Log.i("reason","reached");
        Intent i = new Intent(getApplicationContext(), MainActivity1.class);
        i.putExtra("user", username.getText().toString());
        startActivity(i);
    }
            else {
        Log.i("result321",result);
    }

        }

    }







}
