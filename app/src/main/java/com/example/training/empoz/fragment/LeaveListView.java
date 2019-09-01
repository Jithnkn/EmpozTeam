package com.example.training.empoz.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.training.empoz.DepartmentLeaveRequest;
import com.example.training.empoz.R;
import com.example.training.empoz.adapters.Employee;
import com.example.training.empoz.adapters.LeaveSender;
import com.example.training.empoz.adapters.Leavenoti;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LeaveListView extends Fragment {
    ImageButton re;
    ListView leaveList;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    String uid;
   private DatabaseReference reference,databaseReference,data,ref;
   private ArrayList<String> emp=new ArrayList<>();
   Employee employee;
    String leavaename,key;
    TextView ereason,edatefrom,edateto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_leave_list_view, container, false);
        leaveList=(ListView)view.findViewById(R.id.leaveList);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, emp);
        leaveList.setAdapter(adapter);
        database=FirebaseDatabase.getInstance();
       firebaseAuth=FirebaseAuth.getInstance();
        re=(ImageButton)view.findViewById(R.id.leave_add);
        data=database.getReference("Users");
       reference=database.getReference("Leave");
     uid=firebaseAuth.getCurrentUser().getUid();
     ref=database.getReference("Notification");

       databaseReference=database.getReference("Users/"+uid);



        firebaseAuth=FirebaseAuth.getInstance();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final LeaveSender leaveSender=dataSnapshot.getValue(LeaveSender.class);
              leavaename=leaveSender.getName();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Employee employee=dataSnapshot.getValue(Employee.class);
                        data.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Employee emplo=dataSnapshot.getValue(Employee.class);
                                if(emplo.getName().equalsIgnoreCase(leavaename)) {
                                    if (employee.getDepartment().equalsIgnoreCase(emplo.getDepartment())) {
                                        if (employee.getPosition().equalsIgnoreCase("department")) {
                                            if (emplo.getPosition().equalsIgnoreCase("Employee")) {

                                                emp.add(String.valueOf(leaveSender.getName()));
                                            }
                                        }

                                    }

                                    else {
                                        if (employee.getPosition().equalsIgnoreCase("department")) {

                                            emp.add(String.valueOf(leaveSender.getName()));
                                        }
                                    }
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

re.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getActivity(), DepartmentLeaveRequest.class);
        startActivity(i);
    }
});


            leaveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.leavedialogbox);
                    dialog.setTitle("LeaveRequest");
                      ereason = (TextView) dialog.findViewById(R.id.ereason);
                      edatefrom = (TextView) dialog.findViewById(R.id.edatefrom);
                      edateto = (TextView) dialog.findViewById(R.id.edateto);
                    Button leaveapprove = (Button) dialog.findViewById(R.id.bapprove);
                    Button leavereject = (Button) dialog.findViewById(R.id.breject);
                    final String namea= (String) leaveList.getItemAtPosition(position);
                    DatabaseReference re=database.getReference("Leave");
                    re.addChildEventListener(new ChildEventListener() {


                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            LeaveSender leaveSender=dataSnapshot.getValue(LeaveSender.class);
                       key=dataSnapshot.getKey();
                            if(namea.equalsIgnoreCase(leaveSender.getName()))
                            {
                                String reson=leaveSender.reason;
                                String from=leaveSender.dateFrom;
                                String to=leaveSender.dateTo;
                                ereason.setText(reson);
                                edatefrom.setText(from);
                                edateto.setText(to);
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

                    leaveapprove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String key = ref.push().getKey();
                            Leavenoti leavenoti=new Leavenoti(namea,"Approve");
                            ref.child(key).setValue(leavenoti);
                            String s=reference.child(namea).getKey();
                            Toast.makeText(getContext(),"Approved",Toast.LENGTH_SHORT).show();
                            reference.child(key).removeValue();

                        }

                    });
                    leavereject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String key = ref.push().getKey();
                            Leavenoti leavenoti=new Leavenoti(namea,"Reject");
                            ref.child(key).setValue(leavenoti);
                            Toast.makeText(getContext(),"Rejected",Toast.LENGTH_SHORT).show();
                            reference.child(key).removeValue();

                        }


                    });
                    dialog.show();
                }

            });


        return view;

}
}