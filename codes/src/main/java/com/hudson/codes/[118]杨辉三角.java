package com.hudson.codes;//给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
//
// 
//
// 在杨辉三角中，每个数是它左上方和右上方的数的和。 
//
// 示例: 
//
// 输入: 5
//输出:
//[
//     [1],
//    [1,1],
//   [1,2,1],
//  [1,3,3,1],
// [1,4,6,4,1]
//] 
// Related Topics 数组 
// 👍 431 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution118 {
    // 100%  52.58%
    // 杨辉三角
    public static List<List<Integer>> generate(int numRows) {
        if(numRows <= 0) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {// 遍历每层
            List<Integer> item = new ArrayList<>();
            item.add(1);
            // 第i层有i个元素
            if(i == 1){
                result.add(item);
                continue;
            }
            List<Integer> last = result.get(result.size() - 1);
            int j = 0;
            int next = j + 1;
            // 我们当前层的结果来自上一层，即i-1层
            for (; j < i - 2 && next < i - 1; j++, next++) {
                // 把当前的j的位置与next位置的值加起来
                item.add(last.get(j) + last.get(next));
            }
            // 继续添加最后一个1
            item.add(1);
            result.add(item);
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
