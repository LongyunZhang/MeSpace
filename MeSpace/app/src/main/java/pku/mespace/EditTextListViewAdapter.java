package pku.mespace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pku.db.ItemInfo;

/**
 * Created by Zhang on 2015/4/9.
 */
public class EditTextListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ItemInfo> data;

    //构造函数
    public EditTextListViewAdapter(Context context, List<ItemInfo> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //result_item是条目对应的xml文件
            convertView = inflater.inflate(R.layout.result_item, null);
        }
        //获取条目对象
        TextView textView = (TextView) convertView.findViewById(R.id.tv_data_search);
        //设置条目显示的内容
        String itemName = data.get(position).getItemName();
        //设置条目信息
        textView.setText(itemName);
        return convertView;
    }
}