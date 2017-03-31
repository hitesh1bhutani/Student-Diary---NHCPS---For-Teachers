package com.example.hitesh1bhutani.studentdiary_nhcps_forteachers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by hitesh1bhutani on 3/30/2017.
 */

public class ClassList extends Activity{

    private ArrayList<String> mClasses;
    private final String URL =  "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list);
        final ListView mListView = (ListView) findViewById(R.id.lv_classList_classes);
        final Firebase mRef = new Firebase(URL);
        mClasses = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mClasses);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(ClassList.this, Subjects.class);
                Bundle bundle = new Bundle();
                String className = mListView.getItemAtPosition(position).toString();
                bundle.putString(getResources().getString(R.string.key_classNameURL), URL.concat(className));
                newActivity.putExtras(bundle);
                startActivity(newActivity);
            }
        });
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mClasses.add(value);
                arrayAdapter.notifyDataSetChanged();
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
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
