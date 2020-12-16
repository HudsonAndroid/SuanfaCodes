package com.hudson.codes;//假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
//
// 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？ 
//
// 注意：给定 n 是一个正整数。 
//
// 示例 1： 
//
// 输入： 2
//输出： 2
//解释： 有两种方法可以爬到楼顶。
//1.  1 阶 + 1 阶
//2.  2 阶 
//
// 示例 2： 
//
// 输入： 3
//输出： 3
//解释： 有三种方法可以爬到楼顶。
//1.  1 阶 + 1 阶 + 1 阶
//2.  1 阶 + 2 阶
//3.  2 阶 + 1 阶
// 
// Related Topics 动态规划 
// 👍 1378 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution70 {
    // 时间100%，空间73.88%
    // 本题其实跟62、63题基本属于一个题型
    // 就是有两种选择方法，求方法次数
    // 很明显的动态规划
    // 最后一步可以选择一个台阶到达顶部，也可以选择两个台阶
    // 因此 f(i) = f(i - 1) + f(i - 2)
    public static int climbStairs(int n) {
        // 【错误1】对特殊情况讨论， n = 1，这时，2下标越界
        if(n == 1) return 1;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        // 接下来填表即可
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
