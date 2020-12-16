package com.hudson.codes;//给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
//
// 说明：每次只能向下或者向右移动一步。 
//
// 
//
// 示例 1： 
//
// 
//输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
//输出：7
//解释：因为路径 1→3→1→1→1 的总和最小。
// 
//
// 示例 2： 
//
// 
//输入：grid = [[1,2,3],[4,5,6]]
//输出：12
// 
//
// 
//
// 提示： 
//
// 
// m == grid.length 
// n == grid[i].length 
// 1 <= m, n <= 200 
// 0 <= grid[i][j] <= 100 
// 
// Related Topics 数组 动态规划 
// 👍 736 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution64 {
    // 通过，时间：86.73%，空间超过53.06%
    // 分析
    // 普通办法，回溯法，利用回溯法找出所有的可能情况，并记录走过的步子的和
    // 最后比较得到最小值
    // 很明显，如果二维数组很大的情况，走法是相当多的，效率很低
    // 因此这里再次考虑前面两道题类似的思路，动态规划
    // dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]
    // 最终填完表即完成了求算
    public static int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return -1;
        int rows = grid.length;
        int columns = grid[0].length;
        int[][] dp = new int[rows][columns];
        dp[0][0] = grid[0][0];
        // 我们可以优先把第一行和第一列的结果计算出来
        for (int j = 1; j < columns; j++) {
            dp[0][j] += (dp[0][j - 1] + grid[0][j]);
        }
        for (int i = 1; i < rows; i++) {
            dp[i][0] += (dp[i - 1][0] + grid[i][0]);
        }
        // 开始填其他地方
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[rows - 1][columns - 1];
    }


    // 通过，但效率并不高，可能就是因为没有对第一行和第一列先初始化吧
    // 这个是之前的提交，修改了输入数组，哈哈哈哈，不过思路也还是动态规划的思路
    public int minPathSum2(int[][] grid) {
        if(grid != null){
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if(i == 0 && j == 0) continue;
                    int sum = Integer.MAX_VALUE;
                    if(i > 0) sum = Math.min(sum, grid[i - 1][j]);
                    if(j > 0) sum = Math.min(sum, grid[i][j - 1]);
                    grid[i][j] += sum;
                }
            }
            return grid[grid.length - 1][grid[0].length - 1];
        }
        return 0;
    }

    // 综合上述两种方法 时间98.02%，空间58.30%
    public static int minPathSum3(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return -1;
        int rows = grid.length;
        int columns = grid[0].length;
        // 我们可以优先把第一行和第一列的结果计算出来
        for (int j = 1; j < columns; j++) {
            grid[0][j] += grid[0][j - 1];
        }
        for (int i = 1; i < rows; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        // 开始填其他地方
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
            }
        }
        return grid[rows - 1][columns - 1];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
