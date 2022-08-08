//Drop down where we collect data from realtime database and show into dropdown






package com.example.dropdown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView bazarName;
    ArrayAdapter<String> adapterItem;
    ArrayList<String> mylist = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bazarName = (AutoCompleteTextView) findViewById(R.id.select_bazar);
        TextView textView = (TextView)findViewById(R.id.textview);
        TextInputLayout textInputLayout= findViewById(R.id.text);






        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Markets").child("dhaka");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    textInputLayout.setVisibility(View.VISIBLE);
                    for (DataSnapshot markets : snapshot.getChildren()) {
                        String market = String.valueOf(markets.child("bazar_name").getValue()).toUpperCase();
                        System.out.println(market);
                        mylist.add(market);

                    }
                }
                else{
                    System.out.println("No data is here");
                    textInputLayout.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }

                adapterItem = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, mylist);
                bazarName.setAdapter(adapterItem);
                System.out.println("my list:" + mylist);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        bazarName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String crop= adapterView.getItemAtPosition(i).toString().toUpperCase(Locale.ROOT);
//                System.out.println(crop);
//            }
//        });


    }
}