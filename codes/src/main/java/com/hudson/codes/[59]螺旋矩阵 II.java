package com.hudson.codes;//给定一个正整数 n，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵。
//
// 示例: 
//
// 输入: 3
//输出:
//[
// [ 1, 2, 3 ],
// [ 8, 9, 4 ],
// [ 7, 6, 5 ]
//] 
// Related Topics 数组 
// 👍 271 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution59 {
    // 通过，时间：100%，空间：76.87%
    // 与第54题类似，分四步走
    // 这道题可能更简单，终止条件也是一样的，循环的圈数是n/2 + 1
    public static int[][] generateMatrix(int n) {
        if(n <= 0) return new int[0][0];
        int[][] result = new int[n][n];
        // 【错误1】圈数应该是(n + 1)/2，而不是(n / 2)+1，需要分析除了奇数情况，也要考虑偶数情况
        int circles = ((n + 1) >> 1);
        int curValue = 1;
        int start = 0;
        int end = n - 1;
        // 注意：四步中有一个特殊情况，就是最后一圈是一个数字【单行或单列的情况不用考虑，见54题】
        for (int i = 0; i < circles; i++) {
            // 特殊情况处理
            if(start == end){
                // 这种情况下，最后一圈只剩下一个数字
                result[start][start] = curValue;
            }
            // 第一步，不包括最后一个数  行不变
            for (int j = start; j < end; j++) {
                result[start][j] = curValue;
                curValue ++;
            }
            // 第二步  列不变
            for (int j = start; j < end; j++) {
                result[j][end] = curValue;
                curValue++;
            }
            // 第三步  行不变  【错误2】最低是start，不是0
            for (int j = end; j > start; j--) {
                result[end][j] = curValue;
                curValue++;
            }
            // 第四步  列不变
            for (int j = end; j > start; j--) {
                result[j][start] = curValue;
                curValue++;
            }
            // 最后起始位置和终止位置需要变动，进行下一圈的遍历
            start++;
            end--;
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
