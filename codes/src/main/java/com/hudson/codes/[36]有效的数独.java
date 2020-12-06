package com.hudson.codes;//判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
//
// 
// 数字 1-9 在每一行只能出现一次。 
// 数字 1-9 在每一列只能出现一次。 
// 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。 
// 
//
// 
//
// 上图是一个部分填充的有效的数独。 
//
// 数独部分空格内已填入了数字，空白格用 '.' 表示。 
//
// 示例 1: 
//
// 输入:
//[
//  ["5","3",".",".","7",".",".",".","."],
//  ["6",".",".","1","9","5",".",".","."],
//  [".","9","8",".",".",".",".","6","."],
//  ["8",".",".",".","6",".",".",".","3"],
//  ["4",".",".","8",".","3",".",".","1"],
//  ["7",".",".",".","2",".",".",".","6"],
//  [".","6",".",".",".",".","2","8","."],
//  [".",".",".","4","1","9",".",".","5"],
//  [".",".",".",".","8",".",".","7","9"]
//]
//输出: true
// 
//
// 示例 2: 
//
// 输入:
//[
//  ["8","3",".",".","7",".",".",".","."],
//  ["6",".",".","1","9","5",".",".","."],
//  [".","9","8",".",".",".",".","6","."],
//  ["8",".",".",".","6",".",".",".","3"],
//  ["4",".",".","8",".","3",".",".","1"],
//  ["7",".",".",".","2",".",".",".","6"],
//  [".","6",".",".",".",".","2","8","."],
//  [".",".",".","4","1","9",".",".","5"],
//  [".",".",".",".","8",".",".","7","9"]
//]
//输出: false
//解释: 除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。
//     但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。 
//
// 说明: 
//
// 
// 一个有效的数独（部分已被填充）不一定是可解的。 
// 只需要根据以上规则，验证已经填入的数字是否有效即可。 
// 给定数独序列只包含数字 1-9 和字符 '.' 。 
// 给定数独永远是 9x9 形式的。 
// 
// Related Topics 哈希表 
// 👍 447 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution36 {
    // 说明，这个题目中，i是行，j是列，i是数值方向上增长的，j是水平方向上增长的，不要和x,y坐标混了




    // 通过，效率：时间超过53.27%，空间超过67.03%
    // 首先我们判定不重复的方式
    // 一般情况下，判定是否重复我们都使用Map来判定
    // 由于我们有行列判定、box判定，因此我们需要3类Map判定
    // 我们从左到右，从上到下遍历，
    // 由于列方向的情况需要记录，但是我们从左到右遍历
    // 无法一次性记录所有的列情况，因此我们需要一个数组型Map记录每列情况
    // 而行方向上只需要一个map即可，因为我们可以每次直接判断行
    // 而对box，由于每次都有行或列的增加，因此我们也需要一系列的Map记录
    // 现在问题是如何在遍历过程根据i和j判定是在第几个box
    // 实际上就是先计算出当前位于第几行box，行的位置取决于，即 i / 3
    // 而位于第几列的box，取决与j，即 j / 3
    // 因此box的位置  index = (i/3)*3 + j/3;
    // 但实际过程中，我们可以与行类似，只需要保留水平方向上的3个Map即可
    public static boolean isValidSudoku(char[][] board) {
        if(board == null || board.length == 0 || board[0].length == 0) return false;
        Map<Character,Integer> row = new HashMap<>();
        Map<Character,Integer>[] columns = new HashMap[9];
        Map<Character,Integer>[] boxes = new HashMap[3];
        // 初始化存储结构【注意上面new的里面数据成员还没有初始化】【注意：错误，容易出错】
        for (int i = 0; i < columns.length; i++) {
            columns[i] = new HashMap<>();
        }
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new HashMap<>();
        }
        // i是一行元素下标，j是一列元素下标
        for (int i = 0; i < board.length; i++) {
            if(i % 3 == 0){
                // 清空这一轮box的状态
                for (Map<Character, Integer> box : boxes) {
                    box.clear();
                }
            }
            row.clear();// 清空原有记录
            for (int j = 0; j < board[0].length; j++) {
                // 遍历二维数组
                char curChar = board[i][j];
                if(curChar == '.'){
                    continue;
                }
                if(row.get(curChar) != null){
                    return false;
                }else{
                    row.put(curChar, 1);
                }
                if(columns[j].get(curChar) != null){
                    return false;
                }
                columns[j].put(curChar,1);
                int index = j / 3;
                if(boxes[index].get(curChar) != null){
                    return false;
                }
                boxes[index].put(curChar, 1);
            }
        }
        return true;
    }


    // 通过，时间 95.12%，空间82.64%
    // 实际上，我们上面使用Map结构中，最多存储的次数都是1次，因此可以考虑优化
    // 我们使用数组来替换Map结构
    public static boolean isValidSudoku2(char[][] board) {
        if(board == null || board.length == 0 || board[0].length == 0) return false;
        // 另外，考虑到每次都要行都要清理数据，为了优化，空间换时间，创建二位数组
        int[][] row = new int[9][10]; // 针对行，创建一个数组，数组存储的数记录了本行中该数的出现次数，而下标代表当前字符
        int[][] columns = new int[9][10];// 类似的，针对列，需要有9个数组存储次数，存储的值是次数，次数最大值是9，因此创建长度是10
        int[][] boxes = new int[9][10];// 同理
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char curChar = board[i][j];
                if(curChar == '.') continue;
                // 计算当前数代表的实际int
                int index = curChar - '0';
                if(row[i][index] != 0){
                    return false;
                }
                row[i][index] ++;
                if(columns[j][index] != 0){
                    return false;
                }
                columns[j][index] ++;
                int boxIndex = (i / 3) * 3 + j / 3;
                if(boxes[boxIndex][index] != 0){
                    return false;
                }
                boxes[boxIndex][index] ++;
            }
        }
        return true;
    }


    // 通过，效率最高，时间100%，空间96.43%
    // 实际上，我们还可以更加优化
    // 我们实际代表的出现次数其实要不是0，要不是1，那么直接可以使用1位代替
    // 即我们可以使用一个9位的数字代替存储次数的数据结构
    // 因此我们用1个数记录行的情况，用9个数记录列的情况，用3个数记录box的情况
    // 回到第一种方式，我们恢复的过程会比之前简单很多，直接将数和000000000做位&运算
    public static boolean isValidSudoku3(char[][] board) {
        if(board == null || board.length == 0 || board[0].length == 0) return false;
        int row = 0;
        int[] columns = new int[9];
        int[] boxes = new int[3];
        for (int i = 0; i < board.length; i++) {
            if(i % 3 == 0){
                for (int index = 0; index < boxes.length; index++) {
                    boxes[index] = 0;
                }
            }
            row = 0; // 恢复初始状态 row & 00000000
            for (int j = 0; j < board[0].length; j++) {
                char curChar = board[i][j];
                if(curChar == '.') continue;
                int num = board[i][j] - '0';
                row = checkRepeat(row, num);
                columns[j] = checkRepeat(columns[j], num);
                int index = j / 3;
                boxes[index] = checkRepeat(boxes[index], num);
                // 判断是否重复的结果
                if(row == -1 || columns[j] == -1 || boxes[index] == -1){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 如果已经出现过num，那么返回-1，否则返回记录后的map
     * @param map
     * @param num
     * @return
     */
    private static int checkRepeat(int map, int num){
        // map如果右移num - 1位【注意：没有0需要存储，因此如果num=1的话，它是存储在最后一位的】
        // 做&运算，为1，则已经出现过；不是的话，需要给map记录上1，因此做^运算
        return (map >> (num - 1) & 1) == 1 ? -1 : (map ^ (1 << (num - 1)));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
