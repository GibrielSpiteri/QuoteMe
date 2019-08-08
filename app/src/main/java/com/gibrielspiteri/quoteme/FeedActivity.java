package com.gibrielspiteri.quoteme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {
    public static final String TAG = "FeedActivity";

    private ArrayList<String> mQuotes = new ArrayList<>();
    private ArrayList<String> mAuthors = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Log.d(TAG, "OnCreate: started");
        myRef = FirebaseDatabase.getInstance().getReference("Positivity");
        initImageBitmaps();
    }

    private void initImageBitmaps(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.i(TAG, "onDataChange");
                    Quote quote = snapshot.getValue(Quote.class);
                    mQuotes.add(quote.theQuote);
                    mAuthors.add(quote.author);
                    //mImages.add("https://upload.wikimedia.org/wikipedia/commons/6/62/Starsinthesky.jpg");
                    initRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: started");

        RecyclerView recyclerView = findViewById(R.id.rvFeed);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mQuotes, mAuthors, mImages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
