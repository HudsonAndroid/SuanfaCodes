package com.hudson.codes;//给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,2]
//输出：
//[[1,1,2],
// [1,2,1],
// [2,1,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 8 
// -10 <= nums[i] <= 10 
// 
// Related Topics 回溯算法 
// 👍 539 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution47 {

    // 通过，时间超过25.38%，空间超过90.72%
    // 内部有重复的数字，那么我们需要去重
    // 最简单的办法就是在遍历的过程中跳过已经遍历过的数字
    // 有两种方案：1）使用HashSet判断是否已经遍历过  2）先排序，后在遍历过程中判断是否等于前一个数
    public static List<List<Integer>> permuteUnique(int[] nums) {
        if(nums == null || nums.length == 0) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        HashSet<Integer> iterateMap = new HashSet<>();
        List<Integer> item = new ArrayList<>();
        HashSet<Integer> usedIndexes = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if(iterateMap.contains(nums[i])){
                continue;
            }
            iterateMap.add(nums[i]);
            item.add(nums[i]);
            usedIndexes.add(i);
            permuteUniqueInner(nums, result,item, 1, usedIndexes);
            item.clear();
            usedIndexes.clear();
        }
        return result;
    }

    // usedIndexes用于存储前面位置中已经出现的数的下标，避免后面再次使用
    // index表示当前正在填入数字的下标
    private static void permuteUniqueInner(int[] nums, List<List<Integer>> result, List<Integer> item,
                                           int index, HashSet<Integer> usedIndexes){
        if(index >= nums.length){
            result.add(new ArrayList<>(item));
            return ;
        }
        HashSet<Integer> iterateMap = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if(iterateMap.contains(nums[i])) continue; // 如果已经遍历过该数，跳过
            if(usedIndexes.contains(i)) continue;// 该数前面的位置已经被使用过了，跳过
            iterateMap.add(nums[i]);
            item.add(nums[i]);
            usedIndexes.add(i);
            permuteUniqueInner(nums, result, item, index + 1, usedIndexes);
            // 由于前面可能存在该数，我们需要移除的是最后一个该数，实际上就是移除最后一个元素；而不是移除这个元素就行了【错误1，因为可能有重复】
//            item.remove((Integer)nums[i]);
            item.remove(item.size() - 1);// 移除最后一个元素，即递归前添加的元素
            usedIndexes.remove(i);
        }
    }


    // 方案2：先排序，通过，时间：25.38%，空间66.92%
    public static List<List<Integer>> permuteUnique2(int[] nums) {
        if(nums == null || nums.length == 0) return new ArrayList<>();
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> item = new ArrayList<>();
        HashSet<Integer> usedIndexes = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }
            item.add(nums[i]);
            usedIndexes.add(i);
            permuteUniqueInner2(nums, result,item, 1, usedIndexes);
            item.clear();
            usedIndexes.clear();
        }
        return result;
    }

    private static void permuteUniqueInner2(int[] nums, List<List<Integer>> result, List<Integer> item,
                                            int index, HashSet<Integer> usedIndexes){
        if(index >= nums.length){
            result.add(new ArrayList<>(item));
            return ;
        }
        for (int i = 0; i < nums.length; i++) {
            // 【错误1】要确保已经遍历过的不是已使用过的数
            if(i > 0 && nums[i] == nums[i - 1] && !usedIndexes.contains(i-1)) continue; // 如果已经遍历过该数，跳过
            if(usedIndexes.contains(i)) continue;// 该数前面的位置已经被使用过了，跳过
            item.add(nums[i]);
            usedIndexes.add(i);
            permuteUniqueInner2(nums, result, item, index + 1, usedIndexes);
            // 由于前面可能存在该数，我们需要移除的是最后一个该数，实际上就是移除最后一个元素；而不是移除这个元素就行了【错误1，因为可能有重复】
//            item.remove((Integer)nums[i]);
            item.remove(item.size() - 1);// 移除最后一个元素，即递归前添加的元素
            usedIndexes.remove(i);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
