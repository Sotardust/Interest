package com.dht.database.bean.music;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 最近播放歌曲实体类
 * <p>
 * created by dht on 2019/1/17 15:06\
 *
 * @author Administrator
 * @Entity(tableName = "recent_play", indices = {@Index("recent_id")})
 */

@Entity(tableName = "recent_play", indices = {@Index("id")})
public class RecentPlayBean {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recent_id")
    private int id;

    /**
     * 歌曲名称
     */
    @ColumnInfo(name = "song_name")
    private String songName;

    /**
     * 用户id
     */
    @ColumnInfo(name = "person_id")
    private long personId;

    /**
     * 播放总次数
     */
    @ColumnInfo(name = "play_total")
    private int playTotal;

    /**
     * 歌曲最近播放时间名称
     */
    @ColumnInfo(name = "play_time")
    private long playTime;


    /**
     * 最近播放次数
     */
    @ColumnInfo(name = "recent_play_count")
    private int playCount;

    @Embedded()
    private MusicBean music;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public int getPlayTotal() {
        return playTotal;
    }

    public void setPlayTotal(int playTotal) {
        this.playTotal = playTotal;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public MusicBean getMusic() {
        return music;
    }

    public void setMusic(MusicBean music) {
        this.music = music;
    }
}
