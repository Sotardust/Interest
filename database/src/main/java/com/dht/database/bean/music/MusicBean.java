package com.dht.database.bean.music;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Music 与MusicBean 实质上是一样的，只是为了避免MusicDao之中引用aidl中的MusicBean 遂用Music代替MusicBean
 *
 * created by dht on 2019/1/10 16:55
 *
 * @author Administrator
 */
@Entity(tableName = "music")
public class MusicBean {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    /**
     * 歌名
     */
    @ColumnInfo(name = "name")
    private String name;

    /**
     * 歌曲路径
     */
    @ColumnInfo(name = "path")
    private String path;

    /**
     * 歌手
     */
    @ColumnInfo(name = "author")
    private String author;

    /**
     * 歌曲对应图像
     */
    @ColumnInfo(name = "avatar")
    private String avatar;

    /**
     * 歌词
     */
    @ColumnInfo(name = "lyrics")
    private String lyrics;

    /**
     * 歌曲类型
     */
    @ColumnInfo(name = "type")
    private String type;

    public MusicBean() {

    }


    /**
     * 转换成Music 实体类
     *
     * @return Music
     */
    public Music toMusic() {
        Music music = new Music();
        music.setId(id);
        music.setAuthor(author);
        music.setAvatar(avatar);
        music.setLyrics(lyrics);
        music.setName(name);
        music.setPath(path);
        music.setType(type);
        return music;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", author='" + author + '\'' +
                ", avatar='" + avatar + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
