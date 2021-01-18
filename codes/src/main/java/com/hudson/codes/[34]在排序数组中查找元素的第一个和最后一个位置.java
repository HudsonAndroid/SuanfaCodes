package com.hudson.codes;//给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
//
// 如果数组中不存在目标值 target，返回 [-1, -1]。 
//
// 进阶： 
//
// 
// 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？ 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 8
//输出：[3,4] 
//
// 示例 2： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 6
//输出：[-1,-1] 
//
// 示例 3： 
//
// 
//输入：nums = [], target = 0
//输出：[-1,-1] 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 105 
// -109 <= nums[i] <= 109 
// nums 是一个非递减数组 
// -109 <= target <= 109 
// 
// Related Topics 数组 二分查找 
// 👍 758 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution34 {
    // 题目要求logn的复杂度，因此很明显是二分查找
    // 首先通过二分查找找到一个位置等于该数，然后向前和向后延申,
    // 但是如果重复的数字太多的情况下，时间复杂度将可能升至O(n)
    // 因此我们不能这样操作。
    // 我们回顾下二分查找思路
    // 如果目标数大于middle，那么往后找；如果小于middle，那么往前找
    // 如果等于就直接结束了
    // 既然这样，我们是否能够在找到目标数后，继续判定它是否是第一个出现的
    // 目标数，或者是否是最后一个出现的目标数呢？
    // 其实，如果我们要找第一个出现的目标数，在我们找到对应的数的时候，这个
    // 位置可能是中间位置，也可能是第一个位置，因此我们继续把right设置为中间
    // 位置，继续查找，缩小范围，这样最终能找到第一个出现目标数的位置
    // 最后一个数的位置同理
    public static int[] searchRange(int[] nums, int target) {
        if(nums == null || nums.length == 0) return new int[]{-1,-1};
        int first = findFirstTarget(nums, target);
        int last = findLastTarget(nums, target);
        return new int[]{first, last};
    }

    private static int findFirstTarget(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int middle = (left + right) >> 1;
        while(left < right){
            if(nums[middle] > target){
                // 目标数在前面
                right = middle - 1;
            }else if(nums[middle] ==  target){
                // 这时，我们不终止查找，继续查找第一个位置
                right = middle;//要包含该位置
            }else {
                // 说明数在后面
                left = middle + 1;
            }
            // 但是这里不需要+1，哈哈哈，什么鬼
            middle = (left + right) >> 1;
        }
        // 最后跳出循环的时候，left = right
        if(nums[left] ==  target){
            // 如果找到了目标数
            return left;
        }
        return  -1;
    }

    // 查找最后一个数的思路与查找第一个数的思路一致，只是在等于的情况下，我们是确定左边界
    private static int findLastTarget(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int middle = (left + right) >> 1;
        while(left < right){
            if(nums[middle] > target){
                // 目标数在前面
                right = middle - 1;
            }else if(nums[middle] ==  target){
                // 这时，我们不终止查找，继续查找第一个位置
                left = middle;//要包含该位置
            }else {
                // 说明数在后面
                left = middle + 1;
            }
            // 【错误1】 这里需要+1,否则出现死循环， 例如{5, 7, 7, 8, 8, 10}, 8这样的输入，会导致middle=4一直死循环
            // 在leetcode的视频中也说到了要+1，见https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/solution/zai-pai-xu-shu-zu-zhong-cha-zhao-yuan-su-de-di-3-4/
            middle = (left + right + 1) >> 1;
        }
        // 最后跳出循环的时候，left = right
        if(nums[left] ==  target){
            // 如果找到了目标数
            return left;
        }
        return  -1;
    }



    // 复习。 注意：找尾部的时候，要left + right + 1再除以2
    public static int[] searchRange2(int[] nums, int target) {
        if(nums == null || nums.length == 0) return new int[]{-1,-1};
        return new int[]{findStart(nums, target), findEnd(nums,target)};
    }

    private static int findStart(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        while(left < right){
            int middle = (left + right) >>> 1;
            if(nums[middle] == target){
                // 找到了，那么继续迁移right
                right = middle; // 而且需要继续保留当前的middle，否则有可能再找不到我们要的数
            }else if(nums[middle] > target){
                right = middle - 1;
            }else{
                left = middle + 1;
            }
        }
        // 跳出循环
        if(left == right && nums[left] == target){
            return left;
        }
        return -1;// 没有找到
    }

    private static int findEnd(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        while(left < right){
            int middle = (left + right + 1) >>> 1;
            if(nums[middle] == target){
                // 找到了，继续前移left
                left = middle;
            }else if(nums[middle] > target){
                right = middle - 1;
            }else{
                left = middle + 1;
            }
        }
        if(left == right && nums[left] == target){
            return left;
        }
        return -1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
