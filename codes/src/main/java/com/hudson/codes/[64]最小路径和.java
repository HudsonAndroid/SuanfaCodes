package com.hudson.codes;
//给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
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
// 👍 722 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution64 {
    public int minPathSum(int[][] grid) {
        if(grid != null){
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if(i == 0 && j == 0) continue;
                    int sum = Integer.MAX_VALUE;
                    if(i > 0) sum = Math.min(sum, grid[i - 1][j]);
                    if(j > 0) sum = Math.min(sum, grid[i][j - 1]);
                    grid[i][j] += sum;// 会修改原数组
                }
            }
            return grid[grid.length - 1][grid[0].length - 1];
        }
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
