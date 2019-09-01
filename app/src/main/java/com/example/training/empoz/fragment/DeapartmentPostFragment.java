package com.example.training.empoz.fragment;
import com.example.training.empoz.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.training.empoz.adapters.PostSender;
import com.example.training.empoz.utils.Constants;
import com.example.training.empoz.utils.PreferenceManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DeapartmentPostFragment extends Fragment {
    LayoutInflater inflate;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference Ref;
    private ListView listview;
    FloatingActionButton buttonPost;
    private ArrayList<String> postorg=new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_deapartment_post, container, false);
        inflate = getLayoutInflater();
        database = FirebaseDatabase.getInstance();
        listview= v.findViewById(R.id.list_dept);
        myRef = database.getReference("DepartmentPost");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, postorg);
        listview.setAdapter(adapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final PostSender postEmp = dataSnapshot.getValue(PostSender.class);
                postorg.add(postEmp.post);
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

        buttonPost= (FloatingActionButton) v.findViewById(R.id.floatingActionButton2);
        buttonPost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater inflater=getLayoutInflater();
                View alertLayout=inflater.inflate(R.layout.dialogue_box,null);
                final EditText postContent=(EditText) alertLayout.findViewById(R.id.post_content);
                final EditText postTitle=(EditText) alertLayout.findViewById(R.id.post_title);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                final String date = sdf.format(System.currentTimeMillis());
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Add New Post :");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String userkey=myRef.push().getKey();
                        String user_name = PreferenceManager.getFromPreference(getActivity(), Constants.USER_NAME);
                        String department = PreferenceManager.getFromPreference(getActivity(), Constants.DEPARTMENT);

                        Toast.makeText(getContext(),user_name+"",Toast.LENGTH_LONG).show();


                        final PostSender postSender=new PostSender(postContent.getText().toString(),date,user_name,postTitle.getText().toString(),department);
                        myRef.child(userkey).setValue(postSender);
                        Toast.makeText(getContext(), "Post Added Successfully "  , Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

            }
        });



        return v;
    }
}
