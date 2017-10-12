package ozcer.runichelper;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

import static android.R.attr.id;

public class GeActivity extends AppCompatActivity {
    TextView tvGeDisplay;
    EditText edtGeSearchBar;
    ImageView ivItemImage;
    String imageUrl = null;
    String apiBase = "http://services.runescape.com/m=itemdb_oldschool/api/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge);

        Button btnGeSearch = (Button) findViewById(R.id.ge_searchButton);
        tvGeDisplay  = (TextView) findViewById(R.id.ge_itemDescription);
        edtGeSearchBar = (EditText) findViewById(R.id.ge_searchBar);
        ivItemImage = (ImageView) findViewById(R.id.ge_itemImage);

        btnGeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemId = edtGeSearchBar.getText().toString();
                new JSONTask().execute(itemId);
            }
        });




    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
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

                // grabs the first item
                JSONObject item = (new JSONObject(finalJson)).getJSONArray("items").getJSONObject(0);

                imageUrl = item.getString("icon");

                String name = item.getString("name");
                String price = item.getJSONObject("current").getString("price");

                return String.format("%s\n%s", name, price);


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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result != null) {
                tvGeDisplay.setText(result);
            } else {
                Toast.makeText(GeActivity.this, "no item found", Toast.LENGTH_SHORT).show();
            }
            if(imageUrl != null) {
                Picasso.with(GeActivity.this).load(imageUrl).into(ivItemImage);

            }

        }
    }



}


