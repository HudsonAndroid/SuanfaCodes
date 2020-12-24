package com.hudson.codes;//给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
//
// 说明：本题中，我们将空字符串定义为有效的回文串。 
//
// 示例 1: 
//
// 输入: "A man, a plan, a canal: Panama"
//输出: true
// 
//
// 示例 2: 
//
// 输入: "race a car"
//输出: false
// 
// Related Topics 双指针 字符串 
// 👍 307 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution125 {
    // 99.85%   85.09%
    // 双指针， 需要注意，大写A可以与小写a对应
    // 这个还可以优化，仔细看看代码哪里有问题
    public static boolean isPalindrome(String s) {
        if(s == null) return false;
        if(s.length() == 0) return true;
        int left = 0;
        int right = s.length() - 1;
        while(left < right){
            // 验证位置是否是符合要求的字符
            char c = s.charAt(left);
            c = isValidChar(c);
            if(c == '&'){
                left++;
                continue;
            }
            char r = s.charAt(right);
            r = isValidChar(r);
            if(r == '&'){
                right--;
                continue;
            }
            if(c != r){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private static char isValidChar(char c){
        // 有个方法检测是否是字母和数字
//        Character.isLetterOrDigit(c);
        // 转小写字母
//        Character.toLowerCase(c)
        int offset = c - '0';
        if(offset >= 0 && offset <= 9) return c;
        offset = c - 'a';
        // 【错误】，最后一个应该是25，不是26
        if(offset >= 0 && offset <= 25){
            return c;
        }
        offset = c - 'A';
        if(offset >= 0 && offset <= 25){
            return (char) ('a' + offset);
        }
        return '&';
    }
    // 可以优化的地方：其实应该是在内部while直到找到合法数为止
}
//leetcode submit region end(Prohibit modification and deletion)
