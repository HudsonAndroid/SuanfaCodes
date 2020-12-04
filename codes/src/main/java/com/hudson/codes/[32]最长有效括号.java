package com.hudson.codes;//给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
//
// 示例 1: 
//
// 输入: "(()"
//输出: 2
//解释: 最长有效括号子串为 "()"
// 
//
// 示例 2: 
//
// 输入: ")()())"
//输出: 4
//解释: 最长有效括号子串为 "()()"
// 
// Related Topics 字符串 动态规划 
// 👍 1094 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution32 {
    // 本题最直观的办法就是栈，而针对最值问题，我们通常还可以考虑
    // 动态规划来解决。
    // 动态规划最核心问题就是找到状态转移方程
    // dp[i]表示以i下标结尾的最长有效括号的长度（注意：必须是到i下标的字符串，而不是说到i位置的有效括号中的最长长度）
    // 那么我们分类讨论
    // 1. s[i] == (，那么dp[i]必然为0，因为以i结尾的字符必然不符合要求
    // 2. s[i] == )
    //   1) s[i - 1] == (，那么和下一个构成一对， dp[i] = dp[i-2] + 2
    //   2) s[i - 1] == )，那么当s[i - dp[i-1] - 1]是(的时候， dp[i] = dp[i - dp[i - 1] - 2] + 2 + dp[i - 1]
    // 解释下，我们上面讨论的都是假设的条件下，如果不符合条件，那么对应的dp[i]值将是默认值

    public int longestValidParentheses(String s) {
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
