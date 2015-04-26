package pku.db;

import java.io.Serializable;

/**
 * Created by Zhang on 2015/3/30.
 */
public class ItemInfo implements Serializable {
    private int id; //编号
    private String itemName;//条目名称
    private String userName;//用户名
    private String passWord;//密码
    private String fileName; //条目所属文件夹名称


   /* private String remark;  //备注
    private Timestamp lastEditName;//最后修改时间
    private int itemType;    //条目类别
    private int IdNumber;   //身份证号
    private String site;    //网址
    private String bank;    //开户银行*/

    public ItemInfo(){};
    public ItemInfo(String itemName, String userName, String passWord, String fileName){
        this.itemName = itemName;
        this.userName = userName;
        this.passWord = passWord;
        this.fileName = fileName;
    }

    /*public ItemInfo(String itemName, String userName, String passWord,String fileName){
        this.itemName = itemName;
        this.userName = userName;
        this.passWord = passWord;
        this.fileName = fileName;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
