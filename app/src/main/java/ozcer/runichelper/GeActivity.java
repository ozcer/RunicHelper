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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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

public class GeActivity extends AppCompatActivity {
    TextView tvItemDescription;
    TextView tvItemName;
    TextView tvItemPrice;
    EditText edtGeSearchBar;
    ImageView ivItemImage;
    ListView lvTrendList;
    String imageUrl = null;
    String apiBase = "http://services.runescape.com/m=itemdb_oldschool/api/";
    String[][] trends = {{"Today's Change", "+1"}, {"1 Month Change", "+2"},
            {"3 Month Change", "+3"}, {"6 Month Change", "+4"}};


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
        ListAdapter myAdapter = new TrendListAdapter(this, trends);
        lvTrendList.setAdapter(myAdapter);


        btnGeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GeActivity.this, GeSearchActivity.class);
                startActivity(i);
            }
        });




    }
}


