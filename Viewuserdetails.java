package com.creditmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Viewuserdetails extends AppCompatActivity {

    Databasehelper mydb;
    String username;

    TextView id,name,email,credits;
    Button btn,transferbtn;
    Spinner spinner;
    EditText editText;
    View mview;

    ArrayList<String> listitem;
    ArrayAdapter adapter;
    int tcredits;
    int tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuserdetails);

        mydb = new Databasehelper(this);
        listitem = new ArrayList<>();

        username = getIntent().getStringExtra("name").toString();

        id = (TextView)findViewById(R.id.id);
        name = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);
        credits = (TextView)findViewById(R.id.credits);

        btn = (Button) findViewById(R.id.transfercredit);

        viewuserdata(username);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Viewuserdetails.this);
                    spinner();
                    transferbtn = (Button)mview.findViewById(R.id.transferbutton);
                    editText = (EditText) mview.findViewById(R.id.transfercredits);
                    transferbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = spinner.getSelectedItem().toString();
                            int transfercredits = Integer.parseInt(editText.getText().toString());
                            if(!TextUtils.isEmpty(editText.getText().toString())) {
                                    Cursor cursor = mydb.getUserData(text);
                                    while (cursor.moveToNext()) {
                                        tid = cursor.getInt(0);
                                        tcredits = cursor.getInt(3);
                                    }
                                tcredits = tcredits+transfercredits;
//                                    Toast.makeText(getApplicationContext(),"enter credits"+tcredits,Toast.LENGTH_LONG).show();
                                updatecredits(tid,tcredits);
                            }
//                            else
//                            {
//                                Toast.makeText(getApplicationContext(),"enter credits",Toast.LENGTH_LONG).show();
//                            }
                        }
                    });
                builder.setView(mview);
                builder.show();
            }
        });

    }

    private void updatecredits(int id,int credits) {
        mydb.update(id,credits);
        Toast.makeText(getApplicationContext(),"credits "+credits,Toast.LENGTH_LONG).show();
    }

    private void spinner() {
        mview = getLayoutInflater().inflate(R.layout.credittransfer,null);
        spinner = (Spinner)mview.findViewById(R.id.spinner);
        Cursor cursor = mydb.getAllData();
        if(!listitem.isEmpty()) {
            listitem.clear();
        }
        while (cursor.moveToNext()) {
            listitem.add(cursor.getString(1));
        }
        adapter = new ArrayAdapter<String>(Viewuserdetails.this, android.R.layout.simple_spinner_item, listitem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void viewuserdata(String username) {

        Cursor cursor = mydb.getUserData(username);
        if(cursor.getCount() == 0) {
            return;
        }
        else {
            while(cursor.moveToNext())
            {
                id.setText(cursor.getString(0));
                name.setText(cursor.getString(1));
                email.setText(cursor.getString(2));
                credits.setText(cursor.getString(3));

            }
        }
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),Viewdata.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
