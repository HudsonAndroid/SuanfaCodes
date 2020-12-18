package com.hudson.codes;//编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
//
// 
// 每行中的整数从左到右按升序排列。 
// 每行的第一个整数大于前一行的最后一个整数。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,50]], target = 3
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,50]], target = 13
//输出：false
// 
//
// 示例 3： 
//
// 
//输入：matrix = [], target = 0
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// m == matrix.length 
// n == matrix[i].length 
// 0 <= m, n <= 100 
// -104 <= matrix[i][j], target <= 104 
// 
// Related Topics 数组 二分查找 
// 👍 288 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution74 {
    // 时间100%，空间52.52%
    //很明显二分查找
    // 只不过需要进一步确定位置
    public static boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return false;
        int row = matrix.length;
        int column = matrix[0].length;
        int searchLen = row * column;
        int left = 0;
        int right = searchLen - 1;
        int middle;
        int i,j;
        // 【错误1】要确保小于或等于，仅小于的情况下，会遗漏掉left = right的情况时的left下标
        while(left <= right){
            middle = (left + right) >> 1;
            i = middle / column;
            j = middle % column;
            if(matrix[i][j] == target){
                return true;
            }else if(matrix[i][j] > target){
                right = middle - 1;
            }else{
                left = middle + 1;
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
