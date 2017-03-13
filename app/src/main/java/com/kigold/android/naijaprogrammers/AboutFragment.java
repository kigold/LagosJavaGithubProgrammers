package com.kigold.android.naijaprogrammers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AboutFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_fragment);

        final Intent intent = new Intent(this, MainActivity.class);
        Button dismiss_btn = (Button) findViewById(R.id.close_about);

        dismiss_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
        //startActivity(intent);

    }

}
