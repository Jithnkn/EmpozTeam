package com.example.training.empoz.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.training.empoz.R;
import com.example.training.empoz.adapters.PostAdapter;
//import com.example.training.empoz.model.Employee;
import com.example.training.empoz.adapters.PostSender;
import com.example.training.empoz.model.Post_Content;
import com.example.training.empoz.utils.Constants;
import com.example.training.empoz.utils.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class Post extends Fragment {


    LayoutInflater inflate;
    View view;
    private TextView user_post, user_content, user_date;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    private ListView listview;
    private ArrayList<String> emppost = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_post, container, false);
        inflate = getLayoutInflater();


        database = FirebaseDatabase.getInstance();

        String username = PreferenceManager.getFromPreference(getActivity(), Constants.USERNAME);
        final String department = PreferenceManager.getFromPreference(getActivity(),Constants.DEPARTMENT);
       // Toast.makeText(getContext(),department+"",Toast.LENGTH_LONG).show();

        //SharedPreferences sharedPreferences=getContext().getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);

        listview = view.findViewById(R.id.list_post);
        myRef = database.getReference("DepartmentPost");

//       String auth=mAuth.getCurrentUser().getUid();
//       Toast.makeText(getContext(),auth+"",Toast.LENGTH_LONG).show();


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, emppost);
        listview.setAdapter(adapter);
        final String postOrg = "Organization";
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                final PostSender postEmp = dataSnapshot.getValue(PostSender.class);
                if (TextUtils.equals(postEmp.postconcept, postOrg)||TextUtils.equals(postEmp.postconcept,department)) {
                    emppost.add(postEmp.post);


                    adapter.notifyDataSetChanged();
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String val = (String) parent.getItemAtPosition(position);
                            String date = (String) postEmp.postdate;
                            String user_name = (String) postEmp.postuser;
                            String post_content = (String) postEmp.postcontent;

                            // Toast.makeText(getContext(),"Item Clicked.1",Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            builder.setTitle("Post Details : " + val);
                            builder.setMessage("Post User:" + user_name + "\n" + "Post Content:" + post_content + "\n" + "Post Date:" + date);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Inflate the layout for this fragment
        return view;

    }


}


























