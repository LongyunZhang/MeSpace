package pku.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zhang on 2015/3/30.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mespace.db"; //数据库名称
    private static final int DATABASE_VERSION = 1; //假设初始数据库版本是1

    public DBHelper(Context context) {
        //第3个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时，onCreate会被调用！
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*db.execSQL("CREATE TABLE IF NOT EXISTS item_table(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "itemName VARCHAR,userName VARCHAR,passWord VARCHAR,remark VARCHAR,lastEditTime DATETIME," +
                "itemType VARCHAR,person_id INT,site VARCHAR,bank VARCHAR)");*/
        //创建file_table
        db.execSQL("CREATE TABLE IF NOT EXISTS file_table(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fileName VARCHAR)");
        //创建item_table
        db.execSQL("CREATE TABLE IF NOT EXISTS item_table(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "itemName VARCHAR,userName VARCHAR,passWord VARCHAR,fileName VARCHAR)");

        db.execSQL("INSERT INTO file_table VALUES(null, ?)", new Object[]{"文件夹1"});
        db.execSQL("INSERT INTO item_table VALUES(null, ?, ?, ?, ?)", new Object[]{"条目1-1","zhly","mima","文件夹1"});
        db.execSQL("INSERT INTO item_table VALUES(null, ?, ?, ?, ?)", new Object[]{"条目1-2","yanwei","hiha","文件夹1"});

        db.execSQL("INSERT INTO file_table VALUES(null, ?)", new Object[]{"文件夹2"});
        db.execSQL("INSERT INTO item_table VALUES(null, ?, ?, ?, ?)", new Object[]{"条目2-1","caoyu","mima","文件夹2"});
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       //执行对数据库表结构更新的操作
       // db.execSQL("ALTER TABLE file_table ADD COLUMN other STRING");
       // db.execSQL("ALTER TABLE item_table ADD COLUMN other STRING");
    }
}
