package muhammadsuhail.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity1 extends Activity {
    ListView custom_list;
    Spinner spinner1;
    Spinner spinner2;
    HttpResponse response=null;
    String responseString="yes";
    String country=null;
    String drink=null;
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity1);
        Bundle extras=getIntent().getExtras();
        if(extras==null){return;}
        username= extras.getString("user");
        Log.i("mainactivity1", username);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drink=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new RequestTask1().execute("http://www.msuhail.hostingsiteforfree.com/check.php?user_name="+URLEncoder.encode(username));
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addtodb) {

            new RequestTask1().execute("http://www.msuhail.hostingsiteforfree.com/check.php?user_name="+URLEncoder.encode(username));



        }
        return super.onOptionsItemSelected(item);
    }

public void favourites(View view){
    new RequestTask1().execute("http://www.msuhail.hostingsiteforfree.com/check.php?user_name="+URLEncoder.encode(username));
}
    public  void receivehttp(View view) {
        String country1= URLEncoder.encode(country);
        new RequestTask().execute("https://msuhailwebapi.herokuapp.com/api/V1/"+drink+"/json/"+country1);

    }

    class RequestTask extends AsyncTask<String, String, String> {
        ProgressDialog progress=new ProgressDialog(getApplicationContext());
        @Override
        protected  void onPreExecute(){

            progress =
                    ProgressDialog.show(MainActivity1.this, "", "Please Wait...");
//            progress.show();
            super.onPreExecute();
        }

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

            Wines wines;
            JSONObject jsonObject;
            ArrayList<Wines> result1=new ArrayList<Wines>();

            try {
                JSONArray jsonArray=new JSONArray(result);
                int length=jsonArray.length();

                for(int i=0;i<length;i++) {

                    wines = new Wines();
                    jsonObject = new JSONObject();
                    jsonObject = jsonArray.optJSONObject(i);
                    wines.set_country(jsonObject.optString("_country"));
                    wines.set_colour(jsonObject.optString("_colour"));
                    wines.set_company_name(jsonObject.optString("_company_name"));
                    wines.set_phone(jsonObject.optString("_phone"));
                    wines.set_postal(jsonObject.optString("_postal"));
                    wines.set_state(jsonObject.optString("_state"));
                    wines.set_status_(jsonObject.optString("_status"));
                    wines.set_address(jsonObject.optString("_address"));
                    wines.set_city(jsonObject.optString("_city"));
                    //Log.i("city", jsonObject.optString("_city"));
                    wines.set_url(jsonObject.optString("_url"));
                    result1.add(wines);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progress.dismiss();
            Log.i("data123456",result1.get(0).get_city());
            custom_list=(ListView)findViewById(R.id.custom_list);
            custom_list.setAdapter(new CustomListAdapter(getApplicationContext(), result1));
            custom_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object ob=custom_list.getItemAtPosition(position);
                    Wines item=(Wines)ob;
                    item.set_username(username);
                    Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                    i.putExtra("item_selected", item);
                    startActivity(i);
                }
            });
        }
    }
    class RequestTask1 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString="r";


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
            Log.i("receiving_result",result);
            ArrayList<Wines> result321=new ArrayList<Wines>();
            try {
                String[] result1=result.split(":end");
                Log.i("re88", result1[0]);
                JSONObject json=new JSONObject(result1[0]);
                JSONArray res=json.optJSONArray("Android");
                Log.i("json",json.toString());
                int length=res.length();
                Wines data=new Wines();
                JSONObject jsonObject1;
                for(int i=0;i<length;i++)
                {
                    data=new Wines();
                    jsonObject1=res.getJSONObject(i);
                    String company_name=jsonObject1.optString("company_name");
                    String colour=jsonObject1.optString("colour");
                    String status=jsonObject1.optString("status");
                    String address=jsonObject1.optString("address");
                    String city=jsonObject1.optString("city");
                    String state=jsonObject1.optString("state");
                    String postal=jsonObject1.optString("postal");
                    String country=jsonObject1.optString("country");
                    String phone=jsonObject1.optString("phone");
                    String url=jsonObject1.optString("url");
                    String notes=jsonObject1.optString("notes");
                    Log.i("company321",company_name);
                    data.set_company_name(company_name);
                    data.set_colour(colour);
                    data.set_url(url);
                    data.set_status_(status);
                    data.set_address(address);
                    data.set_city(city);
                    data.set_state(state);
                    data.set_postal(postal);
                    data.set_country(country);
                    data.set_phone(phone);
                    data.set_notes(notes);
                    result321.add(data);
                }
              //  Log.i("xara321",result321.get(0).get_company_name());

                Integer num=length;
                Log.i("length_json",num.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            custom_list=(ListView)findViewById(R.id.custom_list);
            custom_list.setAdapter(new CustomListAdapter(getApplicationContext(), result321));
            custom_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object ob=custom_list.getItemAtPosition(position);
                    Wines item=(Wines)ob;
                    item.set_username(username);
                    Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                    i.putExtra("item_selected", item);
                    startActivity(i);
                }
            });

        }
    }
}

