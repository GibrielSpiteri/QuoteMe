package com.gibrielspiteri.quoteme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final String TAG = "MainActivity";
    private EditText etInput;
    private Button btnPost;
    private Spinner spinnerGenre;
    private String text;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase Connection
        database = FirebaseDatabase.getInstance();
        //myRef = database.getReference("feelgood-46f98");

        etInput = findViewById(R.id.etInput);
        btnPost = findViewById(R.id.btnPost);
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
                Toast.makeText(MainActivity.this, etInput.getText(), Toast.LENGTH_SHORT).show();
                myRef = database.child(text).setValue(etInput.getText());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
