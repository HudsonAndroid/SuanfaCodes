package com.hudson.codes;//给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复
//的三元组。 
//
// 注意：答案中不可以包含重复的三元组。 
//
// 
//
// 示例： 
//
// 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
//
//满足要求的三元组集合为：
//[
//  [-1, 0, 1],
//  [-1, -1, 2]
//]
// 
// Related Topics 数组 双指针 
// 👍 2785 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution15 {
    // 提交后，时间超过17%，空间超过38%，我擦嘞

    // 因为是找三个数，因此无法直接通过Map存储下标的方式
    // 这样我们可以固定第一个数，然后用双指针头尾剩余的数字
    // 由于双指针的方式需要数组是排好序的，因此需要先对数组排序
    // 值得注意的是，题目要求不重复的组合，因此一旦确认过该数字，
    // 后续的数字不能再找到它，因此需要一个下标记录当前已经
    // 找过的数字; 同时如果数组中存在相同的元素，也要避免出现重复
    // 组合问题
    // 【优化】值得注意的是：如果最小数都 >0，那么必然不会出现等于0的结果
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length == 0) return result;
        // 对数组排序  使用Arrays.sort()更快，我擦
        quickSort(nums, 0, nums.length - 1);
//        Arrays.sort(nums);
        // 遍历数组，遍历中去重，重复的情况有两种：遍历到的数字与之前的重复、固定遍历数字，双指针移动的两次头尾情况一样
        // 由于我们遍历要找3个数，因此最多遍历到 length - 3（包含该位置）
        for (int i = 0; i < nums.length - 2; i++) {
            if(i > 0 && nums[i] == nums[i - 1]){
                // 当前数字和上次的数字一样，跳过
                continue;
            }
            // 双指针查找对应位置
            int left = i + 1; // 因为我们不能找当前位置之前的数
            int right = nums.length - 1;
            // 【优化】最小数必须小于0  【错误1：未考虑到 [0,0,0]的情况，因此需要增加=号】
            while (left < right && nums[i] <= 0){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum == 0){
                    List<Integer> item = new ArrayList<>();
                    item.add(nums[i]);
                    item.add(nums[left]);
                    item.add(nums[right]);
                    result.add(item);
                    left ++;
                    right --;
                    // 去重
                    while(left < right && nums[right] == nums[right + 1]){
                        right --;
                    }
                    while(left < right && nums[left] == nums[left - 1]){
                        left ++;
                    }
                }else if(sum > 0){
                    // 数字太大了，因此前移right
                    right--;
                    // 讲真，leetcode中执行时，去掉这部分优化，还更快，wtf？
                    // 优化，如果新数等于之前的数，直接跳过【注意：应该直到找到不等于之前的位置】
//                    while(left < right && nums[right] == nums[right + 1]){
//                        right --;
//                    }
                }else{
                    // 数字太小了，因此后移left
                    left ++;
//                    while(left < right && nums[left] == nums[left - 1]){
//                        left ++;
//                    }
                }
            }
        }
        return result;
    }

    private static void quickSort(int[] nums, int start, int end){
        if(start >= end){
            return ;
        }
        // 取第一个数作为参考数
        int refer = nums[start];
        int oldStart = start;
        int oldEnd = end;
        while(start < end){
            // 找到一个比参考数小的
            while(start < end && nums[end] >= refer){
                end --;
            }
            // 找一个比参考数大的
            while(start < end && nums[start] <= refer){
                start ++;
            }
            if(start < end){
                int tmp = nums[start];
                nums[start] = nums[end];
                nums[end] = tmp;
            }
        }
        // 找完之后，需要把参考数换到中间去
        int tmp = nums[start];
        nums[start] = nums[oldStart];
        nums[oldStart] = tmp;

        quickSort(nums, oldStart, start - 1);
        quickSort(nums, start + 1, oldEnd);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
