package com.hudson.codes;//n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//
// 
//
// 上图为 8 皇后问题的一种解法。 
//
// 给定一个整数 n，返回 n 皇后不同的解决方案的数量。 
//
// 示例: 
//
// 输入: 4
//输出: 2
//解释: 4 皇后问题存在如下两个不同的解法。
//[
// [".Q..",  // 解法 1
//  "...Q",
//  "Q...",
//  "..Q."],
//
// ["..Q.",  // 解法 2
//  "Q...",
//  "...Q",
//  ".Q.."]
//]
// 
//
// 
//
// 提示： 
//
// 
// 皇后，是国际象棋中的棋子，意味着国王的妻子。皇后只做一件事，那就是“吃子”。当她遇见可以吃的棋子时，就迅速冲上去吃掉棋子。当然，她横、竖、斜都可走一或 N
//-1 步，可进可退。（引用自 百度百科 - 皇后 ） 
// 
// Related Topics 回溯算法 
// 👍 216 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution52 {
    // 跟51题一样，只不过是计算解法数，因此略
    // 时间：62.49%，空间：74.58%
    public static int totalNQueens(int n) {
        // 使用数组更方便
        char[][] result = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = '.';
            }
        }
        // 一共有n行，每行确定一个皇后
        return editQueens(result, 0);
    }

    private static int editQueens(char[][] result, int row){
        int size = result.length;
        if(row >= size){
            return 1;
        }

        int count = 0;
        for (int i = 0; i < size; i++) {
            // 尝试填入result[row][i]
            if(canEdit(result, row, i)){
                // 如果可以填入，那么把该位置放入皇后
                result[row][i] = 'Q';
                // 继续下一行
                count += editQueens(result, row + 1);
                result[row][i] = '.';
            }
        }
        return count;
    }

    private static boolean canEdit(char[][] result, int row, int column){
        // 由于我们是每确定一行上的一个皇后，就继续下一行，因此不用判断行是否有多个皇后
        // 判断列上是否重复，只需要判断当前位置的上面即可
        for (int i = 0; i < row; i++) {
            if(result[i][column] == 'Q') return false;
        }
        // 判断左上斜线是否有皇后
        for(int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--){
            if(result[i][j] == 'Q') return false;
        }
        // 判断右上斜线是否有皇后
        for(int i = row - 1, j = column + 1; i >= 0 && j < result.length; i--, j++){
            if(result[i][j] == 'Q') return false;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
