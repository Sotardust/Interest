package com.dht.database.bean.music;


import android.os.Parcel;
import android.os.Parcelable;


/**
 * Music 与MusicBean 实质上是一样的，只是为了避免MusicDao之中引用aidl中的MusicBean 遂用Music代替MusicBean
 * <p>
 * created by dht on 2019/1/10 16:55
 *
 * @author Administrator
 */

public class Music implements Parcelable {

    private int id;

    /**
     * 歌名
     */
    private String name;

    /**
     * 歌曲路径
     */
    private String path;

    /**
     * 歌手
     */
    private String author;

    /**
     * 歌曲对应图像
     */
    private String avatar;

    /**
     * 歌词
     */
    private String lyrics;

    /**
     * 歌曲类型
     */
    private String type;

    public Music() {

    }


    /**
     * 转换成MusicBean 实体类
     *
     * @return MusicBean
     */
    public MusicBean toMusicBean() {
        MusicBean bean = new MusicBean();
        bean.setId(id);
        bean.setAuthor(author);
        bean.setAvatar(avatar);
        bean.setLyrics(lyrics);
        bean.setName(name);
        bean.setPath(path);
        bean.setType(type);
        return bean;
    }

    private Music(Parcel in) {
        id = in.readInt();
        name = in.readString();
        path = in.readString();
        author = in.readString();
        avatar = in.readString();
        lyrics = in.readString();
        type = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(author);
        dest.writeString(avatar);
        dest.writeString(lyrics);
        dest.writeString(type);
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
