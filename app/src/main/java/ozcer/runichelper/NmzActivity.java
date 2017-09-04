package ozcer.runichelper;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NmzActivity extends AppCompatActivity {


    static EditText edtPrayerLevel;
    static Integer prayerLevel;
    static EditText edtPrayerBonus;
    static Integer prayerBonus;

    static TextView tvDrainRate;
    static Float rawDrainRate;

    static EditText edtMaxTime;
    static Float maxTime;

    static TextView tvPrayerPotionAmount;
    static Integer prayerPotionAmount;

    static TextView tvOverloadAmount;
    static Integer overloadAmount;

    static TextView tvPrayerPotionRepotTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmz);
        tvPrayerPotionRepotTime = (TextView) findViewById(R.id.tv_prayerPotionRepotTime);

        rawDrainRate = 0f;
        prayerLevel = 1;
        prayerBonus = 0;
        maxTime = 0f;
        prayerPotionAmount = 0;
        overloadAmount = 0;

        edtPrayerLevel = (EditText) findViewById(R.id.edt_prayerLevel);
        edtPrayerLevel.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    prayerLevel = Integer.valueOf(s.toString());
                } catch (Exception e ) {
                    prayerLevel = Integer.valueOf("1");
                }
                updatePotions();
                updateRepotTime();

            }
        });

        edtPrayerLevel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // if error input replace it with 1
                try{
                    Integer.valueOf(edtPrayerLevel.getText().toString());
                } catch (Exception e ) {
                    edtPrayerLevel.setText("1");
                }
            }
        });

        edtPrayerBonus = (EditText) findViewById(R.id.edt_prayerBonus);
        edtPrayerBonus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    prayerBonus = Integer.valueOf(s.toString());
                } catch (Exception e ) {
                    prayerBonus = Integer.valueOf("0");
                }
                updatePotions();
                updateDrainRate(0f);

            }
        });
        edtPrayerBonus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // if error input replace it with 0
                try{
                    Integer.valueOf(edtPrayerBonus.getText().toString());
                } catch (Exception e ) {
                    edtPrayerBonus.setText("0");
                }
            }
        });

        tvOverloadAmount = (TextView) findViewById(R.id.tv_overloadAmount);
        tvPrayerPotionAmount = (TextView) findViewById(R.id.tv_prayerPotionAmount);
        updatePotions();

        tvDrainRate = (TextView) findViewById(R.id.drainRate);
        updateDrainRate(0f);

        edtMaxTime = (EditText) findViewById(R.id.edt_maxTime);
        edtMaxTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    maxTime = Float.valueOf(s.toString())*60f;
                } catch (Exception e ) {
                    maxTime = Float.valueOf("0");
                }
                updatePotions();

            }
        });
        edtMaxTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try{
                    Float.valueOf(edtMaxTime.getText().toString());
                } catch (Exception e ) {
                    edtMaxTime.setText("0");
                }

            }
        });





        View rootView = findViewById(android.R.id.content);
        // thick skin
        int thickSkinButtonId = R.id.btn_thickSkin;
        Float thickSkinDR = (1f/3f);
        Drawable thickSkinOn = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.thick_skin_on);
        Drawable thickSkinOff = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.thick_skin_off);

        Prayer thickSkin = new PrayerBuilder()
                .button(thickSkinButtonId, rootView)
                .drainRate(thickSkinDR)
                .sprites(thickSkinOn, thickSkinOff)
                .build();

        // burst of strength
        int bustOfStrengthButtonId = R.id.btn_burstOfStrength;
        Float burstOfStrengthDR = (1f/12f);
        Drawable burstOfStrengthOn = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.burst_of_strength_on);
        Drawable burstOfStrengthOff = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.burst_of_strength_off);

        Prayer burstOfStrength = new PrayerBuilder()
                .button(bustOfStrengthButtonId, rootView)
                .drainRate(burstOfStrengthDR)
                .sprites(burstOfStrengthOn, burstOfStrengthOff)
                .build();

    }

    public static String round(Float f, int places) {
        return String.format("%."+String.valueOf(places)+"f", f);
    }

    public static void updateDrainRate(Float delta) {
        rawDrainRate += delta;
        Float reducedDrainRate = rawDrainRate / (((float) prayerBonus * (1f/30f)) + 1);
        tvDrainRate.setText(round(reducedDrainRate, 3));
        updateRepotTime();
    }

    public static void updateRepotTime() {
        if(Float.valueOf(tvDrainRate.getText().toString()) > 0) {
            Integer pPotRestoreRate = Math.min(7 + prayerLevel / 4, prayerLevel);
            Float durationPerPrayerPotion = pPotRestoreRate / Float.valueOf(tvDrainRate.getText().toString());
            tvPrayerPotionRepotTime.setText(formatTime(durationPerPrayerPotion));
        } else {
            tvPrayerPotionRepotTime.setText("N/A");
        }

    }

    /*public static void updateMaxTime() {
        maxTime = ((float)prayerLevel/rawDrainRate)*(1 + ((float)prayerBonus/30));

        if(maxTime.toString() != "Infinity") {
            tvMaxTime.setText("max time: " + formatTime(maxTime));
            updatePotions();
        } else {
            tvMaxTime.setText("max time: "+ "select prayer");
        };

    }*/

    public static void updatePotions() {
        if(maxTime == 0f) {
            prayerPotionAmount = 0;
            overloadAmount = 0;

        } else {
            float drainSlowdown = ((float) prayerBonus * (1f/30f)) + 1;
            Integer totalPrayerRequired = (int) (Math.ceil(maxTime * (rawDrainRate / drainSlowdown)));
            Integer pPotRestoreRate = Math.min(7 + prayerLevel / 4, prayerLevel);
            prayerPotionAmount = (int) Math.ceil((float) (totalPrayerRequired - prayerLevel) / (float) pPotRestoreRate);


            overloadAmount = Integer.valueOf(round((float) Math.ceil(maxTime / 300f), 0));
        }
        if(prayerPotionAmount < 0) {
            prayerPotionAmount = 0;
        }
        tvPrayerPotionAmount.setText(prayerPotionAmount.toString());
        tvOverloadAmount.setText(overloadAmount.toString());

    }

    public static String formatTime(Float s) {
        Integer rS = Integer.valueOf(round(s, 0));
        String result = "";
        if (rS - rS%3600 != 0) {
            result += String.valueOf((rS - rS%3600)/3600) + "hr ";
            rS = rS%3600;
        }
        if (rS - rS%60 != 0) {
            result += String.valueOf((rS - rS%60)/60) + "min ";
            rS = rS%60;
        }
        result += rS.toString() + "s";

        return result;
    }





}
