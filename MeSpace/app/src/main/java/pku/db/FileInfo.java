package pku.db;

/**
 * Created by Zhang on 2015/3/30.
 */
public class FileInfo {
    private int id;
    private String fileName;

    public FileInfo(){}
    public FileInfo(String fileName){
        this.fileName = fileName;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
