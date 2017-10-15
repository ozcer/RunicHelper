package ozcer.runichelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Arrays;

public class GeSearchActivity extends AppCompatActivity {

    EditText edtSearchBar;
    ListView lvSearchResult;
    String apiBase = "http://services.runescape.com/m=itemdb_oldschool/api/";
    ArrayList<String[]> searchResult = new ArrayList<>();
    ListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_search);

        edtSearchBar = (EditText) findViewById(R.id.ge_searchBar);
        edtSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    new SearchItemTask().execute(v.getText().toString());
                }
                return false;
            }
        });


        lvSearchResult = (ListView) findViewById(R.id.ge_searchResult);
        myAdapter = new GeSearchResultAdapter(this, searchResult);
        lvSearchResult.setAdapter(myAdapter);
        lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(GeSearchActivity.this, GeActivity.class);
                i.putExtra("itemID", searchResult.get(position)[3]);
                Toast.makeText(GeSearchActivity.this, searchResult.get(position)[3], Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

    }

    public class SearchItemTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(
                        String.format(apiBase+"catalogue/items.json?category=1&alpha=%s&page=1",params[0]));
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

                JSONArray jsonArray = (new JSONObject(finalJson)).getJSONArray("items");

                return jsonArray;

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
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            if(jsonArray != null) {

                searchResult.clear();
                // {image_url, item_name, item_price}
                for (int i = 0; i < jsonArray.length(); i++) {
                    String[] thisItem = new String[4];

                    try {
                        thisItem[0] = jsonArray.getJSONObject(i).getString("icon");
                        thisItem[1] = jsonArray.getJSONObject(i).getString("name");
                        thisItem[2] = jsonArray.getJSONObject(i).getJSONObject("current").getString("price");
                        thisItem[3] = Integer.toString(jsonArray.getJSONObject(i).getInt("id"));
                        searchResult.add(thisItem);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                    ((BaseAdapter) myAdapter).notifyDataSetChanged();
            } else {
                Toast.makeText(GeSearchActivity.this, "no item found", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
