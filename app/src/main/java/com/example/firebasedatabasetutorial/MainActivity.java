package com.example.firebasedatabasetutorial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Car> mCarList;
    private Car mCar;
    private ChildEventListener mChildEventListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private FirebaseRecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private void findViews() {
        mRecyclerView = findViewById(R.id.cars_rv);
        mFab = findViewById(R.id.floatingButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("cars");
        mCarList = new ArrayList<>();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(view.getContext(), EditorActivity.class), 1);

            }
        });

        mLayoutManager = new LinearLayoutManager(MainActivity.this);

        FirebaseRecyclerOptions<Car> options = new FirebaseRecyclerOptions.Builder<Car>()
                .setQuery(mDatabaseReference, new SnapshotParser<Car>() {
                    @NonNull
                    @Override
                    public Car parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Car(snapshot.child("model").getValue().toString(),
                                snapshot.child("color").getValue().toString(),
                                Integer.parseInt(snapshot.child("speed").getValue().toString()),
                                Long.parseLong(snapshot.child("views").getValue().toString()),
                                snapshot.child("description").getValue().toString(),
                                snapshot.getKey()
                        );
                    }
                }).build();
        mRecyclerAdapter = new FirebaseRecyclerAdapter<Car, CarViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final CarViewHolder holder, int i, @NonNull final Car car) {

                holder.setModel(car.getModel());
                holder.setColor(car.getColor());
                holder.setSpeed(car.getSpeed());
                holder.setViews(car.getViews());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(holder.itemView.getContext(), CarDetailsActivity.class);
                        intent.putExtra("model", car.getModel());
                        intent.putExtra("description", car.getDescription());
                        intent.putExtra("color", car.getColor());
                        intent.putExtra("views", String.valueOf(car.getViews()));
                        intent.putExtra("speed", String.valueOf(car.getSpeed()));

                        mDatabaseReference.child(mCarList.get(holder.getAdapterPosition()).getKey()).runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                                Car car = mutableData.getValue(Car.class);
                                if (car == null) {
                                    return Transaction.success(mutableData);
                                }
                                car.setViews(car.getViews() + 1);
                                Log.d(TAG, "doTransaction: views count: " + car.getViews());
                                mutableData.setValue(car);
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                                Log.d(TAG, "onComplete: " + databaseError);
                            }
                        });

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new CarViewHolder(rootView);
            }
        };

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        attachDatabaseReadListener();
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Car car = dataSnapshot.getValue(Car.class);
                    car.setKey(dataSnapshot.getKey());
                    mCarList.add(car);
                    mRecyclerAdapter.notifyDataSetChanged();
                    Log.d(TAG, "onChildAdded: " + mCarList.size());
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    String key = dataSnapshot.getKey();
                    for (Car car : mCarList) {
                        if (key.equals(car.getKey())) {
                            mCarList.remove(car);
                            mRecyclerAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            mDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRecyclerAdapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachDatabaseReadListener();
    }
}
