package com.hudson.codes;//给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
//
// 
//
// 在杨辉三角中，每个数是它左上方和右上方的数的和。 
//
// 示例: 
//
// 输入: 3
//输出: [1,3,3,1]
// 
//
// 进阶： 
//
// 你可以优化你的算法到 O(k) 空间复杂度吗？ 
// Related Topics 数组 
// 👍 211 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution119 {
    // 80.25%   81.08%
    // 要求k的空间复杂度
    // 实际上我们再118题中是多个集合存储在一个外部集合中
    // 但实际上，我们的结果都是依赖上一个集合的结果的。为此，我们必须保留两个集合
    // 一个是当前的集合，另一个是上一个集合。
    // 仔细观察
    //     1
    //    1 1
    //   1 2 1
    //  1 3 3 1
    // 1 4 6 4 1
    // 会发现，比如第3行到第4行，实际是 1 + 2 = 3插入在原来集合的2之前，之后继续 2 + 1 = 3，继续插入到
    // 原来的3后面，发现没有多余的元素了，则再把原来多余的元素删除，最后再补个1。
    // 但实际这中方式并不是最佳解
    // 我们实际每一行的元素只比上一行多一个元素，我们能不能直接再原有数组的基础上改，最后补一个1即可呢？
    // 实际上 1 + 2 = 3，然后会替换掉原来2位置的值；这样由于后续2还会被使用，这将无法继续下去；
    // 既然如此，我们从后往前找，我们发现，后面的元素修改了之后，并不影响前面的做加法操作
    // 例如
    // 1 2 1
    // 1 3 3  （1最后的1排除）
    // 实际是这样得到的，从后往前找1 2 1 ->  1 2 3 -> 1 3 3 -> 最后补1 -> 1 3 3 1
    public static List<Integer> getRow(int rowIndex) {
        if(rowIndex </*=*/ 0) return new ArrayList<>();
        // shit，按照leetcode的题目意思，第k行是从0开始的，wtf?
        rowIndex = rowIndex + 1;
        List<Integer> result = new ArrayList<>();
        result.add(1);
        for (int i = 1; i < rowIndex; i++) {// 行遍历
            // 从后面往前找，第一个元素不用动
            for(int j = i - 1; j > 0; j--){
                result.set(j, result.get(j) + result.get(j - 1));
            }
            // 最后补一个1
            result.add(1);
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
