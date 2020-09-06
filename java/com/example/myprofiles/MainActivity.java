package com.example.myprofiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private final String DIALOG_TAG = "Profile Dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        FloatingActionButton floatingActionButton = findViewById(R.id.add_profile);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialog dialog = new ProfileDialog(MainActivity.this);
                dialog.show(getSupportFragmentManager(), DIALOG_TAG);
            }
        });

        TextView showProfiles = findViewById(R.id.show_profiles);
        showProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileDisplay.class);
                startActivity(intent);
            }
        });
    }
}