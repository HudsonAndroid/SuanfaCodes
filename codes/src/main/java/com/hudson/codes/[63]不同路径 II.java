package com.hudson.codes;//一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
//
// 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。 
//
// 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？ 
//
// 
//
// 网格中的障碍物和空位置分别用 1 和 0 来表示。 
//
// 
//
// 示例 1： 
//
// 
//输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
//输出：2
//解释：
//3x3 网格的正中间有一个障碍物。
//从左上角到右下角一共有 2 条不同的路径：
//1. 向右 -> 向右 -> 向下 -> 向下
//2. 向下 -> 向下 -> 向右 -> 向右
// 
//
// 示例 2： 
//
// 
//输入：obstacleGrid = [[0,1],[0,0]]
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// m == obstacleGrid.length 
// n == obstacleGrid[i].length 
// 1 <= m, n <= 100 
// obstacleGrid[i][j] 为 0 或 1 
// 
// Related Topics 数组 动态规划 
// 👍 457 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution63 {
    // 时间超过22.45%，空间超过50.32%
    // 思路与上一题类似
    // 只不过我们需要判断当前的位置是否是1，如果是1那么我们直接把当前的dp结果设置为0
    // 表示无法达到此位置，后续在考虑从该位置达到当前位置时可以不用考虑从它进入
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid == null || obstacleGrid.length == 0
                || obstacleGrid[0] == null || obstacleGrid[0].length == 0) return 0;
        // 由于设置了[0][0]是1，因此这里也不用判断了（leetCode认定只有一个数的情况下，方法数是1，与上一题类似）
        // 但是考虑到只有一个数情况下，就包含了障碍物，即1，那应该返回0吧
        // 【错误2】如果起点就是障碍物，那么没有可能，因此全部返回0
//        if(obstacleGrid.length == 1 && obstacleGrid[0].length == 1 ){
//            if( obstacleGrid[0][0] != 1) return 1;
//            return 0;
//        }
        if(obstacleGrid[0][0] == 1) return 0;
        int rows = obstacleGrid.length;
        int columns = obstacleGrid[0].length;
        int[][] dp = new int[rows][columns];
        dp[0][0] = 1;
        // 由于我们是按行遍历，因此只需要知道[0][1]的值即可，不需要知道[1][0]的值
        // 【错误】上面一行注释思路错误，我们的路径次数的结果是来自于left+right，因此
        // 由于我们设置dp[0][0] = 0，导致如果不设置[1][0]的值的话，会出现[1][0]也是0的情况
        // 因此最好时设置dp[0][0] = 1，这样[1][0]和[0][1]的值就不用手动确定了
//        if(columns > 1) {
//            //那么进一步判定(0,1)的值
//            if(obstacleGrid[0][1] == 1){
//                dp[0][1] = 0;
//            }
//            dp[0][1] = 1;
//        }
        // 开始填表
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // 我们要填过0，0的位置
                if(i == 0 && j == 0) continue;
                if(obstacleGrid[i][j] == 1){
                    // 如果当前位置是1，表明有障碍物，因此后面以此为参考的时候，都是0
                    dp[i][j] = 0;
                    continue;
                }
                int top = i -1 < 0 ? 0 : dp[i-1][j];
                int left = j - 1 < 0 ? 0 : dp[i][j - 1];
                dp[i][j] = top + left;
            }
        }
        return dp[rows - 1][columns - 1];
    }


    // 通过，时间：100%，空间：36.97%
    // 实际上，我们如果第一行或者第一列中出现了障碍物，那么该行或该列的障碍物后面的数值都是无法到达的
    // 另外，如果我们直接确定了第一行和第一列的数值的话，在遍历过程就不需要判断i-1或j-1小于0了，效率得到提高
    public static int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        if(obstacleGrid == null || obstacleGrid.length == 0
                || obstacleGrid[0] == null || obstacleGrid[0].length == 0) return 0;
        if(obstacleGrid[0][0] == 1) return 0;
        int rows = obstacleGrid.length;
        int columns = obstacleGrid[0].length;
        int[][] dp = new int[rows][columns];
        // 我们确定第一行和第一列的值，可以知道的是，如果列或者行上没有障碍物的话，那么每一个位置上方法数其实都是1
        for (int i = 0; i < columns; i++) {
            if(obstacleGrid[0][i] == 1){
                break; //如果有障碍物，直接不用继续判断了
            }
            dp[0][i] = 1;
        }
        for (int i = 0; i < rows; i++) {
            if(obstacleGrid[i][0] == 1){
                break;
            }
            dp[i][0] = 1;
        }
        // 开始填表
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                if(obstacleGrid[i][j] == 1){
                    // 如果当前位置是1，表明有障碍物，因此后面以此为参考的时候，都是0
                    dp[i][j] = 0;
                    continue;
                }
                dp[i][j] = dp[i-1][j] + dp[i][j - 1];
            }
        }
        return dp[rows - 1][columns - 1];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
