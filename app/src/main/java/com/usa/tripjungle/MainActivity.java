package com.usa.tripjungle;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usa.tripjungle.adapter.MainAdapter;
import com.usa.tripjungle.model.MainModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    de.hdodenhof.circleimageview.CircleImageView profileImg;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    EditText searchEdit;

    private List<MainModel> mainList = new ArrayList<>();
    private static List<MainModel> allMainList = new ArrayList<>();

    private MainAdapter mainAdapter;

    private ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadElement();

    }

    private void getSearchData(String key) {
        progressBar.setVisibility(View.VISIBLE);
        mainList.clear();
        for (int i = 0; i < allMainList.size(); i ++) {
            if (allMainList.get(i).getTitle().contains(key)) {
                mainList.add(allMainList.get(i));
            }
        }
        mainAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    public void getDataFromServer(){
        mainList.clear();
        allMainList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("0");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap value = (HashMap) dataSnapshot.getValue();
                Iterator myVeryOwnIterator = value.keySet().iterator();
                while(myVeryOwnIterator.hasNext()) {
                    String key=(String)myVeryOwnIterator.next();
                    arrayList=(ArrayList)value.get(key);
                }
                Log.e(TAG, "Value is: " + value);

              for (int i = 0; i < arrayList.size(); ++i) {
                  HashMap hashMap = (HashMap) arrayList.get(i);
                  JSONObject jsonObject = new JSONObject(hashMap);
                  try {
                      preparemaindData(
                                  "0",
                                  jsonObject.getString("title"),
                                  jsonObject.getString("description"),
                                  jsonObject.getString("image")
                      );
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
              }
              progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void preparemaindData(String id, String title, String description, String image) {
        MainModel movie = new MainModel(id, title, description, image);
        mainList.add(movie);
        allMainList.add(movie);
    }

    private void loadElement(){
        recyclerView = findViewById(R.id.main_recycle);
        progressBar = findViewById(R.id.progressBar);
        mainAdapter = new MainAdapter(mainList);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getDataFromServer();
        LinearLayoutManager HLayoutManager = new LinearLayoutManager(this);
        HLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(HLayoutManager);
        recyclerView.setAdapter(mainAdapter);
        searchEdit = findViewById(R.id.main_search);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSearchData(searchEdit.getText().toString());
            }
        });
    }
}