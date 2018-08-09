package khushboo.sanket.assignment7;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class TransitInfo extends AppCompatActivity {
    private TextView txtDirections;
    //
    EditText startPoint;
    EditText endPoint;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit_info);
        startPoint = (EditText) findViewById(R.id.txtStartPoint);
        endPoint = (EditText) findViewById(R.id.txtEndPoint);
        // set a default start and destinaton
        startPoint.setText(("941 Progress Avenue"));
        endPoint.setText(("CN Tower"));

    }
    //
    public void getInfo(View view) {
        //keep in mind your AVD should support Google API
        //
        String strUrl = "http://maps.googleapis.com/maps/api/directions/json?";
        //
        List<Address> geoCodes = null;
        //get the start point from the edit text startPoint
        String startName = startPoint.getText().toString();
        try
        {
            //Returns an array of Addresses that are known to describe the named location
            //do
            //get geocodes of start point
            geoCodes = new Geocoder(this, Locale.getDefault()).getFromLocationName(startName, 5);
            //while (geoCodes.size()<=0);
            //extract the latitude and longitude from the first address
            //String origineLatitude = String.valueOf(geoCodes.get(0).getLatitude());
            //String origineLongitude = String.valueOf(geoCodes.get(0).getLongitude());
            //encode the latitude and longitude
            //origineLatitude = URLEncoder.encode(origineLatitude, "UTF-8"); // 941 Progress avenue
            //origineLongitude = URLEncoder.encode(origineLongitude, "UTF-8");
            //while (geoCodes.get(0)==null)

            String origineLatitude = URLEncoder.encode("43.7850417", "UTF-8"); // 941 Progress avenue
            String origineLongitude = URLEncoder.encode("-79.2270519", "UTF-8");
            //get the end point from edit text endPoint
            String endName = endPoint.getText().toString();
            //get the geocodes of destinatopn point
            //geoCodes = new Geocoder(this, Locale.getDefault()).getFromLocationName(endName, 5);
            //String destinationLatitude = String.valueOf(geoCodes.get(0).getLatitude());
            //String destinationLongitude = String.valueOf(geoCodes.get(0).getLongitude());
            //
            //System.out.println("destination: "+destinationLatitude);
            //System.out.println("destination: "+destinationLongitude);
            //
            //
            String destinationLatitude = URLEncoder.encode("43.6563", "UTF-8"); // Eaton Tower
            String destinationLongitude = URLEncoder.encode("-79.3808", "UTF-8");
            //create the complete url string
            String origine = "origin=" + origineLatitude + "," + origineLongitude;
            String destination = "destination=" + destinationLatitude + "," + destinationLongitude;
            //
            String sensorValue = URLEncoder.encode("false", "UTF-8");
            String sensor = "sensor=" + sensorValue;
            //arguments for Dec 22, 2017, 4:30pm.
            /*year - the year minus 1900.
            month - the month between 0-11.
            date - the day of the month between 1-31.
            hrs - the hours between 0-23.
            min - the minutes between 0-59.
            sec - the seconds between 0-59.*/
            Date date = new Date(117, 11, 22, 16, 30, 0);
            long l = date.getTime() / 1000;
            System.out.println("Time: " +String.valueOf(l));
            String departureTimeValue = URLEncoder.encode(String.valueOf(l), "UTF-8");
            System.out.println(departureTimeValue);
            String departureTime = "departure_time=" + departureTimeValue;
            //
            String modeValue = URLEncoder.encode("transit", "UTF-8");
            String mode = "mode=" + modeValue;

            //create the url
            //  strUrl = strUrl +origine+"&"+destination+"&"+sensor+"&"+departureTime+"&"+mode;
            strUrl = strUrl +origine+"&"+destination+"&"+sensor+"&"+mode;
            System.out.println(strUrl);
            Log.d("url",strUrl);
            new ReadTransitJSONFeedTask().execute(strUrl);
        }
        catch(UnsupportedEncodingException e){
            Log.d("encodingerror",e.getMessage());
        }
        catch(IOException e)
        {
            Log.d("geocode error",e.getMessage());

        }
    }

    //
    private class ReadTransitJSONFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            Log.d("url",urls[0]);
            return readJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result) {
            String directions=""; //to hold the result after parsing
            try {
                String str = result; //readJSONFeed(result);
                Log.d("link",str);
                // build a JSON object
                JSONObject obj = new JSONObject(str);
                if (obj.getString("status").equals("OK"))
                    System.out.println("OK");
                JSONArray routes=obj.getJSONArray("routes");
                JSONObject data,data1;
                JSONObject bounds;
                //System.out.println(routes.length());
                //
                for (int i=0;i<routes.length();i++)
                {
                    data = routes.getJSONObject(i);
                    Iterator keys = data.keys();
                    while(keys.hasNext()){
                        System.out.println(keys.next());
                    }
                    //System.out.println(data);
                    bounds= data.getJSONObject("bounds");
                    System.out.println(bounds);
                    Iterator keys1 = bounds.keys();
                    while(keys1.hasNext()){
                        System.out.println(keys1.next());
                    }

                    JSONArray legs= data.getJSONArray("legs");
                    //System.out.println(legs.length());
                    for (i=0;i<legs.length();i++)
                    {
                        data = legs.getJSONObject(i);
                        //System.out.println(legs);
                        Iterator keys2 = data.keys();
                        while(keys2.hasNext()){
                            System.out.println(keys2.next());

                        }
                        JSONArray steps= data.getJSONArray("steps");
                        for (int j=0;j<steps.length();j++)
                        {
                            data1 = steps.getJSONObject(j);
                            //System.out.println(steps);

                            Iterator keys3 = data1.keys();
                            while(keys3.hasNext()){
                                String key = (String)keys3.next();
                                if(key.equals("html_instructions")) {
                                    directions+="\n"+data1.getString(key);
                                    System.out.println(data1.getString(key));
                                }

                            }
                        }

                    }


                }
                // display the results in textview txtDirections
                Log.d("Directions", directions);
                txtDirections = (TextView)findViewById(R.id.txtDirections);
                txtDirections.setText(directions);
                txtDirections.setMovementMethod(ScrollingMovementMethod.getInstance());

            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    //
    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        Log.d("url",URL);
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }




}
