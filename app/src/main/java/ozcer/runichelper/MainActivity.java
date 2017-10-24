package ozcer.runichelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton btnNMZ;
    ImageButton btnGE;
    Button btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNMZ = (ImageButton) findViewById(R.id.nmzButton);
        btnGE = (ImageButton) findViewById(R.id.geButton);
        btnStats = (Button) findViewById(R.id.statsButton);

        btnNMZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NmzActivity.class);
                startActivity(i);

            }
        });

        btnGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GeActivity.class);
                i.putExtra("itemId", "4151");
                startActivity(i);

            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, StatsActivity.class);
                startActivity(i);

            }
        });
    }
}
