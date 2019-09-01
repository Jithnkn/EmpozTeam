package com.example.training.empoz.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.training.empoz.R;

import com.example.training.empoz.adapters.Employee;
import com.example.training.empoz.adapters.Leavenoti;
import com.example.training.empoz.fragment.Post;

import com.example.training.empoz.fragment.LeaveFragment;
import com.example.training.empoz.fragment.TeamFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeHome extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
   FirebaseDatabase firebaseDatabase;
   FirebaseAuth firebaseAuth;
   DatabaseReference databaseReference,data;
   String uid,conditon,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe_home);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        uid=firebaseAuth.getCurrentUser().getUid();
        data=firebaseDatabase.getReference("Notification");
        databaseReference=firebaseDatabase.getReference("Users/"+uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Employee employee=dataSnapshot.getValue(Employee.class);
                username=employee.getName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

      data.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String s) {
              Leavenoti leavenoti=dataSnapshot.getValue(Leavenoti.class);
              if(leavenoti.emame.equalsIgnoreCase(username))
              {
                  conditon=leavenoti.condition;
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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.*
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_employe_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {


            case R.id.notification:
                Toast.makeText(this,"Notification clicked",Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
                alertbox.setMessage("Your leave Request "+conditon);
                alertbox.show();
                break;

            case R.id.action_about:
                Toast.makeText(this,"About clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_feedback:
                Toast.makeText(this,"Feedback clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logout:
                firebaseAuth.signOut();
                Toast.makeText(this,"Logout clicked",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(this,EmployeeLogin.class);
                startActivity(i);
                break;




        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_employee_home, container, false);
            TextView textView = (TextView) getView().findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           switch (position)
           {

               case 0:Post post =new Post();
               return post;
               case 1:LeaveFragment leaveFragment =new LeaveFragment();
               return leaveFragment;
           }
           return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
