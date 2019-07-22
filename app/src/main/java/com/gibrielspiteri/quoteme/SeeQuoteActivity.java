package com.gibrielspiteri.quoteme;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class SeeQuoteActivity extends AppCompatActivity {
    private final String TAG = "SeeQuoteActivity";
    private TextView tvDisplay;
    private Button btnQuote;
    private DatabaseReference myRef;
    private int index;
    private long max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_quote);

        tvDisplay = findViewById(R.id.tvDisplay);
        btnQuote = findViewById(R.id.btnQuote);

        myRef = FirebaseDatabase.getInstance().getReference();

        btnQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int genreIndex = (int) (Math.random() * 5 + 1);
                String theGenre = "Positivity";
                switch (genreIndex) {
                    case 1:
                        theGenre = "Positivity";
                        break;
                    case 2:
                        theGenre = "Motivation";
                        break;
                    case 3:
                        theGenre = "Spirituality";
                        break;
                    case 4:
                        theGenre = "Romance";
                        break;
                    case 5:
                        theGenre = "Success";
                        break;
                }

                myRef.child("Positivity").runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        max = mutableData.getChildrenCount();
                        index = (int) (Math.random() * max + 1);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        Toast.makeText(SeeQuoteActivity.this, String.valueOf(index), Toast.LENGTH_SHORT).show();
                        String msg = "Max Value: " + String.valueOf(max);
                        Log.i(TAG, msg);
                        Query query = myRef.child("Positivity").orderByChild("index").startAt(1).endAt(max);


                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String theQuote = "";
                                Log.i(TAG, "Entered Listener");
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    Quote quote = snapshot.getValue(Quote.class);
                                    Log.i(TAG, String.valueOf(index));
                                    if(quote.index == index) {
                                        theQuote = quote.theQuote + " - " + quote.author;
                                        Log.i(TAG, theQuote);
                                    }
                                }
                                Log.i(TAG, dataSnapshot.toString());
                                tvDisplay.setText(theQuote);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w(TAG, "LoadQuote:onCancelled", databaseError.toException());
                            }
                        });
                    }
                });

            }
        });
    }

}
