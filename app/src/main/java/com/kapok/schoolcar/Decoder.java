package com.kapok.schoolcar;

/**
 * Created by WangChangan on 2015/4/23 0023.
 */
public class Decoder {

    public static String translate(String string){
        if(string.contains("兴隆山校区")||string.contains("南山小区")){
            return "X";
        }else{
            if(string.contains("千佛山校区")){
                return "Q";
            }else{
                if(string.contains("中心校区")){
                    return "Z";
                }else{
                    if(string.contains("趵突泉校区")){
                        return "B";
                    }else{
                        if(string.contains("洪家楼校区")||string.contains("五宿舍")){
                            return "H";
                        }else{
                            return "R";
                        }
                    }
                }
            }
        }
    }

}
