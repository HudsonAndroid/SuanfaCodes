package com.hudson.codes;//给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
//
// 你可以假设数组中无重复元素。 
//
// 示例 1: 
//
// 输入: [1,3,5,6], 5
//输出: 2
// 
//
// 示例 2: 
//
// 输入: [1,3,5,6], 2
//输出: 1
// 
//
// 示例 3: 
//
// 输入: [1,3,5,6], 7
//输出: 4
// 
//
// 示例 4: 
//
// 输入: [1,3,5,6], 0
//输出: 0
// 
// Related Topics 数组 二分查找 
// 👍 754 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution35 {
    // 很明显，二分查找
    public int searchInsert(int[] nums, int target) {
        if(nums == null || nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;
        while(left < right){
            int middle = (left + right) >> 1;
            if(nums[middle] == target){
                return middle;
            }
            if(nums[middle] > target){
                // 往前查找
                right = middle - 1;
            }else {
                // 往后查找
                left = middle + 1;
            }
        }
        // 判断找到的位置是否是目标位置，【这个时候left = right = middle】
        if(nums[left] ==  target){
            return left;
        }else if(nums[left] > target){
            return left;
        }else{
            return left + 1;// 返回第一个比他大的数的位置
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
