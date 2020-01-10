package com.dht.database.bean.music;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Administrator
 */
@Entity(tableName = "cloud_music")
public class CloudMusicBean {

    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * 人员ID
     */
    @ColumnInfo(name = "person_id")
    public int personId;
    /**
     * 音乐对应唯一ID
     */
    @ColumnInfo(name = "music_id")
    public long musicId;
    /**
     * 音乐名称
     */
    @ColumnInfo(name = "name")
    public String name;
    /**
     * 存放路径
     */
    @ColumnInfo(name = "path")
    public String path;
    /**
     * 音乐类型
     */
    @ColumnInfo(name = "type")
    public String type;
    /**
     * 音乐时长
     */
    @ColumnInfo(name = "duration")
    public int duration;
}
