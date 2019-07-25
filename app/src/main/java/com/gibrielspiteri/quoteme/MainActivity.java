package com.gibrielspiteri.quoteme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import static com.gibrielspiteri.quoteme.Notification.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final String TAG = "MainActivity";
    private EditText etInput;
    private EditText etAuthor;
    private Button btnPost;
    private Button btnView;
    private Spinner spinnerGenre;
    private String genreText;
    private String id;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private Quote newQuote;
    private int max;

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        //Firebase Connection
        myRef = FirebaseDatabase.getInstance().getReference();

        etInput = findViewById(R.id.etInput);
        etAuthor = findViewById(R.id.etAuthor);
        btnPost = findViewById(R.id.btnPost);
        btnView = findViewById(R.id.btnView);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genres_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerGenre.setAdapter(adapter);
        spinnerGenre.setOnItemSelectedListener(this);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel1(v);
                id = myRef.push().getKey();
                myRef.child(genreText).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        max = (int)(mutableData.getChildrenCount());
                        newQuote = new Quote(etInput.getText().toString(), etAuthor.getText().toString(), genreText, id, max+1);

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        Toast.makeText(MainActivity.this, String.valueOf(max), Toast.LENGTH_SHORT).show();

                        myRef.child(genreText).child(id).setValue(newQuote);
                    }
                });


            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SeeQuoteActivity.class);
                MainActivity.this.startActivity(i);
                Log.i(TAG,"Moving Views");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        genreText = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), genreText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void sendOnChannel1(View v){
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_arrow_upward_black_24dp)
                .setContentTitle(etAuthor.getText().toString())
                .setContentText(etInput.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }
}
