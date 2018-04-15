package com.example.w10.itgcoea.DiscussionForum;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.w10.itgcoea.R;
import com.example.w10.itgcoea.UploadViewImage.ViewImage2;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chat_Room  extends AppCompatActivity{

    ScrollView scrollView;
   Firebase reference1, reference2;

    SimpleDateFormat sdfa;
    LinearLayout layout;
    RelativeLayout layout_2;
    private static final int GALLERY_PICK = 1;
    private Button btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;
    int i=0;
    private StorageReference mImageStorage;
    ImageView sendButton;
    EditText messageArea;
    private String user_name,room_name;
    private DatabaseReference root,root2,roota ;
    private String temp_key,root_name,yes;
    private ImageButton mChatAddBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__room);
        messageArea = (EditText)findViewById(R.id.messageArea);
        mChatAddBtn = (ImageButton) findViewById(R.id.chat_add_btn);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        mImageStorage = FirebaseStorage.getInstance().getReference();

        sdfa = new SimpleDateFormat("EEE, MMM d 'AT' HH:mm a");
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.fullScroll(View.FOCUS_DOWN);
        Firebase.setAndroidContext(this);


        //  btn_send_msg = (Button) findViewById(R.id.btn_send);
        // input_msg = (EditText) findViewById(R.id.msg_input);

        // chat_conversation = (TextView) findViewById(R.id.textView);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        setTitle(" Room - "+room_name);

        root2 = FirebaseDatabase.getInstance().getReference().getRoot();
        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        findRoota();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = roota.push().getKey();
                roota.updateChildren(map);


                DatabaseReference message_root = roota.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("type", "text");
                map2.put("message",messageArea.getText().toString());
                map2.put("time", ServerValue.TIMESTAMP);


                message_root.updateChildren(map2);
                messageArea.setText("");
            }
        });

        mChatAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

            }
        });

/*

        roota.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

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


*/


    }

    private String chat_msg,chat_user_name;
    private long time;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();
            time=(long) ((DataSnapshot)i.next()).getValue();
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String a=sfd.format(new Date(time));

            chat_conversation.append(chat_user_name +" : "+chat_msg +" : "+a +" \n");
        }


    }

    private void append_chat_conversation2(DataSnapshot dataSnapshot) {



    }


    public void findRoota()
    {

        root2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {

                    for (DataSnapshot ds1 : ds.getChildren())
                    {
                        String name = ds1.getKey();

                        if (room_name.equals(name))
                        {
                            roota=ds1.getRef();
                        }

                    }

                }


                roota2();            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }
    public void roota2()
    {

        roota.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Map map = dataSnapshot.getValue(Map.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String type=map.get("type").toString();
                String message = map.get("message").toString();
                String userName = map.get("name").toString();
                Long time = (Long) map.get("time");
                String a=sdfa.format(new Date(time));
                if(type.equals("text"))
                {
                    if(userName.equals(user_name)){
                        addMessageBox("You " , message,a, 1);

                    }
                    else{
                        addMessageBox(userName , message,a, 2);
                    }
                }

                else
                {
                    if(userName.equals(user_name)){
                        addMessageBox11("You " , message,a, 1);

                    }
                    else{
                        addMessageBox11(userName , message,a, 2);
                    }
                }

                //   append_chat_conversation(dataSnapshot);
                //   append_chat_conversation2(dataSnapshot);
                // Toast.makeText(Chat_Room.this,"first"+"",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                // append_chat_conversation(dataSnapshot);

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

    public void addMessageBox11(String name, final String message, String time, int type){

        ImageView imageView = new ImageView(Chat_Room.this);
        TextView textname = new TextView(Chat_Room.this);
        TextView texttime = new TextView(Chat_Room.this);



        textname.setText(name+" :");
        // textname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        textname.setTextSize(15);
        textname.setTypeface(null, Typeface.BOLD);


        textname.setBackgroundResource(R.drawable.rounded_corner1);
        imageView.setAdjustViewBounds(true);
        imageView.setMaxHeight(500);

        //texttime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        Picasso.with(Chat_Room.this).load(message).into(imageView);

        texttime.setTextSize(12);
        texttime.setText(time);
        texttime.setTypeface(null,Typeface.ITALIC);
        texttime.setBackgroundResource(R.drawable.rounded_corner2);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // lp2.weight = 1.0f;

        if(type == 1) {
            lp1.gravity = Gravity.RIGHT;
            lp2.gravity = Gravity.RIGHT;
            lp3.gravity = Gravity.RIGHT;


        }
        else{
            lp1.gravity = Gravity.LEFT;
            lp2.gravity = Gravity.LEFT;
            lp3.gravity = Gravity.LEFT;
            imageView.setBackgroundResource(R.drawable.text_out);
        }

        textname.setLayoutParams(lp1);
        imageView.setLayoutParams(lp2);
        texttime.setLayoutParams(lp3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Chat_Room.this,ViewImage2.class);
                i.putExtra("url", message);

                startActivity(i);
            }
        });
        layout.addView(textname);
        layout.addView(imageView);
        layout.addView(texttime);
        scrollView.fullScroll(View.FOCUS_DOWN);

        //imageView.setOnTouchListener(new ImageMatrixTouchHandler(Chat_Room.this));

    }
    public void addMessageBox(String name,String message,String time, int type){

        TextView textmsg = new TextView(Chat_Room.this);
        TextView textname = new TextView(Chat_Room.this);
        TextView texttime = new TextView(Chat_Room.this);



        textname.setText(name+" :");
        // textname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        textname.setTextSize(15);
        textname.setTypeface(null, Typeface.BOLD);
        textmsg.setText(message);

        textname.setBackgroundResource(R.drawable.rounded_corner1);

        //texttime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);

        texttime.setTextSize(12);
        texttime.setText(time);
        texttime.setTypeface(null,Typeface.ITALIC);
        texttime.setBackgroundResource(R.drawable.rounded_corner2);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;
        textmsg.setTextSize(25);
        if(type == 1) {
            lp1.gravity = Gravity.RIGHT;
            lp2.gravity = Gravity.RIGHT;
            lp3.gravity = Gravity.RIGHT;
            textmsg.setBackgroundResource(R.drawable.text_in);

        }
        else{
            lp1.gravity = Gravity.LEFT;
            lp2.gravity = Gravity.LEFT;
            lp3.gravity = Gravity.LEFT;
            textmsg.setBackgroundResource(R.drawable.text_out);
        }
        textmsg.setTypeface(null, Typeface.BOLD);
        ;
        textname.setLayoutParams(lp1);
        textmsg.setLayoutParams(lp2);
        texttime.setLayoutParams(lp3);
        layout.addView(textname);
        layout.addView(textmsg);
        layout.addView(texttime);
        scrollView.fullScroll(View.FOCUS_DOWN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            Uri imageUri = data.getData();

            final Map<String,Object> map = new HashMap<String, Object>();
            temp_key = roota.push().getKey();
            roota.updateChildren(map);

            StorageReference filepath = mImageStorage.child("message_images").child( temp_key + ".jpg");

            filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){

                        String download_url = task.getResult().getDownloadUrl().toString();
                        DatabaseReference message_root = roota.child(temp_key);
                        Map<String,Object> map2 = new HashMap<String, Object>();

                        map2.put("name",user_name);
                        map2.put("time", ServerValue.TIMESTAMP);
                        map2.put("message", download_url);
                        map2.put("type", "image");
                        message_root.updateChildren(map2);
                        messageArea.setText("");
                    }

                }
            });

        }

    }


}
