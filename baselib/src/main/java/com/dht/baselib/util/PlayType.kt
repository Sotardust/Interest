package com.dht.baselib.util

/**
 * created by dht on 2019/1/14 16:46
 *
 * @author Administrator
 */
enum class PlayType(val index: Int) {
    //顺序播放
    PLAY_IN_ORDER(0),  //单曲循环
    SINGLE_CYCLE(1),  //列表循环
    LIST_LOOP(2),  //随机播放
    SHUFFLE_PLAYBACK(3);

    companion object {
        fun getPlayTypeString(index: Int): String {
            return when (index) {
                0 -> "顺序播放"
                1 -> "单曲循环"
                2 -> "列表循环"
                else -> "随机播放"
            }
        }
    }

}