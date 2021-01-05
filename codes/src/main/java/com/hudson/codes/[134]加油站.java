package com.hudson.codes;//在一条环路上有 N 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
//
// 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。 
//
// 如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。 
//
// 说明: 
//
// 
// 如果题目有解，该答案即为唯一答案。 
// 输入数组均为非空数组，且长度相同。 
// 输入数组中的元素均为非负数。 
// 
//
// 示例 1: 
//
// 输入: 
//gas  = [1,2,3,4,5]
//cost = [3,4,5,1,2]
//
//输出: 3
//
//解释:
//从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油。此时油箱有 = 0 + 4 = 4 升汽油
//开往 4 号加油站，此时油箱有 4 - 1 + 5 = 8 升汽油
//开往 0 号加油站，此时油箱有 8 - 2 + 1 = 7 升汽油
//开往 1 号加油站，此时油箱有 7 - 3 + 2 = 6 升汽油
//开往 2 号加油站，此时油箱有 6 - 4 + 3 = 5 升汽油
//开往 3 号加油站，你需要消耗 5 升汽油，正好足够你返回到 3 号加油站。
//因此，3 可为起始索引。 
//
// 示例 2: 
//
// 输入: 
//gas  = [2,3,4]
//cost = [3,4,3]
//
//输出: -1
//
//解释:
//你不能从 0 号或 1 号加油站出发，因为没有足够的汽油可以让你行驶到下一个加油站。
//我们从 2 号加油站出发，可以获得 4 升汽油。 此时油箱有 = 0 + 4 = 4 升汽油
//开往 0 号加油站，此时油箱有 4 - 3 + 2 = 3 升汽油
//开往 1 号加油站，此时油箱有 3 - 3 + 3 = 3 升汽油
//你无法返回 2 号加油站，因为返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。
//因此，无论怎样，你都不可能绕环路行驶一周。 
// Related Topics 贪心算法 
// 👍 556 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution134 {

    // 暴力破解法
    // 很明显，第一步的位置必然不可以使得最后剩下的汽油是负数，
    // 因此我们直接遍历
    public static int canCompleteCircuit1(int[] gas, int[] cost) {
        if(gas == null || cost == null || gas.length != cost.length) return -1;
        // 遍历所有可能的起始位置，如果从起始位置开始出现的负值，那么终止
        for (int i = 0; i < gas.length; i++) {
            if(gas[i] < cost[i]) continue;
            // 说明汽油有剩余，那么继续判断
            int left = gas[i] - cost[i];
            int j = i + 1;
            while(j != i && left >= 0){
                if(j >= gas.length){
                    j = 0;
                }
                left += gas[j] - cost[j];
                j ++;
            }
            if(left >= 0 && i == j){
                // 说明这个位置符合
                return i;
            }
        }
        return -1;
    }


    // 100%  83.29%
    // 本题使用遍历所有节点的方式
    // 或者说暴力破解，结果会超时。
    // 我们深入考虑
    // 我们的目标是，确保车走完全程过程，车上的油必须>=0，这样就能保证走完全程
    // 既然如此，我们画出消耗油的折线图，之后把折线图整体上移，使得所有的剩余油量
    // 都大于等于0，那么最低点的下一个位置就是我们的出发点。
    // 如果总油量大于等于总耗油量，那么必然存在一个位置使得能走完全程
    // 而这个位置就是上面折线图的最低点的下一个位置。为什么呢？
    // 该折线图，y = kx，如果k是小于0的，说明是耗油的，而k大于0则说明
    // 整体是增加油量的，假设如果可以跑完全程，那么从耗油量最多的下一位置
    // 开始，必然足够后面的油耗。
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        if(gas == null || cost == null || gas.length != cost.length) return -1;
        int leftOil = 0;
        int totalGas = 0;
        int totalCost = 0;
        int minOil = Integer.MAX_VALUE;
        int minOilIndex = -1;
        int len = gas.length;
        for (int i = 0; i < len; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            leftOil += gas[i] - cost[i];
            if(minOil > leftOil){
                minOil = leftOil;
                minOilIndex = i;
            }
        }
        if(totalGas < totalCost){
            return -1;
        }
        if(minOilIndex == -1) return -1;
        int result = minOilIndex + 1;
        return result >= len ? 0 : result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
