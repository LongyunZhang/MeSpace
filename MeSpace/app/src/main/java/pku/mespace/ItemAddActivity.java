package pku.mespace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import pku.db.DBManager;
import pku.db.ItemInfo;


public class ItemAddActivity extends ActionBarActivity implements View.OnClickListener{

    private ImageView backBtn;
    private ImageView addBtn;
    private EditText itemnameET;
    private EditText usernameET;
    private EditText passwordET;
    private EditText filenameET;
    private DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_add);

        backBtn = (ImageView)findViewById(R.id.backBtn_item_add);
        backBtn.setOnClickListener(this);
        addBtn = (ImageView)findViewById(R.id.addBtn_item_add);
        addBtn.setOnClickListener(this);
        itemnameET = (EditText)findViewById(R.id.itemName_item_add);
        usernameET = (EditText)findViewById(R.id.username_item_add);
        passwordET = (EditText)findViewById(R.id.password_item_add);
        filenameET = (EditText)findViewById(R.id.filename_item_add);
        db = new DBManager(this);
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.backBtn_item_add){
            Intent i = new Intent(ItemAddActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
        if(view.getId() == R.id.addBtn_item_add){
            ItemInfo item = new ItemInfo();
            Log.d("test"," 新增:   "+itemnameET.getText().toString()+" "+ usernameET.getText().toString()+" "+passwordET.getText().toString()+" "+filenameET.getText().toString());
            item.setItemName(itemnameET.getText().toString());
            item.setUserName(usernameET.getText().toString());
            item.setPassWord(passwordET.getText().toString());
            item.setFileName(filenameET.getText().toString());
            db.addItem(item);
            Toast.makeText(ItemAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ItemAddActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

//--------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
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
