// IMusicAidlInterface.aidl
package com.dht.database.bean.music;

// Declare any non-default types here with import statements
import com.dht.database.bean.music.Music;

interface IMusicAidlInterface {

   void playMusic(in Music music);
   
   void playCurrentMusic();
   
   void initPlayList();
   
   void setPlayType(int type);
   
   void playPause();

   void pause();
   
   void stop();
   
   void playPrevious();
   
   void playNext();
   
   void seekTo(int msec);
   
   boolean isLooping ();
   
   boolean isPlaying();
   
   int position();
   
   int getDuration();
   
   int getCurrentPosition();
   
   List<Music> getPlayList();
   
   void setPlayList(in List<Music> musics);
   
   Music getCurrentMusic();
   
   void removeFromQueue(int position);
   
   void clearQueue();
   
   void showDesktopLyric(boolean show);
   
   int audioSessionId();
   
   void release () ;
}
