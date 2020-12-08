package com.hudson.codes;//编写一个程序，通过填充空格来解决数独问题。
//
// 一个数独的解法需遵循如下规则： 
//
// 
// 数字 1-9 在每一行只能出现一次。 
// 数字 1-9 在每一列只能出现一次。 
// 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。 
// 
//
// 空白格用 '.' 表示。 
//
// 
//
// 一个数独。 
//
// 
//
// 答案被标成红色。 
//
// 提示： 
//
// 
// 给定的数独序列只包含数字 1-9 和字符 '.' 。 
// 你可以假设给定的数独只有唯一解。 
// 给定数独永远是 9x9 形式的。 
// 
// Related Topics 哈希表 回溯算法 
// 👍 710 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution37 {
    // 通过，时间超过100%的用户，空间超过93.04%的用户，牛逼

    // 本质上就是对一个一个的空格填入可能的数，
    // 如果填到某个空格无法填入任何值，则回溯到之前的状态，
    // 更换数字填入。
    // 因此本质就是递归+回溯
    // 优化：考虑到某些空格可能存在唯一填入值，我们可以优先把
    // 这些空格填好，这样能够避免后续在递归和回溯过程中重复
    // 对该空格判断.
    public static void solveSudoku(char[][] board) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0) return ;
        // 使用一个数存储行或列或box状态情况
//        int row = 0;
//        int[] columns = new int[9];
//        int[] boxes = new int[3];
        // 遍历一遍，以统计出所有的row,columns,boxes的分布情况，并存储空格的位置i和j，以便后面直接遍历这些位置来填数字
        // 由于我们需要遍历完所有情况，才能决定空格可以填的值，因此仅靠一个数维持行这样的状态是不够的
        int[] rows = new int[9];
        int[] columns = new int[9];
        int[] boxes = new int[9];
        List<int[]> blanks = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char curChar = board[i][j];
                if(curChar == '.'){
                    // 是空格，保存，以便后续遍历
                    blanks.add(new int[]{i,j});
                    // 不需要继续后续操作
                    continue;
                }
                int num = curChar - '0';
                // 保存行情况
                rows[i] = saveMapIgnoreInvalid(rows[i], num);
                columns[j] = saveMapIgnoreInvalid(columns[j], num);
                int index = (i / 3) * 3 + j / 3;
                boxes[index] = saveMapIgnoreInvalid(boxes[index], num);
            }
        }
        // 完成了所有已有数字的遍历之后，需要对空格进行填表
        // 为了优化，我们先去填写那些只能填写一个数字的位置
        for (int z = 0; z < blanks.size(); z++) {
            int[] blank = blanks.get(z);
            // 当前该数可填的map信息
            int i = blank[0];
            int j = blank[1];
            int boxesIndex = (i / 3) * 3 + j / 3;
            int blankMap = rows[i] | columns[j] | boxes[boxesIndex];
            if(isOnlyOne0(blankMap)){
                // 那么我们填入那个唯一数
                int num = getBlankNum(blankMap);
                board[i][j] = (char) (num + '0');
                // 更新map信息
                rows[i] = saveMapIgnoreInvalid(rows[i], num);
                columns[j] = saveMapIgnoreInvalid(columns[j], num);
                boxes[boxesIndex] = saveMapIgnoreInvalid(boxes[boxesIndex], num);
                // 移除当前的数，注意z需要--，避免跳过某个位置
                blanks.remove(z--);
            }
        }
        // 把唯一数的空格填完后，可以开始递归回溯操作了
        boolean result = editBlanks(board, rows, columns, boxes, blanks, 0);
        if(!result){
            // 没有找到
            System.out.println("抱歉，该数独无解");
        }
    }

    private static boolean editBlanks(char[][] board, int[] rows, int[] columns, int[] boxes,List<int[]> blanks, int editIndex){
        if(editIndex >= blanks.size()){
            return true;
        }
        int[] blankMap = blanks.get(editIndex);
        int i = blankMap[0];
        int j = blankMap[1];
        int boxesIndex = (i / 3) * 3 + j / 3;
        // 【错误1】是做或运算，保留所有的1
        int map = rows[i] | columns[j] | boxes[boxesIndex];
        if((map ^ 0x1ff) == 0)
            return false;// 如果所有位都是1，则说明没有可以填的数了，直接返回false
        int num = 1;
        // 对空位进行填数字【错误2】判断终止的条件不能是这个，而应该是必须完成9位的判断，例如0000 0000 1这种的，实际
        // 可以填写的位置有8个，而如果用map>0判断，直接结果是一次也没有。因此直接限定查询9次，找到空位则尝试
//        while(map > 0){
        for (int times = 0; times < 9; times++) {
            if((map & 1) == 0){// 发现空格，逻辑是判断最后一位是否是0
                // 尝试填写该数字
                board[i][j] = (char) (num + '0');
                // 更新rows，columns，boxes信息
                rows[i] = saveMapIgnoreInvalid(rows[i], num);
                columns[j] = saveMapIgnoreInvalid(columns[j], num);
                boxes[boxesIndex] = saveMapIgnoreInvalid(boxes[boxesIndex], num);
                // 确定了该空格的数之和，继续后续的空格判断
                if(editBlanks(board, rows, columns, boxes, blanks, editIndex + 1)){
                    // 如果符合要求，那么终止，并返回
                    return true;
                }else{
                    // 当前空格填入的数导致后面填数失败，因此恢复到之前的状态，并换下一个可填数字【回溯】
                    rows[i] = removeMapIgnoreInvalid(rows[i],num);
                    columns[j] = removeMapIgnoreInvalid(columns[j], num);
                    boxes[boxesIndex] = removeMapIgnoreInvalid(boxes[boxesIndex], num);
//                board[i][j] = '.';// 这个可以改，也可以不改，因为后续遍历必然变动它
                }
            }
            // 我们排除掉当前的位，这个位可能是数字，可能是空格
            map = map >> 1;
            num ++;
        }
//        }
        return false;
    }

    private static int getBlankNum(int map){
        int num = 1;
        while ((map & 1) == 1){
            map = map >> 1;
            num ++;
        }
        return num;
    }

    // 保存该数出现过到指定位，如果该数之前已经出现过，那么返回-1，因为不合法
    private static int saveMap(int map, int num){
        return ((map >> (num - 1)) & 1) == 1 ? -1 : (map ^ (1 << (num - 1)));
    }

    // 考虑到题意中输入必然合法，那就不考虑已有数字重复的问题
    private static int saveMapIgnoreInvalid(int map, int num){
        return map ^ (1 << (num - 1));
    }

    // 把指定位取反,和上面的逻辑是一样的
    private static int removeMapIgnoreInvalid(int map, int num){
        return map ^ (1 << (num - 1));
    }

    // 判断是否二进制数只包含一个0
    // 注意：不能使用~取反运算符，因为它会把符号位取反，结果是不正确的，因为java中没有无符号整数
    // 因此思路转变，n ^ (111111111)二进制，这样出来的结果中会只包含一个1，其他都是0，这时，把结果和结果-1做与运算判断是否是0即可
    private static boolean isOnlyOne0(int map){
        // 1 1111 1111 = 1 15 15 = 0x1ff
        int reverse = map ^ 0x1ff;
        return (reverse & (reverse - 1)) == 0;
    }


    // 位运算
    // 判断一个二进制数只包含一个1，   n & (n - 1) = 0
    // 循环 n & (n-1) 直到结果是0.统计次数，次数就是二进制中1的个数
    // 判断一个数的奇偶性   n & 1 = 1就是奇数
}
//leetcode submit region end(Prohibit modification and deletion)
