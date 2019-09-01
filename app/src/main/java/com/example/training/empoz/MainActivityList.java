package com.example.training.empoz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivityList extends AppCompatActivity {
    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<String> uEmp = new ArrayList<>();



//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_list);
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("employee");
//        listView = findViewById(R.id.listone);
//        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter <String>(this,R.layout.support_simple_spinner_dropdown_item,uEmp);
//        listView.setAdapter(arrayAdapter);
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String val=dataSnapshot.getValue(String.class);
//                uEmp.add(val);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//

    }

