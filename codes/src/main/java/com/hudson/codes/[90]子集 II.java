package com.hudson.codes;//给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
//
// 说明：解集不能包含重复的子集。 
//
// 示例: 
//
// 输入: [1,2,2]
//输出:
//[
//  [2],
//  [1],
//  [1,2,2],
//  [2,2],
//  [1,2],
//  []
//] 
// Related Topics 数组 回溯算法 
// 👍 357 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution90 {
    // 时间：99.98%，空间82.73%
    // 按照第78题的思路来，只不过我们利用HashSet去重
    // 这里就使用另一种方式，排序去重
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        // 添加空
        result.add(new ArrayList<Integer>());
        backtrack(0, result,new ArrayList<Integer>(),nums);
        return result;
    }

    private static void backtrack(int index, List<List<Integer>> result, List<Integer> item, int[] nums){
        // 遍历当前index所有可能的取值，而且后续的取值不能再取前面的数
        for (int i = index; i < nums.length; i++) {// 当前index可以取值是后面中的所有元素
            // 判断是否与上一个数相同，如果相同，那么上一个数已经把当前i位置的数的结果处理过了，因此跳过
            // 【错误1】是在当前遍历过的列表中，如果已经遍历过，就跳过，因此i是需要大于index的，而不是0,
            // 因为有可能当前要求的是边界上的，例如i=index，而i与index-1相同，这时就会被误当成不用遍历了
            if(/*i > 0*/i > index && nums[i] == nums[i - 1]) continue;
            item.add(nums[i]);
            // 当前符合情况，直接加入
            result.add(new ArrayList<Integer>(item));
            // 继续判定后面的，因为可能可以继续添加
            backtrack(i + 1,result,item, nums);
            // 回溯
            item.remove(item.size() - 1);// 移除最后一个
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
