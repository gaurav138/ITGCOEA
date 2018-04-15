package com.example.w10.itgcoea.DiscussionForum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w10.itgcoea.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;




public class StudentDiscussion extends AppCompatActivity {
    String roll_no, year="", name;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().child("first year");
    private DatabaseReference root2 = FirebaseDatabase.getInstance().getReference().child("second year");
    private DatabaseReference root3 = FirebaseDatabase.getInstance().getReference().child("third year");
    private DatabaseReference root4 = FirebaseDatabase.getInstance().getReference().child("fourth year");
    private DatabaseReference roota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_discussion);


        Intent ii = getIntent();
        roll_no = ii.getStringExtra("roll_no");
        name=ii.getStringExtra("name");

        if (roll_no.contains("16007")||roll_no.contains("17107"))
            year = "second year";
        else if (roll_no.contains("15007")||roll_no.contains("16107"))
            year = "third year";
        else if (roll_no.contains("14007")||roll_no.contains("15107"))
            year = "fourth year";
        else
            Toast.makeText(StudentDiscussion.this, "Roll no is not valid", Toast.LENGTH_SHORT).show();

        if(year.equals("first year"))
            roota=FirebaseDatabase.getInstance().getReference().child("first year");
        else if(year.equals("second year"))
            roota=FirebaseDatabase.getInstance().getReference().child("second year");
        else if(year.equals("third year"))
            roota=FirebaseDatabase.getInstance().getReference().child("third year");
        else if(year.equals("fourth year"))
            roota=FirebaseDatabase.getInstance().getReference().child("fourth year");
        else
            Toast.makeText(StudentDiscussion.this, "Invalid input", Toast.LENGTH_SHORT).show();



        listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_of_rooms);

        roota.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), Chat_Room.class);
                intent.putExtra("room_name", ((TextView) view).getText().toString());
                intent.putExtra("user_name", name);
                startActivity(intent);
            }
        });

    }

    public void show() {

        roota.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
