package com.hudson.codes;//给你两个二进制字符串，返回它们的和（用二进制表示）。
//
// 输入为 非空 字符串且只包含数字 1 和 0。 
//
// 
//
// 示例 1: 
//
// 输入: a = "11", b = "1"
//输出: "100" 
//
// 示例 2: 
//
// 输入: a = "1010", b = "1011"
//输出: "10101" 
//
// 
//
// 提示： 
//
// 
// 每个字符串仅由字符 '0' 或 '1' 组成。 
// 1 <= a.length, b.length <= 10^4 
// 字符串如果不是 "0" ，就都不含前导零。 
// 
// Related Topics 数学 字符串 
// 👍 530 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution67 {
    // 通过，时间98.60%，空间29.11%
    // 这种题型就是硬上了
    // 没什么特别技巧
    public static String addBinary(String a, String b) {
        // 遍历次数是更长的字符串的长度
        int firstLen = a.length();
        int secondLen = b.length();
        int len = Math.max(firstLen, secondLen);
        StringBuilder sb = new StringBuilder();
        int over = 0;
        for (int i = 0; i < len; i++) {
            int firstIndex = firstLen - 1 - i;
            int secondIndex = secondLen - 1 - i;
            int first = firstIndex < 0 ? 0 : a.charAt(firstIndex) - '0';
            int second = secondIndex < 0 ? 0 : b.charAt(secondIndex) - '0';
            int sum = first + second + over;
            over = sum >= 2 ? 1 : 0;
            sb.append(sum % 2);
        }
        // 判断最后是否有一位溢出
        if(over == 1){
            sb.append(1);
        }
        return sb.reverse().toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
