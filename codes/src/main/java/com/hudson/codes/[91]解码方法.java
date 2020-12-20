package com.hudson.codes;//一条包含字母 A-Z 的消息通过以下方式进行了编码：
//
// 
//'A' -> 1
//'B' -> 2
//...
//'Z' -> 26
// 
//
// 给定一个只包含数字的非空字符串，请计算解码方法的总数。 
//
// 题目数据保证答案肯定是一个 32 位的整数。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "12"
//输出：2
//解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
// 
//
// 示例 2： 
//
// 
//输入：s = "226"
//输出：3
//解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
// 
//
// 示例 3： 
//
// 
//输入：s = "0"
//输出：0
// 
//
// 示例 4： 
//
// 
//输入：s = "1"
//输出：1
// 
//
// 示例 5： 
//
// 
//输入：s = "2"
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 100 
// s 只包含数字，并且可能包含前导零。 
// 
// Related Topics 字符串 动态规划 
// 👍 582 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution91 {
    // 容易出错，可以做以下，锻炼动态规划

    // 时间99.96%，空间77.67%
    // 第一个字符可能是第一个数编码得到，也可能是前两个数编码得到
    // 令f(i)表示以s[i]结尾的子串的编码方法
    // 因此f(i) = f(i - 1) + f(i - 2)
    // 但需要注意的是，如果两个数组合起来超过26，那么没有任何数对应它
    // 因此需要分类讨论
    // 【错误1】遗漏了对0的讨论
    public static int numDecodings(String s) {
        if(s == null || s.length() == 0) return 0;
        // 如果第一个就是0，那么第一个无法编码，因此返回0
        if(s.charAt(0) == '0') return 0;
        if(s.length() == 1) return 1;
        int[] dp = new int[s.length() + 1];
        dp[1] = 1;
        for (int i = 1; i < s.length(); i++) {
            int pre = (s.charAt(i - 1) - '0') * 10;
            int num = s.charAt(i) - '0';
            // 【错误1】我们除了判断当前为0，必须与前一位合并之外，如果后面一位是0，那么也必须与当前位组成二位数
            if(i + 1 <= s.length() - 1 && s.charAt(i + 1) == '0'){
                if(num == 0){
                    // 当前的是0，后面一个也是0，无法编码
                    return 0;
                }else{
                    // 当前位必须与后一位组成二位数
                    dp[i + 1] = dp[i];
                    continue;
                }
            }
            if(num == 0){
                // 它必须与前面一个一起
                // 【错误3】如果必须与前一个数组合，那么不能超过26
                if(pre + num <= 26){
                    dp[i + 1] = dp[i];
                }else{
                    return 0;
                }
                //【错误2】要和前一个数组成一个的话，必须确保前一个数不是0
            }else if(pre != 0 && pre + num <= 26){
                if(i == 1){// 第二位位置需要进一步确定，如果可以单独组成，那么就有两种
                    dp[2] = 2;
                }else{
                    dp[i + 1] = dp[i] + dp[i - 1];
                }
            }else{
                // 必须与前面一个数分开来
                dp[i + 1] = dp[i];
            }
        }
        return dp[s.length()];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
