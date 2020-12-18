package com.hudson.codes;//给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
//
// 说明：解集不能包含重复的子集。 
//
// 示例: 
//
// 输入: nums = [1,2,3]
//输出:
//[
//  [3],
//  [1],
//  [2],
//  [1,2,3],
//  [1,3],
//  [2,3],
//  [1,2],
//  []
//] 
// Related Topics 位运算 数组 回溯算法 
// 👍 912 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution78 {
    // 时间95.4%, 空间86.41%
    // 很容易出错，难度较大
    // 所有子集，那么长度是1~nums.length之间
    // 因此分类讨论
    // 这道题与上一题有点类似
    public static List<List<Integer>> subsets(int[] nums) {
        if(nums == null || nums.length == 0) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        int len = nums.length;
        // 长度从1开始，滑动窗口一直不断增大，直到长度为len
        for (int i = 1; i <= len; i++) {
            int winLeft = 0;// 所有不同长度的窗口都是从0位置开始的
            // 滑动窗口每次确定一个子集后，后滑一个元素
            // 窗口长度是i，窗口左边界是winLeft，因此终止条件是 right = winLeft + i - 1 > nums.length - 1
            int winRight = winLeft + i - 1;
            while(winRight <= nums.length - 1){
                // 把当前窗口的值加入结果中
                List<Integer> item = new ArrayList<>();
                for (int winIndex = winLeft; winIndex <= winRight; winIndex++) {
                    item.add(nums[winIndex]);
                }
                result.add(new ArrayList<Integer>(item));
                // 根据当前的窗口，替换数字(第一个数字必须保证不变，这样能够去重，或者说，我们滑动窗口其实就是为了变化第一个数)
                iterateAll(result, item, winRight + 1, nums, 1);
                // 窗口后移
                winLeft++;
                winRight = winLeft + i - 1;
            }
        }
        // 当然，最后还有一个空的子集 []
        result.add(new ArrayList<Integer>());
        return result;
    }

    private static  void iterateAll(List<List<Integer>> result, List<Integer> item, int numStartIndex,int[] nums, int curIndex) {
        if(curIndex >= item.size()) return ; // 没有位置需要替换
        if(numStartIndex >= nums.length) return ;// 没有没被使用的数字了
        // numStartIndex是剩余数字的起始下标

        // 对curIndex及后面的位置用剩余的元素替换【错误2，我们实际是对curIndex, curIndex+1,...后面的位置进行修改】
        for (int j = curIndex; j < item.size(); j++) {
            // j是当前替换的位置
            // 对curIndex(j)位置用遍所有剩余的数字
            for (int i = numStartIndex; i < nums.length; i++) {
                int old = item.get(j);
                item.set(j, nums[i]);
                result.add(new ArrayList<Integer>(item));
                // curIndex位置确定后，还需要继续判断后面的位置的替换
                // 当前可使用数字的开始位置+1 【错误1，这里应该是i+1，而不是numStartIndex + 1】
                // 替换的位置+1
                iterateAll(result, item, i + 1, nums, j + 1);
                // 回溯
                item.set(j, old);
            }
        }
    }



    // 这个太骚了，但是毕竟难以理解
    public static List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, result, new ArrayList<Integer>());
        return result;
    }

    private static void backtrack(int i, int[] nums, List<List<Integer>> res, List<Integer> tmp){
        res.add(new ArrayList<Integer>(tmp));
        for (int j = i; j < nums.length; j++) {
            tmp.add(nums[j]);
            backtrack(j + 1, nums, res, tmp);
            tmp.remove(tmp.size() - 1);
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)
