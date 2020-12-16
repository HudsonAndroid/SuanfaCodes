package com.hudson.codes;//n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//
// 
//
// 上图为 8 皇后问题的一种解法。 
//
// 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。 
//
// 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。 
//
// 
//
// 示例： 
//
// 输入：4
//输出：[
// [".Q..",  // 解法 1
//  "...Q",
//  "Q...",
//  "..Q."],
//
// ["..Q.",  // 解法 2
//  "Q...",
//  "...Q",
//  ".Q.."]
//]
//解释: 4 皇后问题存在两个不同的解法。
// 
//
// 
//
// 提示： 
//
// 
// 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。 
// 
// Related Topics 回溯算法 
// 👍 695 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution51 {
    // 通过，时间84.32%，空间：89.33%
    // 分析
    // 是n个皇后放在n*n的棋盘上，那么必然每行每列上都有一个皇后
    // 我们可以用跟之前数独类似的方法，以行方式遍历，每行填入一个皇后
    // 填入的条件是列、左上斜线、右上斜线中没有出现过皇后【由于我们是按行向下
    // 因此不用考虑下面的行必然是空的】；如果没有符合位置的皇后，就回溯
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> finalResult = new ArrayList<>();
        // 使用数组更方便
        char[][] result = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = '.';
            }
        }
        // 一共有n行，每行确定一个皇后
        editQueens(finalResult, result, 0);
        return finalResult;
    }

    private static /*boolean*/void editQueens(List<List<String>> finalResult, char[][] result, int row){
        int size = result.length;
        if(row >= size){
            List<String> itemResult = new ArrayList<>();
            for (int m = 0; m < result.length; m++) {
                StringBuilder sb = new StringBuilder();
                for (int n = 0; n < result[0].length; n++) {
                    sb.append(result[m][n]);
                }
                itemResult.add(sb.toString());
            }
            finalResult.add(itemResult);
            return ;
        }

        for (int i = 0; i < size; i++) {
            // 尝试填入result[row][i]
            if(canEdit(result, row, i)){
                // 如果可以填入，那么把该位置放入皇后
                result[row][i] = 'Q';
                // 继续下一行
                editQueens(finalResult, result, row + 1);
                result[row][i] = '.';
            }
        }
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
