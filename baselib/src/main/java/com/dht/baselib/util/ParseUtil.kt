package com.dht.baselib.util

/**
 * 歌名解析工具
 *
 *
 * created by dht on 2020/1/6 18:18
 */
object ParseUtil {
    /**
     * 解析歌曲名称
     *
     * @param name 文件名
     * @return 歌名
     */
    fun parseSongName(name: String?): String? {
        if (name == null) {
            return null
        }
        return name.substringBeforeLast(".").substringAfterLast("-").trim()

    }

    /**
     * 解析歌手名
     *
     * @param name 文件名
     * @return 歌手名
     */
    fun parseAuthor(name: String?): String? {
        if (name == null) {
            return null
        }
        return name.substringBeforeLast("-").trim()
    }

    /**
     * 解析歌曲类型
     *
     * @param name 文件名
     * @return 歌曲类型
     */
    fun parseType(name: String?): String? {
        if (name == null) {
            return null
        }
        return name.substringAfterLast(".").trim()
    }
}