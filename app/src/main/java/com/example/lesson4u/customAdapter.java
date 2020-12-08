package com.example.lesson4u;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;

public class customAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private boolean isQueryingFinished = false;
    private ArrayList<LessonObj> lessons = new ArrayList<LessonObj>();
    final String Tag = "customAdapter";
//    private boolean con = false;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("lessons");
    LessonObj tempLesson;
    private Context context;

    public customAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
        getLessonsFromDB();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getLessonsFromDB();
//
//            }
//        });
//        thread.start();
//        while(!isQueryingFinished){
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        Log.d(Tag, "1lesson.size : "+lessons.size() + ", list.size = "+list.size() );
//        myRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//               for(int i =0; i<list.size(); i++){
//                   lessons.add(dataSnapshot.child(list.get(i)).getValue(LessonObj.class));
//               }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//
//        });
//        while(!con){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("lessons");
//        myRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                tempLesson = dataSnapshot.child(list.get(pos)).getValue(LessonObj.class);
//                return tempLesson;
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//
//        });
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout, null);
        }


        //Handle TextView and display string from your list
        TextView tvContact= (TextView)view.findViewById(R.id.tvContact);
        Log.d(Tag, "2lesson.size : "+lessons.size() + ", list.size = "+list.size() );
//        LessonObj templ = lessons.get(position);
//        tvContact.setText("         | date: "+templ.getDate()+" | time: "+templ.getTime()+":00 | price: "+templ.getPrice());
        tvContact.setText(list.get(position)); //////// ?????

        //Handle buttons and add onClickListeners
        Button callbtn= (Button)view.findViewById(R.id.btn);
        callbtn.setText("<");

        callbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something

            }
        });

        tvContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
//                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void getLessonsFromDB(){
         CountDownLatch done = new CountDownLatch(1);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for(int i =0; i<list.size(); i++){
                   if(!lessons.isEmpty()) break;
                   LessonObj t = dataSnapshot.child(list.get(i)).getValue(LessonObj.class);
                   lessons.add(t);

               }
               isQueryingFinished = true;
               done.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

//        try {
//            done.await(); //it will wait till the response is received from firebase.
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}