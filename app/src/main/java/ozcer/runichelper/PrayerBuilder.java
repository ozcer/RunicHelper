package ozcer.runichelper;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ozcer on 2017-08-28.
 */

public class PrayerBuilder {

    ToggleButton button;
    Float drainRate;
    Drawable onImage;
    Drawable offImage;

    public PrayerBuilder button(int buttonId, View v) {
        button = (ToggleButton) v.findViewById(buttonId);
        return this;
    }

    public PrayerBuilder drainRate(Float drainRate) {
        this.drainRate = drainRate;
        return this;
    }

    public PrayerBuilder sprites(Drawable on, Drawable off) {
        onImage = on;
        offImage = off;
        return this;
    }

    public Prayer build(){
        if (button == null || drainRate == null ||
                onImage == null || offImage == null) {
            Log.e("PrayerBuilder", "insufficient params");
            return null;
        }
        return new Prayer(this);
    }
}
