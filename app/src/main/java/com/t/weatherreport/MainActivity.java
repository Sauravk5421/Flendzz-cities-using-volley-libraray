package com.t.weatherreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List list;
    TextView textView;

    String[] states = {"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
            "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka",
            "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland",
            "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura","Uttar Pradesh",
            "West Bengal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_layout);
        textView = findViewById(R.id.textview);

        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_multiple_choice, states);

        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.states_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        list = new ArrayList<String>();
        if(id==R.id.item_done){
            String itemSelected = "Selected items:  \n";
            for(int i =0;i<listView.getCount();i++){
                if(listView.isItemChecked(i)){
                    itemSelected +=listView.getItemAtPosition(i) + "\n";
                    list.add(listView.getItemAtPosition(i));
                }

            }
            String str1 = list.get(0).toString();
            String str2 = list.get(1).toString();
            String str3 = list.get(2).toString();
            String str4 = list.get(3).toString();
            String str5 = list.get(4).toString();
            Intent intent = new Intent(getApplicationContext(),WeatherActivity.class);
            intent.putExtra("state_name1",str1);
            intent.putExtra("state_name2",str2);
            intent.putExtra("state_name3",str3);
            intent.putExtra("state_name4",str4);
            intent.putExtra("state_name5",str5);
            startActivity(intent);
            Toast.makeText(this,"Selection Done",Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }


}