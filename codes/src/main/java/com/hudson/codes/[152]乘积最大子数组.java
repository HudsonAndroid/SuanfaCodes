package com.hudson.codes;//给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
//
// 
//
// 示例 1: 
//
// 输入: [2,3,-2,4]
//输出: 6
//解释: 子数组 [2,3] 有最大乘积 6。
// 
//
// 示例 2: 
//
// 输入: [-2,0,-1]
//输出: 0
//解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。 
// Related Topics 数组 动态规划 
// 👍 847 👎 0

/**
 * 题型分析：
 * 由于求的是最大乘积的连续子数组，用
 * f(i)表示子数组最大乘积，那么f(i)的值
 * 取决于f(i-1)和a[i]的值。
 * 但是由于类似[2,-1,-2]这样，f(3)的最优解是4；
 * 而f(2)的最优解是2， f(3)的最优解与f(2)的最优解
 * 没有必然的联系，因此不符合动态规划的最优子结构；
 *
 *
 * 其实上述问题产生的原因是由于前面的连续子数组乘积为负数，
 * 刚好遇到当前数为负数的情况下，可能出现更大数导致。
 * 那么我们可以同时计算出某个位置的最小值，当出现最小值的
 * 情况下，那么后续计算中如果遇到是负数，则之前数的最小值与之
 * 相乘是更大的值，而不是最大值与之相乘。
 */

//leetcode submit region begin(Prohibit modification and deletion)
class Solution152 {
    public int maxProduct(int[] nums) {
        if(nums != null){
            int[] maxValues = new int[nums.length];
            int[] minValues = new int[nums.length];
            // 起始位置都一样
            maxValues[0] = minValues[0] = nums[0];
            int result = nums[0];
            for (int i = 1; i < nums.length; i++) {
                // 循环的过程主体是计算出连续数组到i位置的最大值和最小值，但并不是该数组的最大连续子数组乘积
                if(nums[i] > 0) {
                    // 如果当前位置 >0， 那么应该用更大的数与之相乘以得到更大乘积； 用更小的数与之相乘得到更小乘积
                    // 然后进一步判断结果是否比nums[i]本身更大或更小
                    maxValues[i] = Math.max(nums[i], nums[i] * maxValues[i - 1]);
                    minValues[i] = Math.min(nums[i], nums[i] * minValues[i - 1]);
                }else{
                    maxValues[i] = Math.max(nums[i], nums[i] * minValues[i - 1]);
                    minValues[i] = Math.min(nums[i], nums[i] * maxValues[i - 1]);
                }
                // 前面我们只是计算出了连续数组连续到i的最大值或最小值，但我们实际求的是内部连续子数组最大乘积，因此
                // 需要和之前的比较
                result = Math.max(result, maxValues[i]);
            }
            return result;
        }
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
