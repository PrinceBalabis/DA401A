package com.princetronics.peepy;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Prince on 11/11/2014.
 */
public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Update fonts
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/font200.ttf");

        TextView myTextView = (TextView) findViewById(R.id.tv_appName);
        myTextView.setTypeface(typeFace);

        TextView tvAbout = (TextView) findViewById(R.id.tvAbout);
        tvAbout.setTypeface(typeFace);

        TextView tvVersionNumber = (TextView) findViewById(R.id.tvVersionNumber);
        tvVersionNumber.setTypeface(typeFace);

        // Display version number
        String versionName = BuildConfig.VERSION_NAME;
        tvVersionNumber.setText(tvVersionNumber.getText() + " " + versionName);
    }
}
