package ozcer.runichelper;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
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
        Float thickSkinDR = (1f/12f);
        Drawable thickSkinOn = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.thick_skin_on);
        Drawable thickSkinOff = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.thick_skin_off);

        Prayer thickSkin = new PrayerBuilder()
                .button(thickSkinButtonId, rootView)
                .drainRate(thickSkinDR)
                .onSprite(thickSkinOn)
                .offSprite(thickSkinOff)
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
                .onSprite(burstOfStrengthOn)
                .offSprite(burstOfStrengthOff)
                .build();


        // clarity of thought
        int clarityOfThoughtButtonId = R.id.btn_clarityOfThought;
        Float clarityOfThoughtDR = (1f/12f);
        Drawable clarityOfThoughtOn = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.clarity_of_thought_on);
        Drawable clarityOfThoughtOff = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.clarity_of_thought_off);

        Prayer clarityOfThought = new PrayerBuilder()
                .button(clarityOfThoughtButtonId, rootView)
                .drainRate(clarityOfThoughtDR)
                .onSprite(clarityOfThoughtOn)
                .offSprite(clarityOfThoughtOff)
                .build();

        // sharp eye
        int sharpEyeButtonId = R.id.btn_sharpEye;
        Float sharpEyeDR = (1f/12f);
        Drawable sharpEyeOn = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.sharp_eye_on);
        Drawable sharpEyeOff = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.sharp_eye_off);

        Prayer sharpEye = new PrayerBuilder()
                .button(sharpEyeButtonId, rootView)
                .drainRate(sharpEyeDR)
                .onSprite(sharpEyeOn)
                .offSprite(sharpEyeOff)
                .build();

        // mystic will
        Prayer mysticWill = new PrayerBuilder()
                .button(R.id.btn_mysticWill, rootView)
                .drainRate(1f/12f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.mystic_will_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.mystic_will_off))
                .build();

        // rock skin
        Prayer rockSkin = new PrayerBuilder()
                .button(R.id.btn_rockSkin, rootView)
                .drainRate(1f/6f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.rock_skin_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.rock_skin_off))
                .build();

        // superhuman strength
        new PrayerBuilder()
            .button(R.id.btn_superHumanStrength, rootView)
            .drainRate(1f/6f)
            .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.superhuman_strength_on))
            .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.superhuman_strength_off))
            .build();

        // improved reflexes
        new PrayerBuilder()
                .button(R.id.btn_improvedReflexes, rootView)
                .drainRate(1f/6f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.improved_reflexes_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.improved_reflexes_off))
                .build();

        // rapid restore
        new PrayerBuilder()
                .button(R.id.btn_rapidRestore, rootView)
                .drainRate(1f/36f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.rapid_restore_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.rapid_restore_off))
                .build();

        // rapid heal
        new PrayerBuilder()
                .button(R.id.btn_rapidHeal, rootView)
                .drainRate(1f/18f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.rapid_heal_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.rapid_heal_off))
                .build();

        // protect item
        new PrayerBuilder()
                .button(R.id.btn_protectItem, rootView)
                .drainRate(1f/18f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.protect_item_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.protect_item_off))
                .build();

        // hawk eye
        new PrayerBuilder()
                .button(R.id.btn_hawkEye, rootView)
                .drainRate(1f/6f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.hawk_eye_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.hawk_eye_off))
                .build();

        // mystic lore
        new PrayerBuilder()
                .button(R.id.btn_mysticLore, rootView)
                .drainRate(1f/6f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.mystic_lore_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.mystic_lore_off))
                .build();

        // steel skin
        new PrayerBuilder()
                .button(R.id.btn_steelSkin, rootView)
                .drainRate(1f/3f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.steel_skin_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.steel_skin_off))
                .build();

        // ultimate strength
        new PrayerBuilder()
                .button(R.id.btn_ultimateStrength, rootView)
                .drainRate(1f/3f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ultimate_strength_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ultimate_strength_off))
                .build();

        // incredible reflexes
        new PrayerBuilder()
                .button(R.id.btn_incredibleReflexes, rootView)
                .drainRate(1f/3f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.incredible_reflexes_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.incredible_reflexes_off))
                .build();

        // protect from magic
        new PrayerBuilder()
                .button(R.id.btn_protectFromMagic, rootView)
                .drainRate(1f/3f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.protect_from_magic_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.protect_from_magic_off))
                .build();

        // protect from missiles
        new PrayerBuilder()
                .button(R.id.btn_protectFromMissiles, rootView)
                .drainRate(1f/3f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.protect_from_missiles_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.protect_from_missiles_off))
                .build();

        // protect from melee
        new PrayerBuilder()
                .button(R.id.btn_protectFromMelee, rootView)
                .drainRate(1f/3f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.protect_from_melee_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.protect_from_melee_off))
                .build();

        // eagle eye
        new PrayerBuilder()
                .button(R.id.btn_eagleEye, rootView)
                .drainRate(1f/3f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.eagle_eye_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.eagle_eye_off))
                .build();

        // mystic mmight
        new PrayerBuilder()
                .button(R.id.btn_mysticMight, rootView)
                .drainRate(1f/3f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.mystic_might_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.mystic_might_off))
                .build();

        // retribution
        new PrayerBuilder()
                .button(R.id.btn_retribution, rootView)
                .drainRate(1f/12f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.retribution_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.retribution_off))
                .build();

        // redemption
        new PrayerBuilder()
                .button(R.id.btn_redemption, rootView)
                .drainRate(1f/6f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.redemption_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.redemption_off))
                .build();

        // smite
        new PrayerBuilder()
                .button(R.id.btn_smite, rootView)
                .drainRate(1f/2f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.smite_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.smite_off))
                .build();

        // preserve
        new PrayerBuilder()
                .button(R.id.btn_preserve, rootView)
                .drainRate(1f/18f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.preserve_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.preserve_off))
                .build();

        // chivalry
        new PrayerBuilder()
                .button(R.id.btn_chivalry, rootView)
                .drainRate(1f/1.5f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.chivalry_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.chivalry_off))
                .build();

        // piety
        new PrayerBuilder()
                .button(R.id.btn_piety, rootView)
                .drainRate(1f/1.5f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.piety_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.piety_off))
                .build();

        // rigour
        new PrayerBuilder()
                .button(R.id.btn_rigour, rootView)
                .drainRate(1f/1.5f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.rigour_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.rigour_off))
                .build();

        // augury
        new PrayerBuilder()
                .button(R.id.btn_augury, rootView)
                .drainRate(1f/1.5f)
                .onSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.augury_on))
                .offSprite(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.augury_off))
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
