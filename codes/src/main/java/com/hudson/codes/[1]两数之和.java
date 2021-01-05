package com.hudson.codes;//给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
//
// 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。 
//
// 
//
// 示例: 
//
// 给定 nums = [2, 7, 11, 15], target = 9
//
//因为 nums[0] + nums[1] = 2 + 7 = 9
//所以返回 [0, 1]
// 
// Related Topics 数组 哈希表 
// 👍 9683 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution1 {
    // 最简单的方法，使用哈希表，空间复杂度较高
    // 【思路】注意：可以边遍历边判断
    public int[] twoSum(int[] nums, int target) {
        if(nums != null){
            Map<Integer, Integer> valueIndexs = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if(valueIndexs.get(target - nums[i]) == null){
                    // we put it into map
                    valueIndexs.put(nums[i], i);
                }else{
                    // we find it
                    return new int[]{valueIndexs.get(target - nums[i]), i};
                }
            }
            return null;// cannot find
        }
        throw new IllegalArgumentException("The array cannot be null");
    }

    // 目前来看，如果返回下标的话，这种方式不适合
    // 方法二，避免使用空间复杂度，但是这种方式由于快排会打乱原有数组数字下标，因此只能返回数字对，而不是下标对
    public int[] twoSum2(int[] nums, int target) {
        // 对数组排序
        quickSort(nums, 0, nums.length - 1);
        int last = nums.length -1;
        int first = 0;
        while(first < last){
            int tmpSum = nums[last] + nums[first];
            if(tmpSum > target){
                last --;
            }else if(tmpSum < target){
                first ++;
            }else{
                return new int[]{nums[first], nums[last]};
            }
        }
        return null; // cannot find
    }

    private static  void quickSort(int[] nums, int start, int end){
        if(start >= end){
            return ;
        }
        int refer = nums[start];
        int oldStart = start;
        int oldEnd = end;
        while(start < end) {
            // find one less than refer (in other words, if >= continue)
            while(start < end && nums[end] >= refer) {
                end --;
            }
            // find one large than refer
            while(start < end && nums[start] <= refer) {
                start ++;
            }
            // we find two target number, swap them
            if(start < end){
                int tmp = nums[start];
                nums[start] = nums[end];
                nums[end] = tmp;
            }
        }
        // ok, at here, all large number is back, all smaller number is forward
        // we should swap the refer number to middle
        int tmp = nums[start];
        nums[start] = nums[oldStart];
        nums[oldStart] = tmp;

        // so, continue to do
        quickSort(nums, oldStart, start - 1);
        quickSort(nums, start + 1, oldEnd);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
