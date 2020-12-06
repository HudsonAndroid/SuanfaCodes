package com.hudson.codes;//给你一个整数数组 nums ，和一个整数 target 。
//
// 该整数数组原本是按升序排列，但输入时在预先未知的某个点上进行了旋转。（例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2]
// ）。 
//
// 请你在数组中搜索 target ，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。 
// 
//
// 示例 1： 
//
// 
//输入：nums = [4,5,6,7,0,1,2], target = 0
//输出：4
// 
//
// 示例 2： 
//
// 
//输入：nums = [4,5,6,7,0,1,2], target = 3
//输出：-1 
//
// 示例 3： 
//
// 
//输入：nums = [1], target = 0
//输出：-1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 5000 
// -10^4 <= nums[i] <= 10^4 
// nums 中的每个值都 独一无二 
// nums 肯定会在某个点上旋转 
// -10^4 <= target <= 10^4 
// 
// Related Topics 数组 二分查找 
// 👍 1085 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution33 {
    // 题目很简单，首先想到的肯定是二分查找
    // 但问题是升序数组是翻转了的
    // 我们继续沿用二分查找思路
    // 首先找到中间位置，middle
    // 我们知道中间位置可以把翻转数组切分成一个升序数组和一个先升序到最大值后从最小值升序的数组
    // 值得注意的是，像 5 4 3 2 1这样，整个翻转的特殊情况进行确认【由于必然在某个位置上翻转，因此无需考虑】
    // 因此，我们判断哪一个是升序数组，是的那个，那么先判断我们的数字是否是在它内部
    // 如果是，继续找该升序数组内部；如果不是，那么非正即负，必然存在另一个数组中
    // 如此一直找下去，直到找到目标数为止

    // 【缺陷】像如果是5 4 3 2 1这种情况是无法正常的，因为middle拆开两边都是降序的
    // 如果题目要求这样的输入，我们可以针对这种情况使用二分查找【因为它相当于一个反着来的升序数组】
    public static int search(int[] nums, int target) {
        if(nums == null || nums.length == 0) return -1;
        int left = 0;
        int right = nums.length - 1;
        int middle = (left + right) >> 1;
        while(left <= right){
            // 如果找到，直接返回
            if(nums[middle] == target){
                return middle;
            }
            // 判断从left到right是否是升序数组【因为升序数组的另外一个数组第一个元素必然大于最后一个元素】
            // 【错误1】像[3,1]这样的输入的情况下，left和middle是相同的，这时我们实际上是要找后面的数组
            if(nums[left] <= nums[middle]){
                // 说明它是升序数组，因此判断目标数是否在它内部
                if(nums[left] <= target && target < nums[middle]){
                    // 因此继续找该数组内部
                    right = middle - 1;
                }else{
                    // 不在内部，那么找另一个数组
                    left = middle + 1;
                }
            }else{
                // 如果第一个数组不是升序的，那么另一个数组必然是升序的【特殊情况 5 4 3 2 1除外】
                if(nums[middle] < target && target <= nums[right]){
                    // 如果目标数在我们升序数组内部，那么，继续找
                    left = middle + 1;
                }else{
                    // 找另一个数组
                    right = middle - 1;
                }
            }
            middle = (left + right)>>1;
        }
        return -1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
