package com.hudson.codes;//给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//
// candidates 中的每个数字在每个组合中只能使用一次。 
//
// 说明： 
//
// 
// 所有数字（包括目标数）都是正整数。 
// 解集不能包含重复的组合。 
// 
//
// 示例 1: 
//
// 输入: candidates = [10,1,2,7,6,1,5], target = 8,
//所求解集为:
//[
//  [1, 7],
//  [1, 2, 5],
//  [2, 6],
//  [1, 1, 6]
//]
// 
//
// 示例 2: 
//
// 输入: candidates = [2,5,2,1,2], target = 5,
//所求解集为:
//[
//  [1,2,2],
//  [5]
//] 
// Related Topics 数组 回溯算法 
// 👍 451 👎 0


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution40 {
    // 题目有点恶心，花费时间较长，时间复杂度超过30.25%，空间复杂度超过92.15%
    // 与第39题最大的区别是，输入数组可以包含重复数字，并且结果中的元素必须仅使用一次，不能重复使用
    // 虽然这个变了，但思路还是类似的。
    // 由于输入数组的数字可以重复，那么就不能使用HashSet了，而应该使用HashMap了。同样的，结果中一旦
    // 某个数被使用了，那么该数在Map中的次数就减少一次。其他思路与39题一致，确保结果的item的数组是按照递增顺序的
    // 为什么要确保结果的一项必须是递增的数组呢（包含等于的情况）
    // 我们遍历整个输入数组过程中，我们是先确定第一个数的位置的，如果我们允许找比第一个数小的数（后面的数递增）
    // 那么在遍历整个输入数组的时候再遍历到该更小的数的时候，就会重复把我们之前遍历的那个数纳入，因此
    // 我们如果确保从第一个数开始，后面找到的数一定比前一个数大的话，就避免了这种情况的发生。

    // 值得注意的是，遍历输入数组中可能出现重复数
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if(candidates == null || candidates.length == 0) return new ArrayList<>();
        List<List<Integer>> combines = new ArrayList<>();
        // 建立Map表
        Map<Integer,Integer> countMap = new HashMap<>();
        for (int candidate : candidates) {
            Integer count = countMap.get(candidate);
            if(count == null){
                countMap.put(candidate, 1);
            }else{
                countMap.put(candidate, ++count);
            }
        }
        List<Integer> result = new ArrayList<>();
        // 针对直接等于target的情况
        if(countMap.get(target) != null){
            result.add(target);
            combines.add(new ArrayList<Integer>(result));
            result.clear();
        }
        HashSet<Integer> searchMap = new HashSet<>();// 避免重复查询Map表
        // 针对其他情况【注意，与第39题不同的是，由于本题允许输入数组重复数字，因此在循环找的过程中，如果之前有找过该数，
        // 则需要跳过，因为每轮循环状态都是重置的或者说是初始状态】
        for (int i = 0; i < candidates.length; i++) {
            int candidate = candidates[i];
            if(searchMap.contains(candidate)){
                continue;// 以该数作为基准的查询已经做过，因此跳过
            }
            int offset = target - candidate;
            if(offset < 0 || offset < candidate){
                continue;
            }
            result.add(candidate);
            Integer count = countMap.get(candidate);
            if(count != null ){// 不需要判断是否>0，因为必然>0，其实是否是null也可以不用判断
                countMap.put(candidate, --count); // 使用一次
                findIntegers(combines, result, candidates, offset, countMap, candidates[i]);
                // 恢复使用次数
                countMap.put(candidate, ++count);
            }
            result.clear();// 这一轮查询完成，清空，重新开始
            searchMap.add(candidate);
        }
        return combines;
    }

    // 注意：在找的过程中可以找与参考位置相等的数字，但不能找比它小的数
    private static void findIntegers(List<List<Integer>> combines, List<Integer> result,int[] candidates,
                                     int offset, Map<Integer, Integer> countMap, int refer){
        Integer count = countMap.get(offset);
        if(count != null && count > 0){// 说明该数字还可以使用
            List<Integer> item = new ArrayList<>(result);
            item.add(offset);
            combines.add(item);
            // 由于这里已经使用了之后就没有后续了，因此不需要对countMap的使用次数就行修改
        }
        // 由于输入数组包含重复的数字，因此我们这里要判断上面的结果是否可能与下面的结果重复
        // 例如 {2,1,1},1，上面执行到第一1的时候，查看剩余结果是1，因此添加；但下面的循环中又会重新把1加入，因此下面的判断
        // 需要增加一条，找到的数不能与最初的剩余值offset相同

        // 注意：我们可以使用之前的数字，因为我们去重的操作是通过找递增数组完成的，不是起始index
        // 但是我们不能在循环中重复找某个数，因此需要借助HashSet
        HashSet<Integer> searchMap = new HashSet<>();
        for (int candidate : candidates) {
            if(searchMap.contains(candidate)) continue; // 如果这个数字之前当过第一个数，那么跳过
            if(candidate < refer) continue;// 确保后面输出不能小于refer
            int over = offset - candidate;
            // 为了保证结果是递增的数组，剩余的数over必须不能小于refer，也不能小于当前的数字
            if(over < 0 || over < refer || over < candidate || candidate == offset){// over < candidate的话，无法保证结果是按照递增顺序（或者等于的顺序）
                continue;
            }
            count = countMap.get(candidate);
            if(count != null && count > 0){// 这里必须判断是否>0,
                result.add(candidate);// 只有还有使用次数才能加入
                countMap.put(candidate, --count); // 使用一次
                findIntegers(combines,result, candidates, over, countMap, candidate);
                countMap.put(candidate, ++count);// 恢复使用次数
                result.remove((Integer) candidate);// 恢复现场，回溯
                searchMap.add(candidate);
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
