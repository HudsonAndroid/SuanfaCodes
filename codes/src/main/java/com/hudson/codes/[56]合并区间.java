package com.hudson.codes;//给出一个区间的集合，请合并所有重叠的区间。
//
// 
//
// 示例 1: 
//
// 输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
//输出: [[1,6],[8,10],[15,18]]
//解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
// 
//
// 示例 2: 
//
// 输入: intervals = [[1,4],[4,5]]
//输出: [[1,5]]
//解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。 
//
// 注意：输入类型已于2019年4月15日更改。 请重置默认代码定义以获取新方法签名。 
//
// 
//
// 提示： 
//
// 
// intervals[i][0] <= intervals[i][1] 
// 
// Related Topics 排序 数组 
// 👍 728 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution56 {
    // 时间：81.75%，空间：88.66%
    // 思路很简单，能合并的两个区间，必然是起始小的那个的最大值
    // 大于等于起始大的那个的最小值。
    // 因此我们把所有数按照左区间排序，然后逐个尝试合并。
    public static int[][] merge(int[][] intervals) {
        if(intervals == null || intervals.length == 0) return new int[0][2];
        // 排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        // 合并
        // 合并的条件是前一个的右区间大于或等于后一个的左区间
        List<int[]> result = new ArrayList<>();
        result.add(intervals[0]);// 先加入一个，假设它不会与后面的重合，后面真正要合并，只是在它的基础上改
        for (int i = 1; i < intervals.length; i++) {
            // 【错误1】实际上我们应该和已经合并的区间判断
            // 判断当前的区间是否可以和上一个区间合并
            int[] last = result.get(result.size() - 1);
            if(intervals[i][0] <= last[1]){
                // 可以合并，那么留住更小的数作为左区间，留住更大的数，作为右区间
                last[1] = Math.max(intervals[i][1], last[1]);
                // 不需要再次加入，只需要修改元素的值
            }else{
                // 无法合并的情况下，继续先加入下一个数组，假设它不会与后面的重合
                result.add(intervals[i]);
            }
        }
        int[][] target = new int[result.size()][2];
        return result.toArray(target);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
