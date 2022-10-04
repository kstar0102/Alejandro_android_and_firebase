package com.usa.tripjungle;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    ImageView backBtn, my_pager;
    TextView title, description;
    ProgressDialog progress;
    int id = 0;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_detail);
        getSupportActionBar().hide();
        loadElement();

        extras = getIntent().getExtras();

        String image = extras.getString("image");
        StrictMode.ThreadPolicy gfgPolicy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);
        Picasso.get().load(extras.getString("image")).into(my_pager);
        this.id = Integer.parseInt(extras.getString("id"));
        title.setText(extras.getString("title"));
        description.setText(extras.getString("description"));
    }

    private void loadElement(){
        backBtn = findViewById(R.id.detail_back);
        title = findViewById(R.id.detail_title);
        description = findViewById(R.id.detail_description);
        my_pager = findViewById(R.id.my_pager);
        description.setMovementMethod(new ScrollingMovementMethod());

        progress = new ProgressDialog(DetailActivity.this);
        progress.setIndeterminate(true);
        backBtn.setOnClickListener(v -> {
            Intent i = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(i);
        });
    }
}
