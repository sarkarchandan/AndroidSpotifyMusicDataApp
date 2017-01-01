package de.uniba.androidspotifymusicdataapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import de.uniba.androidspotifymusicdataapp.R;

public class DetailActivity extends AppCompatActivity {

    private static final String BUNDLE_EXTRA = "BUNDLE_EXTRA";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try {
            Bundle extra = getIntent().getBundleExtra(BUNDLE_EXTRA);
            ((TextView)findViewById(R.id.textview_for_person_quote)).setText(extra.getString(EXTRA_QUOTE));

        }catch (NullPointerException nP){
            nP.getCause();
        }

    }
}
