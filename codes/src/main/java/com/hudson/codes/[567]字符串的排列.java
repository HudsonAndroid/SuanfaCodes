package com.hudson.codes;//给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
//
// 换句话说，第一个字符串的排列之一是第二个字符串的子串。 
//
// 示例1: 
//
// 
//输入: s1 = "ab" s2 = "eidbaooo"
//输出: True
//解释: s2 包含 s1 的排列之一 ("ba").
// 
//
// 
//
// 示例2: 
//
// 
//输入: s1= "ab" s2 = "eidboaoo"
//输出: False
// 
//
// 
//
// 注意： 
//
// 
// 输入的字符串只包含小写字母 
// 两个字符串的长度都在 [1, 10,000] 之间 
// 
// Related Topics 双指针 Sliding Window 
// 👍 200 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution567 {
    // 滑动窗口
    // 每次移动一格，比较的长度是s1的长度，通过对比两个统计个数数组，数值相同，则说明选中的s2滑块与s1的全排列相同
    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;
        int[] s1map = new int[26];
        int[] s2map = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            s1map[s1.charAt(i) - 'a']++;
            s2map[s2.charAt(i) - 'a']++;
        }
        // 循环的终止条件就是已经到达s2的尾部
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            if (matches(s1map, s2map))
                return true;
            s2map[s2.charAt(i + s1.length()) - 'a']++; // 增加一个新的滑块，并计算字符计数
            s2map[s2.charAt(i) - 'a']--; //把原始滑块的头一个元素移除（恢复该位置之前字符计数）
        }
        return matches(s1map, s2map);
    }

    // 比较字符分布图
    public static boolean matches(int[] s1map, int[] s2map) {
        for (int i = 0; i < 26; i++) {
            if (s1map[i] != s2map[i])
                return false;
        }
        return true;
    }

    //==============================自己练习的代码====================
    public static boolean checkInclusion2(String s1, String s2) {
        if(s1 == null || s2 == null || s1.length() > s2.length()){
            return false;
        }
        int[] targetMap = new int[26];
        int[] sideMap = new int[26];
        // 计算字符个数分布图
        for (int i = 0; i < s1.length(); i++) {
            targetMap[s1.charAt(i) - 'a'] ++;
            sideMap[s2.charAt(i) - 'a'] ++;
        }
        // 开始滑动滑块
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            if(isSame(targetMap, sideMap)){
                return true;
            }
            // 继续滑动
            // 先添加元素
            sideMap[s2.charAt(i + s1.length()) - 'a']++;
            sideMap[s2.charAt(i) - 'a'] --;
        }
        return isSame(targetMap, sideMap);
    }

    private static boolean isSame(int[] map1, int[] map2){
        if(map1.length == map2.length){
            for (int i = 0; i < map1.length; i++) {
                if(map1[i] != map2[i]){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
