package com.hudson.codes;//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
//
// 示例: 
//
// 输入: [-2,1,-3,4,-1,2,1,-5,4]
//输出: 6
//解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
// 
//
// 进阶: 
//
// 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。 
// Related Topics 数组 分治算法 动态规划 
// 👍 2690 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution53 {

    // 通过，时间94.13%，空间66.44%
    // 最大和，很明显使用动态规划最简单
    // 由于包含正负数，因此需要分类讨论
    // dp(i)表示以i结尾的最大子数组的和， 注意必须以i结尾
    // 那么dp(i) = dp(i - 1) + nums[i] 且nums[i] >= 0
    // dp(i) = dp(i - 1) 且 nums[i] < 0  这个转移方程有问题吧（复习发现）
    // 应该是这样的吧？ dp(i) = max(dp(i - 1) + nums[i], nums[i])
    public static int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int maxValue = dp[0];
        for (int i = 1; i < nums.length; i++) {
            // 找到下一个位置上，只要之前的和+当前的数的和比当前数要大，那么使用之前的和继续累加比使用当前数重新开始更好
            if((dp[i - 1] + nums[i]) > nums[i]){
                dp[i] = dp[i - 1] + nums[i];
            }else{
                //【错误1】如果小于0，不应该加之前的数，应该重新开始，初始值就时当前数，而不是0
                dp[i] = nums[i];
            }
            maxValue = Math.max(maxValue, dp[i]);
        }
        return maxValue;
    }

    // 通过：时间94.13%的用户，空间83.59%
    // 更简便的方法，如果我们的dp[i - 1] > 0，那么无论何种情况下，我们加上nums[i]的和一定比nums[i]大，
    // 因此我们只需要判断上一个的dp值是否>0即可
    public static int maxSubArray2(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int maxValue = dp[0];
        for (int i = 1; i < nums.length; i++) {
            if(dp[i - 1] > 0){
                dp[i] = dp[i - 1] + nums[i];
            }else{
                dp[i] = nums[i];
            }
            maxValue = Math.max(maxValue, dp[i]);
        }
        return maxValue;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
