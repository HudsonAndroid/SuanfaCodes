package com.hudson.codes;//给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的 连续 子数组，并返回其长度。如果不存在符合条件的子数组，返回
// 0。 
//
// 
//
// 示例： 
//
// 输入：s = 7, nums = [2,3,1,2,4,3]
//输出：2
//解释：子数组 [4,3] 是该条件下的长度最小的子数组。
// 
//
// 
//
// 进阶： 
//
// 
// 如果你已经完成了 O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。 
// 
// Related Topics 数组 双指针 二分查找 
// 👍 498 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution209 {
    // 双指针（滑动窗口） start end两个指针，如果和>=s，则start后挪一位；
    // 如果和小于s，则end后挪一位
    public static int minSubArrayLen(int s, int[] nums) {
        int start = 0;
        int end = 0;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;
        while(end < nums.length){
            sum += nums[end];
            while(sum >= s) {
                minLen = Math.min(minLen, end - start + 1);
                sum -= nums[start];
                start ++;
            }
            end ++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static int minSubArrayLen2(int s, int[] nums) {
        int start = 0;
        int end = -1;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;
        while(true){
            if(sum < s){
                end ++;
                if(end >= nums.length){
                    break;
                }
                sum += nums[end];
            }else{
                minLen = Math.min(minLen, end - start + 1);
                sum -= nums[start];
                start ++;
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // 暴力破解
    public static int minSubArrayLen3(int s, int[] nums){
        int minLen = Integer.MAX_VALUE;
        int sum;
        for (int i = 0; i < nums.length; i++) {
            sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if(sum >= s){
                    minLen = Math.min(minLen, j - i + 1);
                    break;// 优化：因为这已经是以i为起始点最短的了
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // 二分查找方式（这个位置上可能有点难以理解）
    public static int minSubArrayLen4(int s, int[] nums){
        int minLen = Integer.MAX_VALUE;
        // 创建一个 nums.length + 1的数组
        int[] sums = new int[nums.length + 1];
        for (int i = 1; i < sums.length; i++) {
            // 给sums赋值  sum[i] = nums[0] + ... + nums[i - 1]
            sums[i] = sums[i - 1] + nums[i - 1];
        }
        for (int i = 1; i < sums.length; i++) {
            int target = s + sums[i - 1];
            int bound = Arrays.binarySearch(sums, target);
            if(bound < 0){
                // 没有找到位置，但找到了插入点（恢复插入点）
                bound = -bound - 1;
            }
            if(bound < sums.length){
                minLen = Math.min(minLen, bound - (i - 1));
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
