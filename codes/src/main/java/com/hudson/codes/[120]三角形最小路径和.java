package com.hudson.codes;//给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
//
// 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。 
//
// 
//
// 例如，给定三角形： 
//
// [
//     [2],
//    [3,4],
//   [6,5,7],
//  [4,1,8,3]
//]
// 
//
// 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。 
//
// 
//
// 说明： 
//
// 如果你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题，那么你的算法会很加分。 
// Related Topics 数组 动态规划 
// 👍 663 👎 0


import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution120 {

//    public static void main(String[] args){
//        ArrayList<List<Integer>> triangle = new ArrayList<>();
//        List<Integer> item = new ArrayList<>();
//        item.add(2);
//        triangle.add(item);
//        item = new ArrayList<>();
//        item.add(3);
//        item.add(4);
//        triangle.add(item);
//        item = new ArrayList<>();
//        item.add(6);
//        item.add(5);
//        item.add(7);
//        triangle.add(item);
//        item = new ArrayList<>();
//        item.add(4);
//        item.add(1);
//        item.add(8);
//        item.add(3);
//        triangle.add(item);
//        int i = minimumTotal(triangle);
//        System.out.println(" "+i);
//    }



    // 72.39%  27.90%
    // 咋一看，好像贪婪算法可以，但其实所有的贪婪算法都需要证明
    // 因此，不到万不得已，最好别用贪婪算法。
    // 其实这道题与之前的左上角到右下角的题目有些类似
    // 因此我们可以也考虑下动态规划
    // 我们定义i为行数，j为列数
    // 那么定义f(i,j)为到达i行j列的最小和，根据题意，这个结果等于
    // f(i,j) = min( f(i - 1)(j - 1), f(i - 1)(j) ) + a[i][j]
    // 考虑一种特殊情况，如果j=0，那么j-1是无效的，因此这种情况下
    // f(i,j) = f(i - 1)(j) + a[i][j]
    // 这样我们把所有位置数据填表，推导出结果即可。
    // 然而题目要求O（n）的空间复杂度，我们填表是一个二维数组，因此需要考虑优化
    // 实际上，我们当前行的结果，只需要上一层的结果即可，无需依赖之前的数据
    // 因此可以继续优化
    // 我们创建一个一维数组，存储上一层的所有情况即可，数组长度使用最底部的长度
    public static int minimumTotal(List<List<Integer>> triangle) {
        if(triangle == null || triangle.size() == 0) return -1;
        int size = triangle.get(triangle.size() - 1).size();
        int[] lastLevel = new int[size];// 记录上一层
        int[] curLevel = new int[size];//记录当前层
        // 初始化第一层的最小和，注意，数组的下标就是j (i不用考虑，因为我们都是从上一层获取)
        // 由于后面的比前面的数据量更多，因此不用考虑两个数据交换，数组中残留的数据的影响
        lastLevel[0] = triangle.get(0).get(0);
        for (int i = 1; i < triangle.size(); i++) {
            List<Integer> integers = triangle.get(i);
            for (int j = 0; j < integers.size(); j++) {
                if(j == 0){
                    curLevel[j] = lastLevel[0] + integers.get(j);
                }else{
                    // 【错误】这里需要判断越界的问题，即这个数在上一层没有对应的j，如果是则只能使用上一个数
                    int another;
                    if(j >= i){
                        another = lastLevel[j - 1];
                    }else{
                        another = lastLevel[j];
                    }
                    curLevel[j] = Math.min(lastLevel[j - 1], another) + integers.get(j);
                }
            }
            // 完成当前层之后，交换两个数组功能
            int[] tmp = lastLevel;
            lastLevel = curLevel;
            curLevel = tmp;
        }
        // 完成了最后一层，遍历最后一层，找出最小和（其实我觉得是不是可以在上面用一个临时遍历存储，这样可以避免后面的遍历）
        // 注意，最后一层存储在lastLevel中，因此被交换了
        int minSum = Integer.MAX_VALUE;
        for (int i : lastLevel) {
            minSum = Math.min(minSum, i);
        }
        return minSum;
    }


    // 其实还可以优化，我们这里是从顶部往下推导，其实从底部往上推导，最终我们根本不需要遍历查找，因为顶部只有一个元素，返回dp[0]即可
}
//leetcode submit region end(Prohibit modification and deletion)
