package com.hudson.codes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution49 {
    // 时间超过：29.93%，空间：33.29%，效率这么低，我擦嘞
    // 最初的思路肯定是对所有单词按字典排序，然后比对，这里不予考虑
    // 分析：
    // 像这种比重的题目，最快想到的就是类Map来比较
    // 如果每个单词中没有重复的字母的话，我们可以使用一个int数来映射
    // 每个单词的结构，每一位1表示该字母出现了，这样最终是可以比较int数
    // 判断是否是相同的。然鹅，本题没有说单词字符不会重复出现，因此不能这样做
    // 既然这样不行，我们继续考虑映射成别的，并且可以区分有重复字母的单词的方法呢？
    // 既然可能某个字符多次出现，就不能再以1和0区分某个字符了，我们就可以把
    // 字符和它出现的次数拼接起来，并且所有拼接字符按照字典顺序再拼起来，这样比对字符串
    public static List<List<String>> groupAnagrams(String[] strs) {
        if(strs == null || strs.length == 0) return new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            // 先计算出字母出现次数，并存储到一个长度为26的数组中
            int[] counts = new int[26];
            for (int i = 0; i < str.length(); i++) {
                counts[str.charAt(i) - 'a']++;
            }
            // 然后拼接出最终的映射结果
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                if(counts[i] > 0){
                    // 有该字母存在则拼接
                    sb.append('a' + i).append(counts[i]);
                }
            }
            // 最后把结果存入一个Map中，在此之前，需要判断Map是否已经存在该映射形式的结果
            String targetKey = sb.toString();
            if(map.containsKey(targetKey)){
                // 已经有了，就直接往List增加
                map.get(targetKey).add(str);
            }else{
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(targetKey, list);
            }
        }
        List<List<String>>  result = new ArrayList<>();
        // 最后，转成最终结果
        for (String s : map.keySet()) {
            result.add(map.get(s));
        }
        return result;
    }
}