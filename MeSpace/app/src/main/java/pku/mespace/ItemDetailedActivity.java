package pku.mespace;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pku.db.DBManager;
import pku.db.ItemInfo;


public class ItemDetailedActivity extends ActionBarActivity implements View.OnClickListener{

    private ImageView backBtn;
    private ImageView editBtn;
    private EditText usernameET;
    private EditText passwordET;
    private TextView itemnameET;

    private DBManager mgr;

    private ItemInfo originalItem;

    private int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature( Window.FEATURE_NO_TITLE );*/
        setContentView(R.layout.item_detailed);

        backBtn = (ImageView)findViewById(R.id.backBtn_item_detail);
        backBtn.setOnClickListener(this);
        editBtn = (ImageView)findViewById(R.id.editBtn_item_detail);
        editBtn.setOnClickListener(this);

        usernameET = (EditText)findViewById(R.id.username_item_detail);
        passwordET = (EditText)findViewById(R.id.password_item_detail);
        itemnameET = (TextView)findViewById(R.id.itemName_item_detail);

        originalItem = (ItemInfo)this.getIntent().getSerializableExtra(HomeActivity.SER_KEY);
        itemnameET.setText(originalItem.getItemName());
        usernameET.setText(originalItem.getUserName());
        passwordET.setText(originalItem.getPassWord());

        mgr =  new DBManager(this);
    }

    /**
     * 返回按钮！！！
     * @param view
     */
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.backBtn_item_detail){
            Intent i = new Intent(ItemDetailedActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
        if(view.getId() == R.id.editBtn_item_detail){
            if(clickCount%2 == 0) {
                //更改按钮图标
                editBtn.setImageResource(R.drawable.save);
                itemnameET.requestFocus();
                itemnameET.setFocusableInTouchMode(true);
                usernameET.setFocusableInTouchMode(true);
                //usernameET.setFocusable(true); ---这个没用
                passwordET.setFocusableInTouchMode(true);
                this.clickCount++;
            }else{
                this.clickCount++;
                String itemname="", username="", password="",filename="";
                itemname = itemnameET.getText().toString();
                username = usernameET.getText().toString();
                password = passwordET.getText().toString();
                filename = originalItem.getFileName();
                //Log.d("test"," 修改前:   "+originalItem.getItemName()+" "+ originalItem.getUserName()+" "+originalItem.getPassWord()+" "+originalItem.getFileName()+" "+originalItem.getId());
                //Log.d("test"," 修改后:   "+itemname+" "+ username+" "+password+" "+filename+" "+originalItem.getId());
                ItemInfo item = new ItemInfo();
                item.setId(originalItem.getId());
                item.setItemName(itemname);
                item.setUserName(username);
                item.setPassWord(password);
                item.setFileName(filename);
                mgr.updateItem(item);
                Toast.makeText(ItemDetailedActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ItemDetailedActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }

    }

    //===============================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_detailed, menu);
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
