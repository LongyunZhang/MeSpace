package pku.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhang on 2015/3/30.
 */

//*************封装我们所有的业务方法****************
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        //实例化DBHelper
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了context.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        //getWritableDatabase()：并获取一个SQLiteDatabase对象，作为整个应用的数据库实例！！！
        //当数据库不存在的时候，会调用onCreate()，进行创建数据库，及初始化表的操作
        db = helper.getWritableDatabase();
    }

    public List<FileInfo> queryAllFile() {
        ArrayList<FileInfo> files = new ArrayList<FileInfo>();
        Cursor c = queryTheCursor(1);
        while (c.moveToNext()) {
            FileInfo file = new FileInfo();
            file.setId(c.getInt(c.getColumnIndex("id")));
            file.setFileName(c.getString(c.getColumnIndex("fileName")));
            files.add(file);
        }
        c.close();
        return files;
    }

    public List<ItemInfo> queryAllItem() {
        ArrayList<ItemInfo> items = new ArrayList<ItemInfo>();
        Cursor c = queryTheCursor(2);
        while (c.moveToNext()) {
            ItemInfo item = new ItemInfo();
            item.setId(c.getInt(c.getColumnIndex("id")));
            item.setItemName(c.getString(c.getColumnIndex("itemName")));
            item.setUserName(c.getString(c.getColumnIndex("userName")));
            item.setPassWord(c.getString(c.getColumnIndex("passWord")));
            item.setFileName(c.getString(c.getColumnIndex("fileName")));
            items.add(item);
        }
        c.close();
        return items;
    }

    public ItemInfo queryFromItemname(String itemname) {
        Cursor c = db.rawQuery("SELECT * FROM item_table where itemName = ?", new String[]{itemname});
        ItemInfo item = new ItemInfo();
        while (c.moveToNext()) {
            item.setId(c.getInt(c.getColumnIndex("id")));
            //         c.getInt(0) : //获取第一列的值
            item.setItemName(c.getString(c.getColumnIndex("itemName")));
            //         c.getString(1) : //获取第二列的值
            item.setUserName(c.getString(c.getColumnIndex("userName")));
            item.setPassWord(c.getString(c.getColumnIndex("passWord")));
            item.setFileName(c.getString(c.getColumnIndex("fileName")));
        }
        c.close();
        return item;
    }

    public Cursor queryTheCursor(int type) {
        Cursor c = null;
        if(type == 1) {
            c = db.rawQuery("SELECT * FROM file_table", null);
        }
        if(type == 2) {
            c = db.rawQuery("SELECT * FROM item_table", null);
        }
        return c;
    }

    public void addItem(ItemInfo item){
        try {
            db.execSQL("INSERT INTO item_table VALUES(null, ?, ?, ?, ?)", new Object[]{item.getItemName(),item.getUserName(),item.getPassWord(),item.getFileName()});
        }finally {
            Log.d("sql","add item failed");
        }
    }
    public void addFile(FileInfo file){
        try {
            db.execSQL("INSERT INTO file_table VALUES(null, ?)", new Object[]{file.getFileName()});
        }finally {
            Log.d("sql","add file failed");
        }
    }

    public void addItems(List<ItemInfo> items) {
        //在添加多个item信息时，我们采用了事务处理，确保数据完整性！！！
        db.beginTransaction();  //开启事务
        try {
            for (ItemInfo item : items) {
                db.execSQL("INSERT INTO file_table VALUES(null, ?)", new Object[]{"文件夹5"});
                db.execSQL("INSERT INTO item_table VALUES(null, ?, ?, ?, ?)", new Object[]{"条目5-1","taobao","taobao","文件夹5"});
            }
            db.setTransactionSuccessful();//调用此方法会在执行到endTransaction()时提交当前事务，如果不调用此方法会回滚事务
        } finally {
            Log.d("sql","add items failed");
            db.endTransaction();    //由事务的标志决定是提交事务，还是回滚事务
        }
    }


    public void updateItem(ItemInfo item) {
        /*try {
            Log.d("test"," updateItem():   "+item.getItemName()+" "+ item.getUserName()+" "+item.getPassWord()+" "+item.getFileName()+" "+item.getId());
            db.execSQL("update item_table set itemName=? and userName=? and passWord=? and fileName=? where id=?",new Object[]{item.getItemName(),item.getUserName(),item.getPassWord(),item.getFileName(),item.getId()} );
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Log.d("sql","update item failed...");
        }*/
        ContentValues cv = new ContentValues();
        cv.put("itemName", item.getItemName());
        cv.put("userName", item.getUserName());
        cv.put("passWord", item.getPassWord());
        cv.put("fileName", item.getFileName());
        db.update("item_table", cv, "id=?", new String[]{String.valueOf(item.getId())});
    }
    public void updataFile(String fileNewName,String fileOldName){
        db.execSQL("update file_table set fileName = ? where fileName = ?",new Object[]{fileNewName,fileOldName});
        db.execSQL("update item_table set fileName = ? where fileName = ?",new Object[]{fileNewName,fileOldName});
    }


    public void deleteItem(ItemInfo item) {

    }
    public void deleteItem(String itemName) {
        try{
            db.execSQL("DELETE FROM item_table WHERE itemName = ? ",new Object[]{itemName});
        }finally {
            Log.d("sql","delete item failed");
        }
    }
    public void deleteFile(String fileName) {
        try{
            db.execSQL("DELETE FROM file_table WHERE fileName = ? ",new Object[]{fileName});
        }finally {
            Log.d("sql","delete file failed");
        }
    }

    /**
     * 释放数据库资源
     */
    public void closeDB() {
        db.close();
    }
}
