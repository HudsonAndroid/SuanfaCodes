package com.hudson.codes;
//三步问题。有个小孩正在上楼梯，楼梯有n阶台阶，小孩一次可以上1阶、2阶或3阶。实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模100
//0000007。 
//
// 示例1: 
//
// 
// 输入：n = 3 
// 输出：4
// 说明: 有四种走法
// 
//
// 示例2: 
//
// 
// 输入：n = 5
// 输出：13
// 
//
// 提示: 
//
// 
// n范围在[1, 1000000]之间 
// 
// Related Topics 动态规划 
// 👍 29 👎 0

/**
 * 题目分析：
 * 假设有i级台阶，那么第一步有三种选择，而所有选择之和
 * 则是这三种选择之后的所有次数之和。假设f(i)表示i级台阶
 * 跳法，那么 f(i) = f(i - 1) + f(i - 2) + f(i - 3)。
 * 我们只考虑i级的第一步和后续的次数，而后续次数具体是多少
 * 我们并不关心，只是用一个表达式表示。
 *
 * 动态规划无后效性：
 * 无论dp状态是如何得到的，都不会影响到后续dp状态的取值
 */

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 需要一个额外的数组（空间复杂度）存储结果
    // 耗时 25ms, 内存 42.4M
    public int waysToStep(int n) {
        if(n <= 2){
            return n; // 防止越界
        }
        int[] array = new int[n + 1];
        array[1] = 1;
        array[2] = 2;
        array[3] = 4;
        if(n <= 3){
            return array[n];
        }
        for (int i = 4; i <= n; i++) {
            // 对两个数之和取模，防止三个数之和溢出；同时为了避免三个数都接近1000000007，出现对每个数取模无效的情况
            array[i] = (array[i - 1] + (array[i - 2]+ array[i -3]) % 1000000007) % 1000000007;
        }
        return array[n];
    }

    //针对上面的优化
    // 考虑到我们只是为了计算出f(n)的值，因此之前的值（除f(n -1)、f(n-2)、f(n-3)之外）都没有用了
    // 因此可以节省一定的内存消耗。但通过leetcode上验证来看，时间复杂度增加了
    // 耗时 35ms,  内存： 35.1M
    public int waysToStep2(int n){
        if(n <= 2) return n;
        if(n == 3) return 4;
        int first = 1;
        int second = 2;
        int third = 4;
        for(int i = 4; i <= n; i++){
            // 每次循环计算出下一个数，并更新first, second角色
            int tmpThird = third;
            third = (first + (second + third) % 1000000007) % 1000000007;
            first = second;
            second = tmpThird;
        }
        return third;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
