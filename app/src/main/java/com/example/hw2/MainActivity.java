package com.example.hw2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;

//Contact object class for listView entries
class Contact {
    private long id;
    private String name;
    private String contactInfo;
    
    public Contact(long id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }
    
    public long getId() { return id; }
    public String getInfo() {
        return contactInfo;
    }
    @Override
    public String toString() {
        return name;
    }
}

public class MainActivity extends Activity {
    List<Object> contacts = new ArrayList<>();
    ArrayAdapter adapter;
    public final int REQUEST_CODE = 1;
    public boolean del = false;
    DBAdapter db;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ListView list = findViewById(R.id.list0);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        adapter = new ArrayAdapter(
                this,
                R.layout.activity_listview,
                contacts
        );
        list.setAdapter(adapter);
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact con = (Contact) adapter.getItem(position);
                
                if (del == true) {
                    db.open();
                    if (db.deleteContact(con.getId()) == true) {
                        Toast.makeText(getApplicationContext(), 
                                "Deleted Contact " +con.toString(), 
                                Toast.LENGTH_LONG).show()
                        ;
                    }
                    else {
                        Toast.makeText(getApplicationContext(), 
                                "Delete failed.", 
                                Toast.LENGTH_LONG).show()
                        ;
                    }
                    db.close();
                    
                    contacts.remove(con);
                    adapter.notifyDataSetChanged();
                    return;
                }
                
                Toast.makeText(MainActivity.this,
                        (adapter.getItem(position)).toString(), 
                        Toast.LENGTH_SHORT
                ).show();
                
                Intent data = new Intent("com.example.hw2.ThirdActivity");
                data.putExtra("data", con.getInfo());
                startActivity(data);
            }
        });

        // Version 3 additions --------------------------------------------------------
        
        db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
            do {
                contacts.add(new Contact(
                        Long.parseLong(c.getString(0)), 
                        c.getString(1), 
                        c.getString(2)
                ));
            } while (c.moveToNext());
            adapter.notifyDataSetChanged();
        }
        db.close();

        // Version 3 additions finished -----------------------------------------------
    }
    
    public void onClick(View view) {
        Intent data = new Intent("com.example.hw2.SecondActivity");
        
        startActivityForResult(data, REQUEST_CODE);
    }
    
    public void deleteToggle(View view) {
        Button btn = (Button) findViewById(R.id.del);
        if (del == true) {
            del = false;
            btn.setBackgroundColor(Color.parseColor("#494954"));
        }
        else {
            del = true;
            btn.setBackgroundColor(Color.parseColor("#ff4824"));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode != REQUEST_CODE) {
            return;
        }
        if (resultCode != RESULT_OK) {
            return;
        }
        
        String contact = 
                "Name: " +data.getStringExtra("name")
                +"\nPhone: " +data.getStringExtra("phone")
                +"\nEmail: " +data.getStringExtra("email")
                +"\nAddress: " +data.getStringExtra("address")
                +"\nRelationship: " +data.getStringExtra("relation")
        ;

        db.open();
        long id = db.insertContact(data.getStringExtra("name"), contact);
        db.close();
        
        contacts.add(new Contact(id, data.getStringExtra("name"), contact));
        adapter.notifyDataSetChanged();
        
        Toast.makeText(this, contact, Toast.LENGTH_SHORT).show();
    }
}