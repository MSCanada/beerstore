package muhammadsuhail.login;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.List;


public class MapsActivity extends FragmentActivity {
    List<Address> geocodeMatches = null;
    String input1="";
WebView source1;
    String input="12";
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Double latitude;
    Double longitude;
    Wines wines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle extras=getIntent().getExtras();
        if(extras==null){return;}
        wines= (Wines) extras.getSerializable("item_selected");
        source1=(WebView)findViewById(R.id.webview);
        source1.setWebViewClient(new WebViewClient());
        String url=wines.get_url();
try {
    if (url.charAt(0) == 'w') {
        url = "http://" + url;
    }
} catch (Exception e){}

        source1.loadUrl(url);


        JSONObject obj=new JSONObject();
        try {
            obj.accumulate("company_name",wines.get_company_name());
            obj.accumulate("country",wines.get_country());
            obj.accumulate("address",wines.get_address());
            obj.accumulate("city",wines.get_city());
            obj.accumulate("state",wines.get_state());
            obj.accumulate("postal",wines.get_postal());
            obj.accumulate("phone",wines.get_phone());
            obj.accumulate("url",wines.get_url());
            obj.accumulate("notes",wines.get_notes());
            obj.accumulate("status",wines.get_status_());
            obj.accumulate("colour",wines.get_colour());
            obj.accumulate("username",wines.get_username());
             input=obj.toString();


            Log.i("result123", "http://www.msuhail.hostingsiteforfree.com/test.php?subject=" + input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//Log.i("geoaur",wines.get_address()+","+wines.get_city()+","+wines.get_country());
        try {
            geocodeMatches =
                    new Geocoder(this).getFromLocationName(
                           // wines.get_address()+","+wines.get_city()+","+wines.get_country(), 1);
            wines.get_address()+","+wines.get_country(), 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if (!geocodeMatches.isEmpty())
        {
            latitude = geocodeMatches.get(0).getLatitude();
            longitude = geocodeMatches.get(0).getLongitude();

        }

String name="suhail";
        setUpMapIfNeeded();

         input1= URLEncoder.encode(input);

        Log.i("query123","http://www.msuhail.hostingsiteforfree.com/test.php?subject="+input);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(wines.get_company_name().toUpperCase()).snippet(wines.get_colour().toUpperCase()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
    }
    class RequestTask extends AsyncTask<String, String, String> {
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
            Log.i("check321",result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.pushdata) {

            new RequestTask().execute("http://www.msuhail.hostingsiteforfree.com/test.php?subject="+input1);
            Toast.makeText(getApplicationContext(),"Favourite Added",Toast.LENGTH_LONG).show();       }

        if (id == R.id.call) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+wines.get_phone()));
            startActivity(intent);
                  }
        return super.onOptionsItemSelected(item);
    }





    }



