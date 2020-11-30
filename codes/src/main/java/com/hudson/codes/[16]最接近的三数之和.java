package com.hudson.codes;//给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和
//。假定每组输入只存在唯一答案。 
//
// 
//
// 示例： 
//
// 输入：nums = [-1,2,1,-4], target = 1
//输出：2
//解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
// 
//
// 
//
// 提示： 
//
// 
// 3 <= nums.length <= 10^3 
// -10^3 <= nums[i] <= 10^3 
// -10^4 <= target <= 10^4 
// 
// Related Topics 数组 双指针 
// 👍 632 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution16 {
    // 与15题类似
    public int threeSumClosest(int[] nums, int target) {
        if(nums == null || nums.length == 0) return 0;
        Arrays.sort(nums);
        // 遍历数组，遍历中去重，重复的情况有两种：遍历到的数字与之前的重复、固定遍历数字，双指针移动的两次头尾情况一样
        // 由于我们遍历要找3个数，因此最多遍历到 length - 3（包含该位置）
        int closestSum = 0;
        int closestOffset = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            if(i > 0 && nums[i] == nums[i - 1]){
                // 当前数字和上次的数字一样，跳过
                continue;
            }
            // 双指针查找对应位置
            int left = i + 1; // 因为我们不能找当前位置之前的数
            int right = nums.length - 1;
            while (left < right){
                int sum = nums[i] + nums[left] + nums[right];
                int offset = sum - target;
                if(Math.abs(offset) < closestOffset){
                    closestOffset = Math.abs(offset);
                    closestSum = sum;
                }
                if(offset == 0){
                    // 如果找到相等的，不用再找了，直接返回
                    return target;
                }else if(offset > 0){
                    // 数字太大了，因此前移right
                    right--;
                }else{
                    // 数字太小了，因此后移left
                    left ++;
                }
            }
        }
        return closestSum;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
