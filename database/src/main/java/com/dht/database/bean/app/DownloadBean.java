package com.dht.database.bean.app;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * created by dht on 2019/3/11 18:27
 */
@Entity(tableName = "download")
public class DownloadBean {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    /*歌名*/
    @ColumnInfo(name = "name")
    public String name;

    /*歌曲路径*/
    @ColumnInfo(name = "path")
    public String path;

    /*歌手*/
    @ColumnInfo(name = "author")
    public String author;


    /*下载状态：0：下载中 ，1：暂停 ，2：完成 */
    @ColumnInfo(name = "state")
    public int state;


}
