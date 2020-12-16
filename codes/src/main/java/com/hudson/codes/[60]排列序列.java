package com.hudson.codes;//给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。
//
// 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下： 
//
// 
// "123" 
// "132" 
// "213" 
// "231" 
// "312" 
// "321" 
// 
//
// 给定 n 和 k，返回第 k 个排列。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3, k = 3
//输出："213"
// 
//
// 示例 2： 
//
// 
//输入：n = 4, k = 9
//输出："2314"
// 
//
// 示例 3： 
//
// 
//输入：n = 3, k = 1
//输出："123"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 9 
// 1 <= k <= n! 
// 
// Related Topics 数学 回溯算法 
// 👍 450 👎 0


import java.util.HashSet;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution60 {
    // 时间超过99.99%的用户，空间超过：91.49%
    // 分析：
    // n = 3的情况下
    // 实际上从小到大排列的话，每个数字处在第一个位置的情况有n-1的阶乘中情况
    // 因此，我们可以求得k%（n-1阶乘）的余数，继续使用第二个数字判断...如此直到找到目标
    // 这种方法不断接近目标。值得注意的是，每次我们确定一部分排除的时候
    // 我们就能确定对应高位的值，例如第一次判断，我们能确定最高位的数是什么。
    // 很明显，这是一个递归过程，每次确定一位的数字。
    // 另外，我们前面出现过了数字后面不能再出现，因此需要一个HashSet存储
    public static String getPermutation(int n, int k) {
        StringBuilder result = new StringBuilder();
        confirmNumber(result, n, k, n, new HashSet<Integer>());
        return result.toString();
    }

    private static int getNMul(int n){
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private static void confirmNumber(StringBuilder sb, int n, int k, int maxNum, HashSet<Integer> hashSet){
        if(n == 1){
            // 只剩最后一位，直接判断即可
            for (int i = 1; i <= maxNum; i++) {
                if(!hashSet.contains(i)){
                    sb.append(i);
                    return ;
                }
            }
            return ;
        }
        int averageCount = getNMul(n - 1);
        int offset = (k - 1) / averageCount;// 找出当前数相对最开始位置的offset
        // 【错误1】不能仅判断是否该数出现过，然后++，实际calculateNum是剩余数的第calculateNum大的数
        int calculateNum = offset + 1;
//        // 有可能该数已经出现过，因此需要判断
//        while(hashSet.contains(calculateNum)/* && calculateNum <= n*/){
//            // 如果出现过，那么往后增加
//            calculateNum++;
//        }
        // 从1开始往后遍历找剩余数中第calculateNum大的数
        int sort = 1;
        int target = 1;
        for (int i = 1; i <= maxNum; i++) {
            if(!hashSet.contains(i)){
                if(calculateNum == sort) {
                    target = i;
                    break;
                }
                sort++;
            }
        }
        // 把该数添加到位中
        sb.append(target);
        hashSet.add(target);
        confirmNumber(sb, n - 1, k - offset * averageCount, maxNum, hashSet);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
