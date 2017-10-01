package ozcer.runichelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnNMZ;
    Button btnGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNMZ = (Button) findViewById(R.id.nmzButton);
        btnGE = (Button) findViewById(R.id.geButton);

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
                startActivity(i);

            }
        });
    }
}
