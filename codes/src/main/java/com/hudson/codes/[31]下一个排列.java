package com.hudson.codes;//实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
//
// 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。 
//
// 必须 原地 修改，只允许使用额外常数空间。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[1,3,2]
// 
//
// 示例 2： 
//
// 
//输入：nums = [3,2,1]
//输出：[1,2,3]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,1,5]
//输出：[1,5,1]
// 
//
// 示例 4： 
//
// 
//输入：nums = [1]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 100 
// 
// Related Topics 数组 
// 👍 859 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution31 {
    // 这道题个人感觉不简单，个人认为属于困难题（下面的解答时间超过97%，空间超过32.9%）

    // 分析
    // 我们发现，下一个数一定是从后往前找，变换最小位数（或者说变换各位）
    // 当我们最后一个数大于倒数第二个数的时候，我们直接让最后一个数和倒数第二个数交换即可
    // 以1 3 2 5 6 7 9 2 3 1 2 4为例；变换后最后2和4交换
    // 当我们最后一个数比上一个数小或相等的时候，我们应该继续往前找，直到找到后一个数大于前一个数的位置
    // 找到后把最后一个数跟找到的目标数的前一位交换即可【错误，见错误1解释】
    // 以1 3 2 5 6 7 9 2 3 1 4 2为例，我们需要把最后一个1和最后一个2交换
    // 考虑特殊情况，如果要交换的两个数相等，那么交换是没有意义的，因此需要继续往前一位，并把后面的数字
    // 从小到大排列的结果
    // 再考虑特殊情况 4 3 2 1，这已经是最大的数了，那么最终找不到后一个数大于前一个数的情况，因此
    // 直接反转数组即可。
    public static void nextPermutation(int[] nums) {
        if(nums == null || nums.length == 0 || nums.length == 1) return ;
        int p = nums.length - 1;
        if(nums[p] > nums[p - 1]){
            // 交换两个数即可
            swap(nums, p, p - 1);
        }else {
            // 【错误3】输入5,1,1是，后面两个1相等，因此需要增加等号
            while(nums[p] <= nums[p - 1]){
                p --;
                if(p <= 0){
                    // 找不到目标，因此数组是递减数组，反转即可
                    int left = 0;
                    int right = nums.length - 1;
                    reverse(nums, left, right);
                    return ;
                }
            }
            // 【错误1】要找的这个数至少要小于最后一个数，以确保这个位置这个数没有出现过
            // 例如 2 3 1， 2不能和1交换，因此1曾经出现过在第一个位置
            int wantSwapIndex = nums.length - 1;
            while(wantSwapIndex >= 0 && nums[wantSwapIndex] <= nums[p - 1]){//【错误2】，输入[1,1]，因此要判断是否是越界
                wantSwapIndex --;
            }
            if(wantSwapIndex >= 0){
                // 交换位置，然后让剩下的从小到大排列
                swap(nums, p - 1, wantSwapIndex);
                reverse(nums, p, nums.length - 1);
            }
        }
    }

    private static void reverse(int[]nums, int left, int right){
        while(left < right){
            swap(nums,left, right);
            left ++;
            right --;
        }
    }

    private static void swap(int[] nums, int first, int second){
        int tmp = nums[first];
        nums[first] = nums[second];
        nums[second] = tmp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
