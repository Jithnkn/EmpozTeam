package com.example.training.empoz.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.training.empoz.R;
import com.example.training.empoz.adapters.Employee;
import com.example.training.empoz.adapters.LeaveSender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LeaveList extends AppCompatActivity implements AdapterView.OnItemClickListener {
ListView LeaveListView;
    private FirebaseDatabase database;
    private DatabaseReference reference,databaseReference,leaveRefrence;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> emp=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_list);
        LeaveListView=(ListView)findViewById(R.id.leaveList);
        database=FirebaseDatabase.getInstance();



        leaveRefrence=database.getReference("Leave");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, emp);
        LeaveListView.setAdapter(adapter);
        firebaseAuth=FirebaseAuth.getInstance();
        leaveRefrence.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final LeaveSender leaveSender=dataSnapshot.getValue(LeaveSender.class);
                final String leaveId=leaveSender.userId;
                final String id=firebaseAuth.getCurrentUser().getUid();
                databaseReference=database.getReference("Users/"+leaveId);


              databaseReference.addChildEventListener(new ChildEventListener() {
                  @Override
                  public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                      final Employee employee=dataSnapshot.getValue(Employee.class);
                      reference=database.getReference("Users/"+id);
                      reference.addChildEventListener(new ChildEventListener() {
                          @Override
                          public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                              Employee empl=dataSnapshot.getValue(Employee.class);
                              if(employee.department.equalsIgnoreCase(empl.department))

                              {
                                  emp.add(empl.name);
                                  adapter.notifyDataSetChanged();

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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.leavedialogbox);
        dialog.setTitle("LeaveRequest");
        final TextView ereason=(TextView) findViewById(R.id.ereason) ;
        final TextView edatefrom=(TextView)findViewById(R.id.edatefrom) ;
        final TextView edateto=(TextView)findViewById(R.id.edateto) ;

        leaveRefrence.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LeaveSender leaveSender=dataSnapshot.getValue(LeaveSender.class);
                ereason.setText(leaveSender.reason);
                edatefrom.setText(leaveSender.dateFrom);
                edateto.setText(leaveSender.dateTo);

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
        Button leaveapprove=(Button)dialog.findViewById(R.id.bapprove);
        Button leavereject=(Button)dialog.findViewById(R.id.breject);
        leaveapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        leavereject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
