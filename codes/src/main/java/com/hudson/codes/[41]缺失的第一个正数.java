package com.hudson.codes;//给你一个未排序的整数数组，请你找出其中没有出现的最小的正整数。
//
// 
//
// 示例 1: 
//
// 输入: [1,2,0]
//输出: 3
// 
//
// 示例 2: 
//
// 输入: [3,4,-1,1]
//输出: 2
// 
//
// 示例 3: 
//
// 输入: [7,8,9,11,12]
//输出: 1
// 
//
// 
//
// 提示： 
//
// 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的额外空间。 
// Related Topics 数组 
// 👍 878 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution41 {
    // 首先要对题目理解透彻
    // 在一个未排序的数组中找出第一个未出现的正数（最小的未出现的正数），且时间复杂度O(n)，空间O（1）
    // 我们之前有做过类似的题目，是长度为n + 1的数组，数字只在1 - n之间，找出一个重复的数
    // 我们可以按照把数放到该数值对应的下标上去，这样就可以找到目标
    // 这里我们是否可以使用这种方式来解决时间复杂度为O(n)的情况呢？
    // 我们分析下，假设数组长度是n，
    //   1）如果数组内所有的数都N大或都是负数，那么第一个未出现的是1；
    //   2）如果数组内只有一个数空缺，那么就是该数；如果有多个，那么就是最小的那个
    //   3）如果数组是连续递增的数组（1 2 3 4 ...），那么结果应该是n+1；如果是(0 1 2 3 4 ...)的递增数组，则是n
    // 也就是所有的结果都在 1 - (n + 1)范围内（边界包括），
    // 那么我们可以遍历整个数组，把遍历到的数放到它应该在的位置（对应的该数-1的位置），然后再遍历一遍数组，找到
    // 那个（下标 + 1） 不等于值的位置就是我们的结果【为什么要放到数-1的下标的位置，因为如果0的位置和它目标位置不对应
    // 我们不能返回0，因为要求是正数，所以必须从1开始，如果继续后面找的话虽然可以找出来，但特殊情况1 2 3 4 ..这种的完整
    // 的递增数组的话，n+1是没法判断出来的，需要额外判断】
    public int firstMissingPositive(int[] nums) {
        if(nums == null || nums.length == 0) return 1;
        // 遍历整个数组
        for (int i = 0; i < nums.length; i++) {
            // 判断当前位置是否应该是它待的位置。由于交换完后，当前位置可能还不等于i + 1，因此要while
            // 【错误2】：判断条件应该是nums[i] - 1 < nums.length
            // 【优化】1.负数可以忽略； 2.如果被换的位置刚好是等于他应该待的位置，那么不必要为了当前的数重新换下，因为交换前后一样
            while(nums[i] > 0 && nums[i] != i + 1 && nums[i] - 1 < nums.length && nums[nums[i] - 1] != nums[i]){
                //那么把它跟真正该待的位置的数交换
                // 【错误1】换的数应该放在nums[i] - 1的位置
                swap(nums, i, nums[i] - 1);
            }
        }
        // 再次遍历整个数组，找出第一个数不与下标+1相等的位置，并返回
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != i + 1){
                return i + 1;//【错误3】必须返回i+1
            }
        }
        // 所有位置都正常，那么只有下一个数了
        return nums.length + 1;
    }

    private static void swap(int[] nums, int l, int r){
        int tmp = nums[l];
        nums[l] = nums[r];
        nums[r] = tmp;
    }






    // 复习
    public static int firstMissingPositive2(int[] nums) {
        if(nums == null || nums.length == 0) return 1;
        // 尝试，1放在下标为0的位置，2放在下标为1的位置...
        for (int i = 0; i < nums.length; i++) {
            while(nums[i] != i + 1){
                // 那么尝试把这个数放到它该放的位置
                // 如果目标位置已经是它该有的数，则不替换
                int targetIndex = nums[i] - 1;
                if(targetIndex < nums.length && targetIndex >= 0){
                    if(nums[targetIndex] == targetIndex + 1) break;// 这一步很重要，否则会出现死循环，例如[1,1]
                    // 可以交换，因此交换
                    int tmp = nums[targetIndex];
                    nums[targetIndex] = nums[i];
                    nums[i] = tmp;
                }else{
                    break;
                }
            }
        }
        // 再次遍历数组
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != i + 1){
                return i + 1;
            }
        }
        return nums.length + 1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
