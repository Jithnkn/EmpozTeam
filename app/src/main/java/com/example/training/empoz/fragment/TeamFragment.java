package com.example.training.empoz.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.training.empoz.R;
import com.example.training.empoz.activity.TeamCreation;
import com.example.training.empoz.adapters.Team;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class TeamFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference rootRef;
    Button bAddTeam;

    private ListView listview;
    View view;
    private ArrayList<String> team=new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_team_tab, container, false);
       //bAddTeam= view.findViewById(R.id.bAddTeam);
        database=FirebaseDatabase.getInstance();
        rootRef=database.getReference("Teams");
        listview= view.findViewById(R.id.listteam);

        final ArrayAdapter<String> adapter=new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1,team);
        listview.setAdapter(adapter);
        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Team value=dataSnapshot.getValue(Team.class);
                team.add(value.teamName);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Team value=dataSnapshot.getValue(Team.class);
                team.remove(value.teamName);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        bAddTeam.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onClick(View v) {
////                Intent intent=new Intent(getActivity(), TeamCreation.class);
////                startActivity(intent);
//
//            }
//        });
        return view;

    }

}
