package com.hudson.codes;//给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c +
// d 的值与 target 相等？找出所有满足条件且不重复的四元组。 
//
// 注意： 
//
// 答案中不可以包含重复的四元组。 
//
// 示例： 
//
// 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
//
//满足要求的四元组集合为：
//[
//  [-1,  0, 0, 1],
//  [-2, -1, 1, 2],
//  [-2,  0, 0, 2]
//]
// 
// Related Topics 数组 哈希表 双指针 
// 👍 684 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution18 {
    // 该题与15题一模一样的解法，更多详见第15题
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        if(nums == null) return null;
        List<List<Integer>> result = new ArrayList<>();
        if(nums.length == 0) return result;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            // 去重
            if(i > 0 && nums[i] == nums[i - 1]){
                // 如果上一次有相同的，那么针对该数的所有组合都已经找过一遍了，不需要再找了
                continue;
            }
            // 固定第一个元素，遍历剩下的元素
            for (int j = i + 1; j < nums.length - 2; j++) {
                // 错误1：在确定第一个元素的情况下，第二个元素重复也要去除
                // 错误2： 第一个元素需要避免判断重复，因为他有可能跟num[i]相同，即判断 j > i + 1
                if(j > i + 1 && nums[j] == nums[j-1]){
                    continue;
                }
                int left = j + 1;
                int right = nums.length - 1;
                while(left < right){//遍历固定前两个数字的所有情况
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if(sum == target){
                        List<Integer> item = new ArrayList<>();
                        item.add(nums[i]);
                        item.add(nums[j]);
                        item.add(nums[left]);
                        item.add(nums[right]);
                        result.add(item);

                        left ++;
                        right --;
                        // 跳过重复
                        while(left < right && nums[left] == nums[left - 1]) left ++;
                        while (left < right && nums[right] == nums[right + 1]) right --;
                    }else if(sum > target){
                        right --;
                    }else{
                        left ++;
                    }
                }
            }
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
