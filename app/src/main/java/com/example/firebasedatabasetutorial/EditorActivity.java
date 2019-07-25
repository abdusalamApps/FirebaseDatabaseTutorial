package com.example.firebasedatabasetutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditorActivity extends AppCompatActivity {

    private static final String TAG = EditorActivity.class.getSimpleName();
    private EditText mModelET, mColorET, mDescriptionET, mSpeedET;
    private Button mUploadButton;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        findViews();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("cars");

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference.push().setValue(new Car(
                        mModelET.getText().toString().trim(),
                        mColorET.getText().toString().trim(),
                        Integer.parseInt(mSpeedET.getText().toString()),
                        0,
                        mDescriptionET.getText().toString().trim(),
                        null

                )).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: upload succeeded");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: upload failed :(");
                    }
                });
            }
        });
        finish();
    }

    private void findViews() {
        mModelET = findViewById(R.id.model_et);
        mColorET = findViewById(R.id.color_et);
        mDescriptionET = findViewById(R.id.description_et);
        mSpeedET = findViewById(R.id.speed_et);
        mUploadButton = findViewById(R.id.upload_btn);
    }

}
