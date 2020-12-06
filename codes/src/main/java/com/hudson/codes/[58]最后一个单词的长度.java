package com.hudson.codes;//给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
//
// 如果不存在最后一个单词，请返回 0 。 
//
// 说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。 
//
// 
//
// 示例: 
//
// 输入: "Hello World"
//输出: 5
// 
// Related Topics 字符串 
// 👍 262 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution58 {
    public static int lengthOfLastWord(String s) {
        if(s == null || s.length() == 0) return 0;
        int right = s.length() - 1;
        while(right >= 0 && s.charAt(right) == ' ') right--;
        int len = 0;
        while(right >= 0 && s.charAt(right) != ' '){
            len ++;
            right --;
        }
        return len;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
