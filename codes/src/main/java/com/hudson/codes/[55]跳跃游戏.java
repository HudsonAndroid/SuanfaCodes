package com.hudson.codes;//给定一个非负整数数组，你最初位于数组的第一个位置。
//
// 数组中的每个元素代表你在该位置可以跳跃的最大长度。 
//
// 判断你是否能够到达最后一个位置。 
//
// 示例 1: 
//
// 输入: [2,3,1,1,4]
//输出: true
//解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
// 
//
// 示例 2: 
//
// 输入: [3,2,1,0,4]
//输出: false
//解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
// 
// Related Topics 贪心算法 数组 
// 👍 966 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution55 {
    // 通过，时间复杂度：99.97%，空间超过5%
    // 不同于45题，本题更加容易解答
    // 分析
    // 我们从后往前找，这样假设能够跳到最后
    //数字 2 3 1 1 4
    //下标 0 1 2 3 4
    // 找到一个数，直到该数的值大于或等于跳跃的目标位置到该数的距离差
    // 例如找到数字3，那么3到末尾4的距离差是3（不包括3），因此符合要求
    // 找到该位置后，继续以该位置作为新的跳跃目标位置，往前寻找能跳跃到
    // 该位置的数字
    public static boolean canJump(int[] nums) {
        return checkBefore(nums, nums.length - 1);
    }

    // 检查前面是否有符合要求的位置
    private static boolean checkBefore(int[] nums, int jumpTargetIndex){
        if(jumpTargetIndex <= 0) return true;
        // 遍历目标位置之前的位置，最坏情况会把所有位置都遍历一遍
        for (int i = jumpTargetIndex - 1; i >= 0; i--) {
            int distance = jumpTargetIndex - i;
            if(nums[i] >= distance){
                // 那么当前位置符合要求，需要继续判定前面是否能够跳到这个位置
                return checkBefore(nums, i);
            }
            // 如果能到这里，说明前面的都无法符合要求，继续查找前一个位置
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
