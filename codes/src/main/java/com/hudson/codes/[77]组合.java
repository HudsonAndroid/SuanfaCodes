package com.hudson.codes;//给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
//
// 示例: 
//
// 输入: n = 4, k = 2
//输出:
//[
//  [2,4],
//  [3,4],
//  [2,3],
//  [1,2],
//  [1,3],
//  [1,4],
//] 
// Related Topics 回溯算法 
// 👍 455 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution77 {
    // 通过，时间：96.40%，空间：88.15%
    // 组合，即k个元素的组成的项，与顺序无关
    // 这个有点像滑动窗口
    // left 从1开始， right 从k开始
    // 从最后一位开始，不断使用后面没使用的数字替换它；
    // 倒数第二位时，从剩余的里面放在倒数第二位上，最后一位从剩余的里面遍历选择
    // 倒数第三位时，从剩余的里面放在倒数第三位上，倒数第二位从剩余的里面选择，最后一位也从继续剩余的里面选择
    public static List<List<Integer>> combine(int n, int k) {
        if(n < k) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        int left = 1;
        for (int i = k; i <= n; i++, left++) {
            List<Integer> item = new ArrayList<>();
            for (int j = 0; j < k; j++) {// 需要构建长度为k的结果
                item.add(j + left);
            }
            result.add(new ArrayList<Integer>(item));
            // 固定第一个元素，后面的从剩余的里面拿【后面不断遍历，其实相当于窗口后滑1个位置，这样就限定了只能使用窗口后面的元素，也就避免了重复使用前面的数了】
            iterateAll(item, result, k + left, n, 1);
        }
        return result;
    }

    private static void iterateAll(List<Integer> item, List<List<Integer>> result, int start, int end, int index){
        if(index > item.size()){
//            result.add(new ArrayList<Integer>(item));
            return ;
        }
        for (int i = index; i < item.size(); i++) {
            // 先修改第一个确定，可选范围start~end
            for (int j = start; j <= end; j++) {
                int old = item.get(i);
                item.set(i, j);
                result.add(new ArrayList<Integer>(item));
                // 继续确定当前值，后面的从剩余的里面拿
                iterateAll(item, result, j + 1, end, i + 1);
                // 回溯
                item.set(i, old);
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
