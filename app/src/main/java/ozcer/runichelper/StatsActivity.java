package ozcer.runichelper;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class StatsActivity extends AppCompatActivity {

    String apiBase = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";
    Button btSearch;
    EditText edtSearch;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        btSearch = (Button) findViewById(R.id.stats_searchButton);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LookupPlayerTask().execute(edtSearch.getText().toString());
            }
        });
        edtSearch = (EditText) findViewById(R.id.stats_searchText);
        tvContent = (TextView) findViewById(R.id.stats_content);

    }

    public class LookupPlayerTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(
                        String.format(apiBase+ params[0]));
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


                return finalJson;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
                    tvContent.setText(result);
                }else {
                    Toast.makeText(StatsActivity.this, "uuuuh", Toast.LENGTH_SHORT).show();
                }
            }

        }
}
