package com.hudson.codes;//编写一个函数来查找字符串数组中的最长公共前缀。
//
// 如果不存在公共前缀，返回空字符串 ""。 
//
// 示例 1: 
//
// 输入: ["flower","flow","flight"]
//输出: "fl"
// 
//
// 示例 2: 
//
// 输入: ["dog","racecar","car"]
//输出: ""
//解释: 输入不存在公共前缀。
// 
//
// 说明: 
//
// 所有输入只包含小写字母 a-z 。 
// Related Topics 字符串 
// 👍 1363 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution14 {
    // 最直观的办法，遍历寻找公共前缀
    public String longestCommonPrefix(String[] strs) {
        if(strs != null && strs.length > 0){
            if(strs.length == 1){
                return strs[0];
            }
            String maxPrefix = strs[0];
            for (int i = 1; i < strs.length; i++) {
                // 用之前的公共前缀和后面的字符串比较
                for (int j = 0; j < maxPrefix.length(); j++) {
                    //错误1： 没有确保strs[i]字符串中j是否越界
                    String tmp = strs[i];
                    if(j >= tmp.length() || maxPrefix.charAt(j) != tmp.charAt(j)){
                        if(j == 0){
                            return "";
                        }
                        maxPrefix = maxPrefix.substring(0, j);
                        break;
                    }
                }
            }
            return maxPrefix;
        }
        return "";
    }
}
//leetcode submit region end(Prohibit modification and deletion)
