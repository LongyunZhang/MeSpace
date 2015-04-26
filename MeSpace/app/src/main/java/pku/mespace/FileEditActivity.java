package pku.mespace;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pku.db.DBManager;
import pku.db.FileInfo;


public class FileEditActivity extends ActionBarActivity {
    Spinner sp;
    EditText et;
    Button bt;
    ArrayList<String> fileNameList;
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);
        sp = (Spinner)findViewById(R.id.editFileSpinner);
        Bundle bd = this.getIntent().getExtras();
        fileNameList =bd.getStringArrayList("yw");
        adapter = new ArrayAdapter<String>(FileEditActivity.this,android.R.layout.simple_spinner_dropdown_item,fileNameList);
        sp.setAdapter(adapter);
       final DBManager db = new DBManager(this);
        et = (EditText)findViewById(R.id.editFileEt);
        bt = (Button)findViewById(R.id.editFileEdit);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFileName = et.getText().toString();
                String oldFileName = sp.getItemAtPosition(sp.getSelectedItemPosition()).toString();
                Log.v("db_oldFileName",oldFileName);
                db.updataFile(newFileName,oldFileName);
                Intent i = new Intent(FileEditActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
