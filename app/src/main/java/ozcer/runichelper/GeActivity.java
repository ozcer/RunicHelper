package ozcer.runichelper;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GeActivity extends AppCompatActivity {
    TextView tvItemDescription;
    TextView tvItemName;
    TextView tvItemPrice;
    EditText edtGeSearchBar;
    ImageView ivItemImage;
    ListView lvTrendList;
    ListAdapter myAdapter;
    String apiBase = "http://services.runescape.com/m=itemdb_oldschool/api/";
    String[][] trends = {{"Today's Change", "+1"}, {"1 Month Change", "+2"},
            {"3 Month Change", "+3"}, {"6 Month Change", "+4"}};


    String currentId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge);

        Button btnGeSearch = (Button) findViewById(R.id.ge_searchButton);
        tvItemDescription = (TextView) findViewById(R.id.ge_itemDescription);
        tvItemName  = (TextView) findViewById(R.id.ge_itemName);
        tvItemPrice  = (TextView) findViewById(R.id.ge_itemPrice);
        edtGeSearchBar = (EditText) findViewById(R.id.ge_searchBar);
        ivItemImage = (ImageView) findViewById(R.id.ge_itemImage);

        lvTrendList = (ListView) findViewById(R.id.ge_trend);
        myAdapter = new TrendListAdapter(this, trends);
        lvTrendList.setAdapter(myAdapter);


        btnGeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GeActivity.this, GeSearchActivity.class);
                startActivity(i);
            }
        });

        currentId = getIntent().getStringExtra("itemId");
        if(currentId != null) {
            new RetrieveItemTask().execute(currentId);
        }
    }

    public class RetrieveItemTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(
                        String.format(apiBase+"catalogue/detail.json?item=%s",params[0]));
                connection  = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject item = (new JSONObject(finalJson)).getJSONObject("item");

                return item;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject item) {
            super.onPostExecute(item);
            if(item != null) {
                String imageUrl = null;
                String price = null;
                String name = null;
                String desc = null;
                String todayTrend = null;
                String day30Trend = null;
                String day90Trend = null;
                String day180Trend = null;
                try {
                    imageUrl = item.getString("icon");
                    price = item.getJSONObject("current").getString("price");
                    name = item.getString("name");
                    desc = item.getString("description");
                    todayTrend = item.getJSONObject("today").getString("price");
                    day30Trend = item.getJSONObject("day30").getString("change");
                    day90Trend = item.getJSONObject("day90").getString("change");
                    day180Trend = item.getJSONObject("day180").getString("change");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Picasso.with(getApplicationContext()).load(imageUrl).into(ivItemImage);
                tvItemPrice.setText(price);
                tvItemName.setText(name);
                tvItemDescription.setText(desc);
                trends[0][1] = todayTrend;
                trends[1][1] = day30Trend;
                trends[2][1] = day90Trend;
                trends[3][1] = day180Trend;

                ((BaseAdapter) myAdapter).notifyDataSetChanged();
            } else {
                Toast.makeText(GeActivity.this, "no item found", Toast.LENGTH_SHORT).show();
            }

        }
    }
}


