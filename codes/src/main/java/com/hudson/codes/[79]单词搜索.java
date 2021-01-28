package com.hudson.codes;//给定一个二维网格和一个单词，找出该单词是否存在于网格中。
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。 
//
// 
//
// 示例: 
//
// board =
//[
//  ['A','B','C','E'],
//  ['S','F','C','S'],
//  ['A','D','E','E']
//]
//
//给定 word = "ABCCED", 返回 true
//给定 word = "SEE", 返回 true
//给定 word = "ABCB", 返回 false 
//
// 
//
// 提示： 
//
// 
// board 和 word 中只包含大写和小写英文字母。 
// 1 <= board.length <= 200 
// 1 <= board[i].length <= 200 
// 1 <= word.length <= 10^3 
// 
// Related Topics 数组 回溯算法 
// 👍 716 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution79 {
    // 时间88.30% 空间54.43%
    // 有点类似机器人走地图，明显的回溯法
    // 由于不能走之前走过的路，因此我们需要一个boolean类型的二维数组来限定
    public static boolean exist(char[][] board, String word) {
        if(board == null || board.length == 0 || board[0] == null
                || board[0].length == 0 || word == null || word.length() == 0) return false;
        int rows = board.length;
        int columns = board[0].length;
        boolean[][] flags = new boolean[rows][columns];
        // 从每一个位置尝试开始
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(goNext(board,0,flags,word,i,j)){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean goNext(char[][] board, int curIndex, boolean[][] flag, String word, int row, int column){
        if(curIndex >= word.length()) return true;
        if(row < 0 || column < 0 || row >= board.length || column >= board[0].length) return false;
        if(flag[row][column] || board[row][column] != word.charAt(curIndex)) return false;// 已经走过这里了或者不对应
        // 确定走该位置了
        flag[row][column] = true;
        // 往四个方向走
        int nextIndex = curIndex + 1;
        boolean result = goNext(board, nextIndex, flag, word, row - 1, column) ||
                goNext(board, nextIndex, flag, word, row + 1, column) ||
                goNext(board, nextIndex, flag, word, row, column - 1) ||
                goNext(board, nextIndex, flag, word, row, column + 1);
        if(!result){
            // 如果没有有效的走法，那么重新把该步置为false 【回溯】
            flag[row][column] = false;
        }
        return result;
    }


    // 复习
    public static boolean exist2(char[][] board, String word) {
        int rows = board.length;
        int columns = board[0].length;
        boolean[][] visited = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(board[i][j] == word.charAt(0)){
                    if(track(board, word, 0,i, j, rows, columns, visited)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean track(char[][] board, String word, int index,int row, int column, int rows, int columns, boolean[][] visited){
        if(index >= word.length()){
            return true;
        }
        if(row >= rows || column >= columns || row < 0 || column < 0) return false;
        // 【遗漏判断是否已经走过】
        if(!visited[row][column] && board[row][column] != word.charAt(index)) return false;
        visited[row][column] = true;
        boolean result = track(board, word, index + 1, row - 1, column, rows, columns, visited) ||
                track(board, word, index + 1, row + 1, column, rows, columns, visited) ||
                track(board, word, index + 1, row, column - 1, rows, columns, visited) ||
                track(board, word, index + 1, row, column + 1, rows, columns, visited);
        if(!result){
            visited[row][column] = false;// 恢复
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
