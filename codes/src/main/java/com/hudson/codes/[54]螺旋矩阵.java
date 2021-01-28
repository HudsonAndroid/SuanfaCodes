package com.hudson.codes;//给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
//
// 示例 1: 
//
// 输入:
//[
// [ 1, 2, 3 ],
// [ 4, 5, 6 ],
// [ 7, 8, 9 ]
//]
//输出: [1,2,3,6,9,8,7,4,5]
// 
//
// 示例 2: 
//
// 输入:
//[
//  [1, 2, 3, 4],
//  [5, 6, 7, 8],
//  [9,10,11,12]
//]
//输出: [1,2,3,4,8,12,11,10,9,5,6,7]
// 
// Related Topics 数组 
// 👍 563 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution54 {
    // 通过，时间100%，空间：33.16%
    // 螺旋输出（顺时针）
    // 分析
    // 把结果分成一圈一圈的方式输出
    // 与48题有一点点类似，终止条件是(i,i)横纵坐标相等的位置i>= ((更短边 + 1)/2)
    public static List<Integer> spiralOrder(int[][] matrix) {
        if(matrix == null || matrix.length == 0) return new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        int endI = matrix.length - 1;
        int endJ = matrix[0].length - 1;
        int minStartI = (Math.min(matrix.length, matrix[0].length) + 1) >> 1;
        int startI = 0;
        // 分为4步，第一步，横向右，第二步，竖向下，第三步，横向左，第四步，竖向上回到起始。
        while(true){
            int startJ = startI;
            if(startI >= minStartI){
                break;
            }
            // 第一步
            for (int j = startJ; j < endJ; j++) {
                result.add(matrix[startI][j]);
            }
            // 第二步
            for (int i = startI; i < endI; i++) {
                result.add(matrix[i][endJ]);
            }
            // 第三步（如果只有一行，那么将会导致错误输出，因此必须保证endI是大于startI的）
            if(endI > startI){
                for (int j = endJ; j > startJ; j--) {
                    result.add(matrix[endI][j]);
                }
            }else{// 但是由于第一步中并没有包括末尾元素，因此我们把末尾元素输出
                result.add(matrix[endI][endJ]);
            }
            // 第四步（如果只有一列，那么将会导致错误输出，因此必须保证endJ是大于startJ的）
            if(endJ > startJ){
                for (int i = endI; i > startI; i--) {
                    result.add(matrix[i][startJ]);
                }
            }else{
                // 【错误1】特殊情况下，如果最后一圈只剩下一个数，那么两个特殊情况都符合，这时候会重复加入，因此排除
                if(startI != endI || startJ != endJ){
                    result.add(matrix[endI][endJ]);
                }
            }
            // 至此，一圈完成，继续后面的圈
            startI ++;
            endI--;
            endJ--;
        }
        return result;
    }


    // 复习
    private static List<Integer> fix(int[][] inputs){
        int startLimit = (Math.min(inputs.length , inputs[0].length) + 1) >> 1;
        int startI = 0;
        int startJ = 0;
        int endI = inputs.length - 1;
        int endJ = inputs[0].length - 1;
        List<Integer> result = new ArrayList<>();
        while(startI < startLimit){
            startJ = startI;
            // 横向，不包括末尾
            for (int j = startJ; j < endJ; j++) {
                result.add(inputs[startI][j]);
            }
            // 向下
            for (int i = startI; i < endI; i++) {
                result.add(inputs[i][endJ]);
            }
            // 向左 ，如果只有单独的一行，这里会重复输出前面的横向，因此判断
            if(endI > startI){
                for(int j = endJ; j > startJ; j --){
                    result.add(inputs[endI][j]);
                }
            }else{
                // 在只有一行的情况下，由于第一次横向没有处理掉，所以这里处理
                result.add(inputs[startI][endJ]);
            }
            // 向上
            if(endJ > startJ){
                for(int i = endI; i > startI; i--){
                    result.add(inputs[i][startJ]);
                }
            }else{
                // 这里也一样，只有一列的情况
//                result.add(inputs[endI][startJ]); // 但其实有一种情况就是只有一个数的情况，即startI == endI && startJ == endJ
                // 因此如果不是start == endI && startJ == endJ，那么就可以加，否则会重复添加
                if(startI != endI || startJ != endJ){
                    result.add(inputs[endI][startJ]);
                }
            }
            startI++;
            endI--;
            endJ--;
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
