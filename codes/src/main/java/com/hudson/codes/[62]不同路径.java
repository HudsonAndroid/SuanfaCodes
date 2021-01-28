package com.hudson.codes;//一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
//
// 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。 
//
// 问总共有多少条不同的路径？ 
//
// 
//
// 示例 1： 
//
// 
//输入：m = 3, n = 7
//输出：28 
//
// 示例 2： 
//
// 
//输入：m = 3, n = 2
//输出：3
//解释：
//从左上角开始，总共有 3 条路径可以到达右下角。
//1. 向右 -> 向右 -> 向下
//2. 向右 -> 向下 -> 向右
//3. 向下 -> 向右 -> 向右
// 
//
// 示例 3： 
//
// 
//输入：m = 7, n = 3
//输出：28
// 
//
// 示例 4： 
//
// 
//输入：m = 3, n = 3
//输出：6 
//
// 
//
// 提示： 
//
// 
// 1 <= m, n <= 100 
// 题目数据保证答案小于等于 2 * 109 
// 
// Related Topics 数组 动态规划 
// 👍 828 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution62 {

    // 提交后，超时，哈哈哈哈，输入是51,9
    // 很简单，明显的回溯法
    public static int uniquePaths(int m, int n) {
        // 根据题意，n表示是行数,m表示的是列数
        // 结果就是第一步的两种情况的和
        // 【错误1】没有考虑到像只有一行或一列的情况，所以第一步可以不用我们手动判定
//        return goNextStep(1, 0, m, n) + goNextStep(0,1,m,n);
        return goNextStep(0,0,m, n);
    }

    private static int goNextStep(int curRow, int curColumn, int m, int n){
        if(curRow == n - 1 && curColumn == m - 1) return 1;
        // 如果没有达到终点，那么选择
        // 有可能某条边到底了
        if(curRow == n - 1){
            // 只能往右走
            return goNextStep(curRow, curColumn + 1, m, n);
        }
        if(curColumn == m - 1){
            // 只能往下走
            return goNextStep(curRow + 1, curColumn, m, n);
        }
        // 有两种选择，因此累加
        return goNextStep(curRow + 1, curColumn, m, n) + goNextStep(curRow, curColumn + 1, m, n);
    }


    // 时间超过100%，空间超过78.74%
    // 仔细分析上面的算法，为什么超时了
    // 实际上，达到某个格子上的时候，我们不管前面怎么走的，后面的走法（格子到最终目的地）
    // 次数肯定对应这个格子是固定的，因此可想我们上面算法中存在很多重复计算
    // 我们以i表示当前的行，j表示当前的列, f(i,j)表示从左上角到i和j的位置的走法数
    // 那么 f(i,j) = f(i - 1, j) + f(i, j - 1)
    // 所以整个问题就转换成了一个动态规划填表的问题了，
    // 这个表实际也是一个二维数组，我们把二维数组表填好后，结果就也出来了
    public static int uniquePaths2(int m, int n) {
        if(m <= 0 || n <= 0) return 0;
        // leetcode上提交要求只有1的情况下返回1，这好像不用走吧，应该是0才对，wtf？
        if(m == 1 && n == 1) return 1;
        int[][] dp = new int[n][m];
        dp[0][0] = 0;
        // 特殊情况，只有一行或一列
        if(m > 1){
            dp[0][1] = 1;
        }
        if(n > 1){
            dp[1][0] = 1;
        }
        // 开始填表，以行遍历，由于f(i,j)的值取决于上面和左边的值，因此没问题
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if((i == 0 && j == 1) || (i == 1 && j == 0)) continue;
                int left = i - 1 < 0 ? 0 : dp[i - 1][j];
                int top = j - 1 < 0 ? 0 : dp[i][j - 1];
                dp[i][j] = left + top;
            }
        }
        return dp[n - 1][m - 1];
    }



    // 复习
    public static int uniquePaths3(int m, int n) {
        // f(m, n) = f(m - 1, n) + f(m, n - 1)
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 最开始的位置忽略
                if(i == 0 && j == 0) continue;
                int left = i - 1 < 0 ? 0 : dp[i - 1][j];
                int right = j - 1 < 0 ? 0 : dp[i][j - 1];
                dp[i][j] = left +  right;
            }
        }
        return dp[m - 1][n - 1];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
