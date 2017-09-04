package ozcer.runichelper;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Created by ozcer on 2017-08-28.
 */

public class Prayer {
    ToggleButton button;
    Float drainRate;
    Drawable onImage;
    Drawable offImage;

    public Prayer (PrayerBuilder builder) {
        button = builder.button;
        drainRate = builder.drainRate;
        onImage = builder.onImage;
        offImage = builder.offImage;


        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                button.requestFocusFromTouch();
                if(isChecked) {
                    NmzActivity.updateDrainRate(drainRate);
                    //NmzActivity.updateMaxTime();
                    NmzActivity.updatePotions();
                    buttonView.setBackground(onImage);
                } else {
                    NmzActivity.updateDrainRate(-drainRate);
                    //NmzActivity.updateMaxTime();
                    NmzActivity.updatePotions();
                    buttonView.setBackground(offImage);
                }
            }
        });
    }


}
