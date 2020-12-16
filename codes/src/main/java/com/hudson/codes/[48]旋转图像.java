package com.hudson.codes;//给定一个 n × n 的二维矩阵表示一个图像。
//
// 将图像顺时针旋转 90 度。 
//
// 说明： 
//
// 你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。 
//
// 示例 1: 
//
// 给定 matrix = 
//[
//  [1,2,3],
//  [4,5,6],
//  [7,8,9]
//],
//
//原地旋转输入矩阵，使其变为:
//[
//  [7,4,1],
//  [8,5,2],
//  [9,6,3]
//]
// 
//
// 示例 2: 
//
// 给定 matrix =
//[
//  [ 5, 1, 9,11],
//  [ 2, 4, 8,10],
//  [13, 3, 6, 7],
//  [15,14,12,16]
//], 
//
//原地旋转输入矩阵，使其变为:
//[
//  [15,13, 2, 5],
//  [14, 3, 4, 1],
//  [12, 6, 8, 9],
//  [16, 7,10,11]
//]
// 
// Related Topics 数组 
// 👍 660 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution48 {
    // 这个解法看起来简单，但编写复制逻辑非常容易出错
    // 通过，时间：100%，空间84.30%
    //分析
    // 以下面的2和1为参考
    // 1 2 3
    // 4 5 6
    // 7 8 9
    // 转
    // 7 4 1
    // 8 5 2
    // 9 6 3
    // 2初始是(0,1),转变后(1, 2或n-1)；1初始(0,0)，转变后(0, 2或n-1)
    // 得出结论：某个位置的数字(i,j)旋转后是(j, n - 1 - i)。  可以用更多情况去验证
    // 由于题目要求只在原地修改（其实我在想，如果我用一个辅助二维数组复制一遍，然后
    // 对原数组改算不算，哈哈哈），因此我们按照上面思路，每次只修改围绕中心一圈的数字。
    // 为什么呢，如果我们以行遍历的方式一行一行地改，那么例如1换到了(0,2)的位置，2换到
    // 了(1,2)的位置【注意不能是交换，只是把原始数复制到目标位置，否则污染更大】，那么
    // 我们一次性将污染两个位置，导致这两个位置的数后续无法继续
    // 而如果我们先把6这个数暂存下来，2复制到6的位置，使用暂存数6复制到8的位置，暂存数更新为8
    // 继续使用暂存数复制到4的位置，暂存数变为4，继续复制到最初2的位置，这样下来，我们只需要
    // 一个暂存数的内存空间即可。
    public static void rotate(int[][] matrix) {
        if(matrix == null || matrix.length == 0) return;
        // 我们以行遍历开始，注意，我们最外一圈只需要完成n-1次，因为最后的一个数和第一个数属于一次操作
        // 我们继续看下一圈
        // 我们发现下一圈实际起始位置是在以第一个数的对角线上，即(i, i)，因此我们终止条件是i > n / 2
        // 而有可能出现中间只有一个数的情况，因此这也是要终止的
        // 示例：除了第一圈需要转之外，内圈也要转，终止条件是(i= 3, j = 3)
        //5  1  9  11
        //2  4  8  10
        //13 3  6  7
        //15 14 12 16
        // 转
        //15 13  2  5
        //14 3   4  1
        //12 6   8  9
        //16 7   10 11
        int len = matrix.length;
        for (int i = 0; i < len / 2; i++) {
            int endIndex = len - 1 - i;
            // 【错误3】思考的逻辑很好，代码却写错来有错误
            // 最外圈j最多可以到达len - 1的前一个位置（下标是 len - 2的位置，len - 1或者说最后一个位置不需要），
            // 完成到len - 1的遍历，即完成了这次的复制
            // 【错误4】j不是从0开始啊，思考的时候是正确的，写的就有问题咯
            for (int j = i; j < endIndex; j++) {
                // 在每一次的遍历中，需要进行len - i圈的旋转复制
                // 需要一个数暂存被复制到的位置的数
                int startI = i;
                int startJ = j;
                int tmp = matrix[startI][startJ];
                // 一圈需要复制4个方向，因此遍历4次
                for (int z = 0; z < 4; z++) {
                    // 【错误5】我们内圈的位置实际上需要添加上i的值，因为我们是根据差值来计算出目标的坐标的
                    int backup = matrix[startJ][endIndex - startI + i];
                    matrix[startJ][endIndex - startI + i] = tmp;
                    tmp = backup;
                    // 【错误1】更新startI和startJ的时候，不需要我们旋转，直接赋值即可，因为前面的代码会旋转
                    // 我们只需要更换为当前数字的i和j，不要旋转
                    // 【错误2】需要注意的是，这两个下标的计算会相互影响
                    backup = endIndex - startI + i;
                    startI = startJ;
                    startJ = backup;
//                    startI = endIndex - startI; //旋转后的i = j
//                    startJ = endIndex - startJ; // 旋转后的j = endIndex - i（这里的i是指之前数的i,不是上面计算结果的i）
                }
            }
        }
    }

    // 通过，时间：100%，空间：68.91%
    // 1 2 3
    // 4 5 6
    // 7 8 9
    // 转
    // 7 4 1
    // 8 5 2
    // 9 6 3
    // 方法2：实际上，这个过程其实就是先数组上下翻转，在对角线翻转的结果【数学奥数】
    public static void rotate2(int[][] matrix) {
        if(matrix == null || matrix.length == 0) return ;
        int len = matrix.length;
        int tmp;
        // 先上下翻转，外部循环次数是n/2
        for (int i = 0; i < len / 2; i++) {
            for (int j = 0; j < len; j++) {
                tmp = matrix[i][j];
                matrix[i][j] = matrix[len - 1 - i][j];
                matrix[len - 1 - i][j] = tmp;
            }
        }
        // 再对角线翻转，外部次数也是n/2，对角线以上的部分遍历，即确保j>i即可，所以最大的i是len - 2
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
