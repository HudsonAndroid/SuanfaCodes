package com.hudson.codes;//给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
//
// 说明： 
//
// 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？ 
//
// 示例 1: 
//
// 输入: [2,2,1]
//输出: 1
// 
//
// 示例 2: 
//
// 输入: [4,1,2,1,2]
//输出: 4 
// Related Topics 位运算 哈希表 
// 👍 1644 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution136 {
    // 99.91%  64.45%
    // 一直异或，因为只有一个元素只出现一次，那么最终留下来的必然是那个数
    public static int singleNumber(int[] nums) {
        if(nums == null || nums.length == 0) return -1;
        int finalNum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            finalNum = finalNum ^ nums[i];
        }
        return finalNum;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
