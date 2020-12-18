package com.hudson.codes;//假设按照升序排序的数组在预先未知的某个点上进行了旋转。
//
// ( 例如，数组 [0,0,1,2,2,5,6] 可能变为 [2,5,6,0,0,1,2] )。 
//
// 编写一个函数来判断给定的目标值是否存在于数组中。若存在返回 true，否则返回 false。 
//
// 示例 1: 
//
// 输入: nums = [2,5,6,0,0,1,2], target = 0
//输出: true
// 
//
// 示例 2: 
//
// 输入: nums = [2,5,6,0,0,1,2], target = 3
//输出: false 
//
// 进阶: 
//
// 
// 这是 搜索旋转排序数组 的延伸题目，本题中的 nums 可能包含重复元素。 
// 这会影响到程序的时间复杂度吗？会有怎样的影响，为什么？ 
// 
// Related Topics 数组 二分查找 
// 👍 263 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution81 {
    // 时间：74.72%, 空间：68.71%
    // 本题是33题的升级版，但本质没有变化
    // 那么问题在哪呢？元素有重复
    // 在33题中，我们目标是确定升序数组的位置
    // 这道题也不例外，也是这样做。但是由于存在重复元素的原因
    // 我们无法仅通过nums[i] < nums[j] (i<j)就能断定出数组是升序的
    // 而可能是nums[i] = nums[j]的时候，也是升序的一边，例如
    // 2 4 5 2 2 2 2
    // 那有什么可能发生吗，想像一下，left = middle = right
    // 这种情况下，无法判断升序数组到底在哪
    // 这种情况下，把left增加，排除一个选项，继续处理即可
    public static boolean search(int[] nums, int target) {
        if(nums == null || nums.length == 0) return false;
        int left = 0;
        int right = nums.length - 1;
        int middle;
        // 注意：边界包含，因此要判断left=right时的情况
        while(left <= right){
            middle = (left + right) >> 1;
            if(nums[middle] == target) return true;
            // 判断首尾是否是相同的数
            if(nums[left] == nums[right]){
                // 排除第一个数
                left++;
                continue;
            }
            // 【错误1】像[3,1]这样的输入的情况下，left和middle是相同的，这时我们实际上是要找后面的数组
            if(nums[left] <= nums[middle]){
                // 如果左边是升序数组
                if(nums[middle] > target && nums[left] <= target){// 注意：这个时候left边界包含
                    // 数字在升序数组内部
                    right = middle - 1;
                }else{
                    // 数字在另一个数组内部
                    left = middle + 1;
                }
            }else{
                // 另一个是升序数组
                if(nums[middle] < target && nums[right] >= target){// 注意：这个时候，right边界包含
                    left = middle + 1;
                }else{
                    right = middle - 1;
                }
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
