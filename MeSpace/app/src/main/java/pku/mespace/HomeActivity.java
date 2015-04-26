package pku.mespace;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import pku.db.DBManager;
import pku.db.FileInfo;
import pku.db.ItemInfo;


public class HomeActivity extends ActionBarActivity implements View.OnClickListener,
        ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener, SearchView.OnQueryTextListener {

    private ListView lv;
    private ListView lvForSearch;
    private SearchView sv;
    private ImageView addFileBtn;
    private Button addItemBtn;
    private Button editFileBtn;
    private ExpandableListView expandableListView;


    private ArrayList<FileInfo> fileList; //保存所有 “文件”信息
    private ArrayList<ItemInfo> itemList; //保存所有 “条目”信息

    private ArrayList<String> fileNameList = new ArrayList<String>(); //父组——保存所有 文件 名称
    private ArrayList<List<String>> childList = new ArrayList<List<String>>(); //子组——

    //adapter
    private MyexpandableListAdapter adapter;
    //数据库操作
    private DBManager mgr;

    //序列化，作为 key 用
    public final static String SER_KEY = "com.zhly.ser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        init();

        //初始化ExpandableListView数据
        initData();
        //创建adapter
        adapter = new MyexpandableListAdapter(this);
        //绑定adapter
        expandableListView.setAdapter(adapter);
        //设置监听(fileList + itemList)
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        //对父组 开闭的监听
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < fileNameList.size(); i++) {
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
        //注册上下文菜单
        registerForContextMenu(expandableListView);
        ArrayList<String>itemNameForFind=new ArrayList<String>();
        lvForSearch = (ListView)findViewById(R.id.lv);
        sv = (SearchView)findViewById(R.id.sv);
        //设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(this);
        //设置该SearchView显示搜索按钮
        sv.setSubmitButtonEnabled(true);
        //设置该SearchView内默认显示的提示文本
        sv.setQueryHint("查找条目");
        Iterator<ItemInfo> it = itemList.iterator();
        while (it.hasNext()){
            String T = it.next().getItemName();
            itemNameForFind.add(T);
        }
        ArrayAdapter myad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemNameForFind);
        lvForSearch.setTextFilterEnabled(true);
        lvForSearch.setAdapter(myad);
        lvForSearch.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        //menuinfo该对象提供了选中对象的附加信息

        final int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        final int group = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        final int child = ExpandableListView.getPackedPositionChild(info.packedPosition);

        if (type == 0)
        {
            new AlertDialog.Builder(this)
                    .setTitle("")
                    .setMessage("确定删除文件夹?（这样您文件下的所有条目也会一并删除）")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialoginterface, int i){
                                    //按钮事件
                                    fileNameList.remove(group);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(HomeActivity.this, "已成功删除",Toast.LENGTH_LONG).show();
                                }
                            }).
                    setNegativeButton("取消",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialoginterface, int i){
                                    //按钮事件

                                    Toast.makeText(HomeActivity.this, "已取消",Toast.LENGTH_LONG).show();
                                }
                            }).show();
            //Toast.makeText(HomeActivity.this,"lalallalal",Toast.LENGTH_SHORT).show();
        }
        if (type == 1)
        {
            //menu.add(0,0,0,"增加");
            Log.v("ttt", fileNameList.toString());
            new AlertDialog.Builder(this)
                    .setTitle("")
                    .setMessage("确定删除条目?")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialoginterface, int i){
                                    //按钮事件

                                    mgr.deleteItem(childList.get(group).get(child));
                                    childList.get(group).remove(child);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(HomeActivity.this, "已成功删除条目",Toast.LENGTH_SHORT).show();
                                }
                            }).
                    setNegativeButton("取消",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialoginterface, int i){
                                    //按钮事件

                                    Toast.makeText(HomeActivity.this, "已取消",Toast.LENGTH_LONG).show();
                                }
                            }).show();

           // Toast.makeText(HomeActivity.this,"长按按钮",Toast.LENGTH_SHORT).show();
        }

    }

    private void init() {
        addFileBtn = (ImageView) findViewById(R.id.addFileBtn);
        addFileBtn.setOnClickListener(this);
        addItemBtn = (Button)findViewById(R.id.addItemBtn);
        editFileBtn = (Button)findViewById(R.id.editFileBtn);
        editFileBtn = (Button)findViewById(R.id.editFileBtn);
        addItemBtn.setOnClickListener(this);
        editFileBtn .setOnClickListener(this);
        //初始化DBManager!!!
        mgr = new DBManager(this);
        //获取 ExpandableListView 对象
        expandableListView = (ExpandableListView) findViewById(R.id.expandablelist);


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean onQueryTextChange(String newText) {
        // TODO Auto-generated method stub
        if(TextUtils.isEmpty(newText))
        {
            //清楚ListView的过滤
            lvForSearch.setVisibility(View.INVISIBLE);
            lvForSearch.clearTextFilter();
        }
        else
        {
            //使用用户输入的内容对ListView的列表项进行过滤
            lvForSearch.setVisibility(View.VISIBLE);
            lvForSearch.setFilterText(newText);


        }
        return true;
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addFileBtn) {
            Intent i = new Intent(HomeActivity.this, FileAddActivity.class);
            startActivity(i);
        }
        if(view.getId() == R.id.addItemBtn){
            Intent i = new Intent(HomeActivity.this, ItemAddActivity.class);
            startActivity(i);
        }
        if(view.getId() == R.id.editFileBtn){
            Intent i = new Intent(HomeActivity.this,FileEditActivity.class);
            Bundle bd = new Bundle();
            bd.putStringArrayList("yw",fileNameList);
            i.putExtras(bd);
            Log.v("ttt","edit file touched");
            startActivity(i);

        }

    }

    //初始化数据
    void initData() {
        fileList = (ArrayList) mgr.queryAllFile();
        itemList = (ArrayList) mgr.queryAllItem();

        //将文件名称，保存在  fileNameList
        for (int i = 0; i < fileList.size(); i++) {
            fileNameList.add(fileList.get(i).getFileName());
        }
        for (int i = 0; i < fileNameList.size(); i++) {
            ArrayList<String> childTemp = null;
            //判定item属于哪个文件夹，进行绑定！！！
            childTemp = new ArrayList<String>();
            for (int j = 0; j < itemList.size(); j++) {
                if (itemList.get(j).getFileName().equalsIgnoreCase(fileNameList.get(i))) {
                    Log.d("ttt", itemList.get(j).getFileName() + " " + itemList.get(j).getItemName() + "~~~" + fileNameList.get(i));
                    childTemp.add(itemList.get(j).getItemName());
                }
            }
            childList.add(childTemp);
        }
    }

    /**
     * 数据源
     */
    class MyexpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        //作用类似于findViewById()，不同之处：findViewById()是找具体控件
        //LayoutInflater是用来获得布局文件，并实例化
        private LayoutInflater inflater;

        //构造函数, context：当前布局的对象（上下文）
        public MyexpandableListAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        // 返回父列表个数
        @Override
        public int getGroupCount() {
            return fileNameList.size();
        }

        // 返回子列表个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        //返回父group对象
        @Override
        public Object getGroup(int groupPosition) {
            return fileNameList.get(groupPosition);
        }

        //返回子group对象
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        //返回父group 的id
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        //返回子 group 的id
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        //获取子视图
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            //convertView 重用已有的视图对象, 在使用前应检查它是否为空！
            if (convertView == null) {
                groupHolder = new GroupHolder();
                //inflater.inflate(R.layout.group, null) 获取 group.xml 布局文件对象
                convertView = inflater.inflate(R.layout.group, null);
                //获取 父组上的 textView
                groupHolder.textView = (TextView) convertView.findViewById(R.id.group);
                //获取 父组上的 箭头imageView 图标对象
                groupHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                //设置textView的字符长度
                groupHolder.textView.setTextSize(15);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            groupHolder.textView.setText(getGroup(groupPosition).toString());
            //判断父列表是否处于Expanded 展开状态
            if (isExpanded)//展开
                groupHolder.imageView.setImageResource(R.drawable.expanded);
            else    //折叠
                groupHolder.imageView.setImageResource(R.drawable.collapse);
            return convertView;
        }

        //获取子视图
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                //inflater.inflate(R.layout.item, null) 获取 item1.xml 布局文件对象
                convertView = inflater.inflate(R.layout.item, null);
            }
            //获取 子组中 的textView
            TextView textView = (TextView) convertView.findViewById(R.id.item);
            textView.setTextSize(13);
            textView.setText(getChild(groupPosition, childPosition).toString());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    //点击父条目的响应 ---返回true的话，点击父条目，子菜单也不会打开
    @Override
    public boolean onGroupClick(final ExpandableListView parent, final View v, int groupPosition, final long id) {
        return false;
    }


    //点击子条目的响应
    @Override

    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        //Toast.makeText(HomeActivity.this, childList.get(groupPosition).get(childPosition), Toast.LENGTH_SHORT).show();
        String itemname =  childList.get(groupPosition).get(childPosition);
        //根据“条目名称"获取完整的条目信息
        ItemInfo item = mgr.queryFromItemname(itemname);
        //跳转至 条目详细信息页面
        Intent i = new Intent(HomeActivity.this, ItemDetailedActivity.class);
        //绑定
        Bundle mBundle = new Bundle();
        //这句很关键！！！！！
        //第1个参数，key值，
        //第2个参数，对象实例
        mBundle.putSerializable(SER_KEY, item);
        i.putExtras(mBundle);
        startActivity(i); //跳转
        finish();
        return false;
    }

    class GroupHolder {
        TextView textView;
        ImageView imageView;
    }

//------------------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
