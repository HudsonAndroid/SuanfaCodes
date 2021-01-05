package com.hudson.codes;//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
//
// 示例 1: 
//
// 输入: "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 输入: "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 输入: "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
// Related Topics 哈希表 双指针 字符串 Sliding Window 
// 👍 4624 👎 0


import java.util.HashMap;

//leetcode submit region begin(Prohibit modification and deletion)
// 考验滑动窗口
class Solution3 {
    // 这道题不能简简单单地认为是HashMap存储字符位置，然后计算差值就可以了
    // 因为像 "abba"这样的，a被重复的b隔开，这样计算出来的a的不重复长度是3
    // 但实际上中间有重复的b
    // 【思路：滑动窗口】
    public static int lengthOfLongestSubstring(String s) {
        if(s != null){
            HashMap<Character, Integer> indexs = new HashMap<>();
            char[] chars = s.toCharArray();
            int maxLength = 0;
            int curLength = 0;
            int start = 0;
            // 错误1： 外界输入" "的情况(仅输入一个字符)下，返回了0
//            if(chars.length > 0){
//                maxLength = 1;// 至少有一个
//            }
            for (int i = 0; i < chars.length; i++) {
                if(indexs.containsKey(chars[i])){
                    // 说明当前存在该key，那么计算下差值
                    int oldPosition = indexs.get(chars[i]);
                    if(oldPosition < start){
                        oldPosition = start;
                    }else{
                        start = oldPosition;
                    }
                    curLength = i - oldPosition; // a - b + 1，由于不包含末尾，因此不用+1
                    maxLength = Math.max(maxLength, curLength);
                }else{
                    //错误2： 没有找到的情况下，maxLength需要自增1
                    curLength ++;
                }
                indexs.put(chars[i], i);// update index position
                // 错误3：如果没有重复字符
                maxLength = Math.max(maxLength, curLength);//如果整条字符串都没有重复的，需要与计算的长度取大值
            }
            return maxLength;
        }
        throw new IllegalArgumentException("The input str cannot be null");
    }

    public static int lengthOfLongestSubstring2(String s){
        if(s != null){
            HashMap<Character,Integer> indexs = new HashMap<>();
            int maxLength = 0;
            int left = 0;
            for (int i = 0; i < s.length(); i++) {
                if(indexs.containsKey(s.charAt(i))){
                    // 根据情况，修改左边界
                    left = Math.max(left, indexs.get(s.charAt(i)) + 1);
                }
                indexs.put(s.charAt(i), i);
                maxLength = Math.max(maxLength, i - left + 1);
            }
            return maxLength;
        }
        throw new IllegalArgumentException("The input str cannot be null");
    }
}
//leetcode submit region end(Prohibit modification and deletion)
