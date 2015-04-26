package pku.mespace;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import pku.db.DBManager;
import pku.db.FileInfo;


public class FileAddActivity extends ActionBarActivity implements View.OnClickListener{

    private ImageView backBtn;
    private ImageView commitBtn;
    private EditText filenameET;
    private DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_add);

        backBtn = (ImageView)findViewById(R.id.backBtn_file_add);
        backBtn.setOnClickListener(this);
        commitBtn = (ImageView)findViewById(R.id.commitBtn_file_add);
        commitBtn.setOnClickListener(this);
        filenameET = (EditText)findViewById(R.id.filename_file_add);
        db = new DBManager(this);

    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.backBtn_file_add){
            finish();
        }
        if(view.getId() == R.id.commitBtn_file_add){
            String filename = filenameET.getText().toString();
            FileInfo file = new FileInfo(filename);
            db.addFile(file);
            Intent i = new Intent(FileAddActivity.this, HomeActivity.class);
            startActivity(i);
            Toast.makeText(FileAddActivity.this, "文件夹添加成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //=====================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_add, menu);
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
