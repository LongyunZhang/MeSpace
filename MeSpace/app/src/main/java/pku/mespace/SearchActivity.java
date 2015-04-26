package pku.mespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pku.db.DBManager;
import pku.db.ItemInfo;


public class SearchActivity extends Activity implements View.OnClickListener {

    private EditText mEditText;
    private ImageView mBackBtn;
    private ListView lv;
    private List<ItemInfo> itemList = new ArrayList<ItemInfo>();
    private EditTextListViewAdapter adapter;
    //数据库操作
    private DBManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        mBackBtn = (ImageView) findViewById(R.id.backBtn_search);
        mBackBtn.setOnClickListener(this);


        mEditText = (EditText) findViewById(R.id.et_search);
        //给EditText设置监听
        mEditText.addTextChangedListener(mTextWatcher);

        //创建adapter对象，并为ListView设置Adapter来绑定数据
       /* adapter = new EditTextListViewAdapter(this, itemList);
        lv.setAdapter(adapter);*/


        lv = (ListView) findViewById(R.id.lv_search);
        //给listview注册点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //parent：当前ListView，view： 代表当前被点击的条目，position：当前条目的位置，id 当前被点击的条目的id
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取 点击条目 的内容
                TextView tv = (TextView) view.findViewById(R.id.tv_data_search);
                String data = (String) tv.getText();
                Toast.makeText(SearchActivity.this, data, Toast.LENGTH_SHORT).show();
               /* String cityNumber = getNumFromName(cityInfo);
                Intent i = new Intent();
                i.putExtra("cityNum", cityNumber);
                setResult(RESULT_OK, i);
                finish();*/
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backBtn_search) {
            finish();
        }
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        //==========文字变化前=========
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        // ==========文字变化时=========
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            itemList.clear();
            if (mEditText.getText() != null) {
                String str = mEditText.getText().toString();
                Log.d("Edit text Change------", str);
                //根据input_info获取数据库内容！！！
                itemList = getSearchData(str);
                Log.d("Data size------ ", "  " + itemList.size());
                //重新绑定adapter
                if(itemList.size() >= 1){
                    adapter = new EditTextListViewAdapter(SearchActivity.this, itemList);
                    lv.setAdapter(adapter);
                }
            }
        }

        // ==========文字变化后=========
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private List<ItemInfo> getSearchData(String inputStr) {
        Log.d("abc------",inputStr);
        //return mgr.queryLikeItemname(inputStr);
        return itemList;
    }


    //----------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
