package com.creditmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Viewdata extends AppCompatActivity {

    Databasehelper mydb;

    ListView listView;

    ArrayList<String> listitem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);

        listitem = new ArrayList<>();
        mydb = new Databasehelper(this);

        listView = (ListView)findViewById(R.id.listview) ;

        viewdata();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = listView.getItemAtPosition(position).toString();
//                showMessage("user "+text,text);
                Intent intent = new Intent(getApplicationContext(),Viewuserdetails.class);
                intent.putExtra("name",text);
                intent.putExtra("id",id);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        });

    }



    private void viewdata()
    {
        Cursor cursor = mydb.getAllData();
        if(cursor.getCount() == 0) {
            showMessage("Error","Nothing found");
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                listitem.add(cursor.getString(1));
//                listitem.add(cursor.getString(2));
//                Toast.makeText(getApplicationContext(),cursor.getString(2).toString(),Toast.LENGTH_LONG).show();
            }
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listitem);
            listView.setAdapter(adapter);
        }
    }
//
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
