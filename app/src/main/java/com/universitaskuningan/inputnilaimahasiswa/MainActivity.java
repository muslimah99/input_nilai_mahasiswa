package com.universitaskuningan.inputnilaimahasiswa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper mydb;
    ArrayList<String> array_list;
    String kelas;
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    ListView obj;
    TextView jml;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Menampilkan jumlah data
        mydb = new DBHelper(this);
        jml = findViewById(R.id.jml_data);
        jml.setText("Jumlah Data : " + mydb.numberOfRows());

        //Membuat spinner/combo box
        spinner = findViewById(R.id.pilihKelas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kelas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Menampilkan semua data
        array_list = mydb.getAllData();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_list);
        obj = findViewById(R.id.listView);
        obj.setAdapter(arrayAdapter);

        //Menampilkan data berdasarkan kelas
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                kelas = spinner.getSelectedItem().toString();
                if(kelas.equals("Semua"))
                    array_list = mydb.getAllData();
                else
                    array_list = mydb.getSomeData(kelas);
                jml.setText("Jumlah Data : " + array_list.size());
                arrayAdapter.clear();
                arrayAdapter.addAll(array_list);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        //Menampilkan rincian nilai
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //TODO Auto-generated method stub
                String data = array_list.get(arg2);
                String id_To_Search = data.substring(0, data.indexOf(" "));
                Bundle dataBundle = new Bundle();
                dataBundle.putString("nim", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), DisplayNilai.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    //Membuat menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Ketika klik menu tambah data
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.tambah_data) {
            Bundle dataBundle = new Bundle();
            dataBundle.putString("nim", "");
            Intent intent = new Intent(getApplicationContext(), DisplayNilai.class);
            intent.putExtras(dataBundle);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Ketika klik menu kembali
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}