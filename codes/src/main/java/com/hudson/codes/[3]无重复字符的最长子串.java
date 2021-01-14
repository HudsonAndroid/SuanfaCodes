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


















    //【复习，发现易错题，复习还是错】
    // 最容易出错的地方：认为找到了重复的，就直接+1替换掉left，其实可能找到的比现在的left还小
    public static int lengthOfLongestSubstring3(String s) {
        if(s.length() == 0) return 0;
        if(s.length() == 1) return 1;
        HashMap<Character, Integer> windowMap = new HashMap<>();
        int left = 0;
        int right = 1;
        windowMap.put(s.charAt(0), 0);
        int maxLen = 1;
        // 需要明确right是一个新的位置，没有加入，记住这个思路
        while(right <= s.length() - 1 && left < right){
            char key = s.charAt(right);
            if(windowMap.get(key) != null){// 记住，找到的key对应的位置可能比现在的left还小
                // 【错误1】如果发现的是当前left之前的相同的位置，我们不能更新，而应该继续使用left

                left = Math.max(windowMap.get(key) + 1, left); // 【错误思路，因为right是新值】如果之前已经存在该值，则更新left，由于必然比上一个长度短，因此不做任何处理
            }
//            else{
//                // 更新值
//            }
            maxLen = Math.max(maxLen, right - left + 1);
            windowMap.put(key, right);
            right++;
        }
        return maxLen;
    }


    //【复习，注意保证map与窗口内容的同步】
    public static int lengthOfLongestSubstring4(String s) {
        if(s.length() == 0) return 0;
        if(s.length() == 1) return 1;
        int left = 0;
        int right = 1;
        HashMap<Character, Integer> map = new HashMap<>();
        int maxLen = 1;
        map.put(s.charAt(0), 0);
        while(right <= s.length() - 1 && left <= right){
            char c = s.charAt(right);
            if(map.containsKey(c)){
                // 【注意】移除旧的元素，从left到旧的c所在的位置，【保证MAP与窗口同步】
                Integer end = map.get(c);
                int start = left;
                while(start <= end){
                    map.remove(s.charAt(start));
                    start++;
                }
                // 那么缩小窗口，必然比缩小前大，因此不用判断len是否替换
                left = end + 1;
            }else{
                // 判定是否超过了最长的长度
                maxLen = Math.max(maxLen, right - left + 1);
            }
            map.put(c,right);
            right++;
        }
        return maxLen;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
