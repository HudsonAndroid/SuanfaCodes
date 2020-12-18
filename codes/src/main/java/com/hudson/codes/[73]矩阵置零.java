package com.hudson.codes;//给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法。
//
// 示例 1: 
//
// 输入: 
//[
//  [1,1,1],
//  [1,0,1],
//  [1,1,1]
//]
//输出: 
//[
//  [1,0,1],
//  [0,0,0],
//  [1,0,1]
//]
// 
//
// 示例 2: 
//
// 输入: 
//[
//  [0,1,2,0],
//  [3,4,5,2],
//  [1,3,1,5]
//]
//输出: 
//[
//  [0,0,0,0],
//  [0,4,5,0],
//  [0,3,1,0]
//] 
//
// 进阶: 
//
// 
// 一个直接的解决方案是使用 O(mn) 的额外空间，但这并不是一个好的解决方案。 
// 一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。 
// 你能想出一个常数空间的解决方案吗？ 
// 
// Related Topics 数组 
// 👍 346 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution73 {
    // 时间99.89%  空间47.49%
    // 根据题解的方法
    // 遍历整个二维数组，把所有遇到的0的对应行和列的头一个元素置为0
    // 最后把所有头一个元素为0的行和列都置为0
    public static void setZeroes(int[][] matrix) {
        if(matrix == null || matrix.length == 0) return ;
        int rows = matrix.length;
        int columns = matrix[0].length;
        int startInfluence = matrix[0][0] == 0 ? 1 : -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(matrix[i][j] == 0){
                    // 把对应的行和列的头一个元素标记为0，表示该行和该列需要后面被全部置为0
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                    // 【错误2】起始是否是初始0还是某个影响导致0，还是双边影响导致
                    if(startInfluence != 1 && startInfluence != 4){// 1表示一开始起始就是0,4表示由于行列同时影响
                        if(i == 0 && j != 0){
                            if(startInfluence == 3){
                                startInfluence = 4;// 双边都有影响
                            }else{
                                startInfluence = 2; // 行影响导致，最终需要遍历第一行
                            }
                        }
                        if(j == 0 && i != 0){
                            if(startInfluence == 2){
                                startInfluence = 4;
                            }else{
                                startInfluence = 3; // 列影响导致，最终需要遍历第一列
                            }
                        }
                    }
                }
            }
        }
        // 【错误1】由于如果把第一列的修改的话，会影响到后面的列判断，因此从1开始；列的遍历也是
        // 再次遍历，把所有被标记的行和列置0
        for (int i = 1; i < rows; i++) {
            if(matrix[i][0] == 0){
                // 把该行置0
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int j = 1; j < columns; j++) {
            if(matrix[0][j] == 0){
                // 把该列置0
                for (int i = 0; i < rows; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
        // 验证(0,0)位置是否对第一行和第一列有影响，由于首次遍历过程可能会把（0,0）置为0，因此我们需要判断来自于哪里的影响
        if(matrix[0][0] == 0){
            if(startInfluence == 3 || startInfluence == 1 || startInfluence == 4){
                for (int i = 0; i < rows; i++) {
                    matrix[i][0] = 0;
                }
            }
            if(startInfluence == 2 || startInfluence == 1 || startInfluence == 4){
                for (int j = 0; j < columns; j++) {
                    matrix[0][j] = 0;
                }
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
