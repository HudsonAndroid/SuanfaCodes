package com.hudson.codes;//给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。
//
// 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。 
//
// 示例: 
//
// X X X X
//X O O X
//X X O X
//X O X X
// 
//
// 运行你的函数后，矩阵变为： 
//
// X X X X
//X X X X
//X X X X
//X O X X
// 
//
// 解释: 
//
// 被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被
//填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。 
// Related Topics 深度优先搜索 广度优先搜索 并查集 
// 👍 440 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution130 {
    // 92.72%  25.27%
    // 根据题意
    // 直白的思路是
    // 遍历整个二维数组，把所有找到的o的左边右边、上边下边判断
    // 是否有x，如果有，则替换
    // 这种思路可以解决。这里不使用这种思路
    // 其实呢，我们可以使用与之前做过的题目类似的方法。
    // 既然我们要找被包围的o，那么我们是否能够先找出不被包围的o，
    // 然后把它们置为某个标识，例如#，当找到了所有的未被包围的o
    // 之后，再次遍历，把所有遇到的o（被包围的o）替换成x，然后所有的
    // #替换回o。
    // 而我们找未被包围的o很简单，从边界开始找即可，然后使用宽度优先遍历bfs
    // 向周围扩展即可
    public static void solve(char[][] board) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0) return ;
        // 找出四个边界上的o，然后以该o作为延伸方向，向四周扩展
        int row = board.length;
        int column = board[0].length;
        // 第一行和最后一行
        for (int j = 0; j < column; j++) {
            if(board[0][j] == 'O'){
                bfs(board, 0, j, row, column);
            }
            if(board[row - 1][j] == 'O'){
                bfs(board, row - 1, j, row, column);
            }
        }
        // 第一列和最后一列
        for (int i = 0; i < row; i++) {
            if(board[i][0] == 'O'){
                bfs(board, i, 0, row, column);
            }
            if(board[i][column - 1] == 'O'){
                bfs(board, i, column - 1, row, column);
            }
        }
        // 再次遍历，把所有的O变成x，把所有的#变回O
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if(board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
                if(board[i][j] == '#'){
                    board[i][j] = 'O';
                }
            }
        }
    }

    // 从该点向四周扩散
    private static void bfs(char[][] board, int i, int j, int row, int column){
        if(i < 0 || j < 0 || i >= row || j >= column) return ;
        if(board[i][j] == '#') return ;// 已经搜索过
        // 由于进入到本方法的必然是处在边界上或者被#的位置延伸而来，所以如果找到了O,必然需要修改成#
        if(board[i][j] == 'O'){
            board[i][j] = '#';
            // 同时继续延伸到其他处
            bfs(board, i - 1, j, row, column);
            bfs(board, i + 1, j, row, column);
            bfs(board, i, j - 1, row, column);
            bfs(board, i, j + 1, row, column);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
