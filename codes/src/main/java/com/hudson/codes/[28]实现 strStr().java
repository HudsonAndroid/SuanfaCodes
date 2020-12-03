package com.hudson.codes;//实现 strStr() 函数。
//
// 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如
//果不存在，则返回 -1。 
//
// 示例 1: 
//
// 输入: haystack = "hello", needle = "ll"
//输出: 2
// 
//
// 示例 2: 
//
// 输入: haystack = "aaaaa", needle = "bba"
//输出: -1
// 
//
// 说明: 
//
// 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。 
//
// 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与C语言的 strstr() 以及 Java的 indexOf() 定义相符。 
// Related Topics 双指针 字符串 
// 👍 630 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution28 {
    // 这题目确定？ 直接indexOf  时间超过100%的用户，空间超过82%的用户，杠杠的
    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    // 通过，时间超过100%，空间超过35.19%的用户
    // 那么我们考虑下不使用indexOf的方式
    // 这很明显是比对字符串包含的问题，那么我们可以利用滑动窗口解决
    // 滑动窗口的长度是后者字符串的长度
    public int strStr2(String haystack, String needle) {
        if(haystack == null || needle == null) return 0;
        int windowsLen = needle.length();
        int left = 0;
        int right = windowsLen + left - 1;
        int limitLen = haystack.length();
        while(right < limitLen){
            if(haystack.substring(left,right + 1).equals(needle)){
                return left;
            }
            // 由于窗口大小不变，因此left和right同时增加
            left ++;
            right ++;
        }
        return -1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
