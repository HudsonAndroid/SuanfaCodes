package com.hudson.codes;//给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//
// candidates 中的数字可以无限制重复被选取。 
//
// 说明： 
//
// 
// 所有数字（包括 target）都是正整数。 
// 解集不能包含重复的组合。 
// 
//
// 示例 1： 
//
// 输入：candidates = [2,3,6,7], target = 7,
//所求解集为：
//[
//  [7],
//  [2,2,3]
//]
// 
//
// 示例 2： 
//
// 输入：candidates = [2,3,5], target = 8,
//所求解集为：
//[
//  [2,2,2,2],
//  [2,3,3],
//  [3,5]
//] 
//
// 
//
// 提示： 
//
// 
// 1 <= candidates.length <= 30 
// 1 <= candidates[i] <= 200 
// candidate 中的每个元素都是独一无二的。 
// 1 <= target <= 500 
// 
// Related Topics 数组 回溯算法 
// 👍 1083 👎 0


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution39 {
    // 通过：时间复杂度超过37.79%的用户，空间复杂度超过98.24%的用户
    // 优化：通过在下面的补充去重处理后，时间复杂度提高到超过85.45%的用户，空间复杂度为96.37%
    // 我们按照结果递增的方式计算，例如 {2,3,6,7},7，那么结果有一个是[2,2,3]， 而不是[3,2,2]或[2,3,2]，这样有助于去重
    // 思路：让目标值从减去0、减去第一个数，减去第二个数等开始，用剩下的值继续重新从减去0、减去第一个数、减去第二个数等开始
    // 直到找不到数为止。

    // 为什么要确保结果的一项必须是递增的数组呢（包含等于的情况）
    // 我们遍历整个输入数组过程中，我们是先确定第一个数的位置的，如果我们允许找比第一个数小的数（后面的数递增）
    // 那么在遍历整个输入数组的时候再遍历到该更小的数的时候，就会重复把我们之前遍历的那个数纳入，因此
    // 我们如果确保从第一个数开始，后面找到的数一定比前一个数大的话，就避免了这种情况的发生
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        if(candidates == null || candidates.length == 0) return new ArrayList<>();
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < candidates.length; i++) {
            hashSet.add(candidates[i]);
        }
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        if(hashSet.contains(target)){
            result.add(target);
            list.add(new ArrayList<Integer>(result));
            result.clear();
        }
        for (int i = 0; i < candidates.length; i++) {
            int offset = target - candidates[i];
            if(offset < 0 || offset < candidates[i]){ // 补充去重处理，避免后面进入方法再判断，减少了无效add和清理操作
                continue;
            }
            result.add(candidates[i]);
            // refer用于避免重复
            findIntegers(hashSet, offset, result, candidates, list, candidates[i]);
            result.clear();
        }
        return list;
    }

    private static void findIntegers(HashSet<Integer> hashSet, int offset, List<Integer> result,
                                     int[] candidates, List<List<Integer>> combines, int refer) {
        if(offset < refer) return;// 确保后面出来的数必须大于或等于refer，以避免重复
        if(hashSet.contains(offset)){
//            result.add(offset);
            List<Integer> finalResult = new ArrayList<>(result);
            finalResult.add(offset);
            combines.add(finalResult);
        }
        for (int candidate : candidates) {
            if(candidate < refer) continue; // 确保后面出来的数必须大于或等于refer，以避免重复
            int left = offset - candidate;
            // 确保后面出来的数必须大于或等于refer，以避免重复
            // 为了确保结果是递增的，剩余值left必须不能小于refer，也不能小于当前数
            if(left < 0 || /*left < refer ||*/ left < candidate ){
                continue;
            }
            result.add(candidate);
            findIntegers(hashSet, left, result, candidates, combines, candidate);
            result.remove((Integer) candidate);// 回溯
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)
