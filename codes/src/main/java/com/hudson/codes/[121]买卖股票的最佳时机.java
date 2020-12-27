package com.hudson.codes;//给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
//
// 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。 
//
// 注意：你不能在买入股票前卖出股票。 
//
// 
//
// 示例 1: 
//
// 输入: [7,1,5,3,6,4]
//输出: 5
//解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
//     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
// 
//
// 示例 2: 
//
// 输入: [7,6,4,3,1]
//输出: 0
//解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
// 
// Related Topics 数组 动态规划 
// 👍 1364 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution121 {
    // 61.56%  74.35%
    // 必须先买入，再卖出
    // 我们保留一个变量记录最小价格
    // 当然，后面有可能还会出现更小的值，这种情况下，需要把最小价格变量更新
    public static int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) return 0;
        int minPrice = prices[0];
        int maxProfit = 0;// 最大利润
        for (int price : prices) {
            int profit = price - minPrice;
            maxProfit = Math.max(maxProfit, profit);
            if(price < minPrice){
                // 那么我们更新最小价格
                minPrice = price;
            }
        }
        return maxProfit;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
