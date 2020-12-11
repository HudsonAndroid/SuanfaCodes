package com.hudson.codes;//给定一个 没有重复 数字的序列，返回其所有可能的全排列。
//
// 示例: 
//
// 输入: [1,2,3]
//输出:
//[
//  [1,2,3],
//  [1,3,2],
//  [2,1,3],
//  [2,3,1],
//  [3,1,2],
//  [3,2,1]
//] 
// Related Topics 回溯算法 
// 👍 1027 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution46 {
    // 简单， 这里直接使用了List.contains来判断是否已经出现过该数
    // 效率居然时间超过71.96%，空间超过98.14%，我擦嘞 ，这么烂的思路也行
    // 为什么我认为烂呢，因为List的contains方法是一个一个去比对，效率很低的
    // 还可以优化吗？
    public static List<List<Integer>> permute(int[] nums) {
        if(nums == null || nums.length == 0) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            // 第一个位置可以是三个数中的任意一个，但需要后续需要把它排除（题目意思没有重复，那么就可以直接用它比较）
            List<Integer> item = new ArrayList<>();
            item.add(nums[i]);
            permuteInner(nums, item, result, 1);
            item.clear();
        }
        return result;
    }

    private static void permuteInner(int[] nums, List<Integer> item, List<List<Integer>> result, int curIndex){
        if(curIndex >= nums.length) {
            // 【错误1】你必须创建新的List，避免被清理
            result.add(new ArrayList<Integer>(item));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if(!item.contains(nums[i])){// 题意是没有重复数字，因此可以这样
                item.add(nums[i]);
                permuteInner(nums, item, result, curIndex + 1);
                item.remove((Integer) nums[i]);
            }
        }
    }


    // 注意：优化仅对数据量庞大的情况下有优势。时间：15.58%，空间：94.25%

    // 优化：在上面的算法中，我们递归过程中都会去检测是否已经使用了该数字（数字不重复），而且是通过List.contains来完成的
    // 而contains方法是一个线性查找过程，如果元素数目庞大的情况下，效率相当低。因此我们需要考虑针对数目庞大情况下的优化。
    // 根据题意，每个数字都不会重复出现，而且我们只需要判断数字是否使用，即非黑即白，那么我们很容易想到使用二进制的一位
    // 表示是否已经使用了该数字。唯一的问题是，如果数字长度很长，比如超过32，那么一个int类型是不够存储数字出现状态的
    // 因为题目只是说nums内部是不重复的数字，但并没有说只是1-9这样的一位数。因此我们可以根据
    // (nums.length / 32) + (nums.length % 32 > 0 ? 1 : 0)决定需要多少个整数来存储状态
    // 注意，这些int数存储的是数字下标是否使用过
    public static List<List<Integer>> permute2(int[] nums) {
        if(nums == null || nums.length == 0) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        int mapLen = nums.length / 32 + (nums.length % 32 > 0 ? 1 : 0);
        int[] indexMap = new int[mapLen];
        for (int i = 0; i < nums.length; i++) {
            // 第一个位置可以是三个数中的任意一个，但需要后续需要把它排除（题目意思没有重复，那么就可以直接用它比较）
            List<Integer> item = new ArrayList<>();
            item.add(nums[i]);
            int index = i / 32;
            indexMap[index] = saveMap(indexMap[index], i);
            permuteInner(nums, item, result, 1, indexMap);
            item.clear();
            // 把整个map恢复初始状态，因为经过上面递归已经完成了该数的全排列
            Arrays.fill(indexMap, 0);
        }
        return result;
    }

    private static void permuteInner(int[] nums, List<Integer> item, List<List<Integer>> result, int curIndex, int[] indexMap){
        if(curIndex >= nums.length) {
            // 【错误1】你必须创建新的List，避免被清理
            result.add(new ArrayList<Integer>(item));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            int mapIndex = i / 32;
            int mapResult = saveMap(indexMap[mapIndex], i);
            if(mapResult != - 1){
                int oldValue = indexMap[mapIndex];
                indexMap[mapIndex] = mapResult;
                item.add(nums[i]);
                permuteInner(nums, item, result, curIndex + 1, indexMap);
                item.remove((Integer) nums[i]);
                // 恢复操作
                indexMap[mapIndex] = oldValue;
            }
        }
    }

    // 如果该位置上已经使用过，那么结果返回-1，否则返回更新后的数
    private static int saveMap(int map, int index){
        // 注意是做异或运算，不是做|运算，因为1左移后，后面位置都是0，如果做或运算会把原有的1转0
        return ((map >> index & 1) == 1) ? -1 : (map ^ (1 << index));
    }

}
//leetcode submit region end(Prohibit modification and deletion)
