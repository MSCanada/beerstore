package muhammadsuhail.login;

import android.app.Activity;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;


public class register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
public void register_1(View view)
{
    EditText username=(EditText)findViewById(R.id.username_register);
    EditText password=(EditText)findViewById(R.id.password_register);
    String username_register=username.getText().toString();
    String password_register=password.getText().toString();
    String username1= URLEncoder.encode(username_register);
    String password1=URLEncoder.encode(password_register);
    new RequestTask().execute("http://msuhail.hostingsiteforfree.com/registration.php?username="+username1+"&password="+password1);

}
    class RequestTask extends AsyncTask<String, String, String> {

        String responseString = "";

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
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            // ArrayList<Recipe> result_json=new ArrayList<Recipe>();

            TextView result1 = (TextView) findViewById(R.id.result_register);
            String[] result12 = result.split(":end");
            Log.i("result123", result12[0]);
            result1.setText(result12[0]);

        }
    }
}
