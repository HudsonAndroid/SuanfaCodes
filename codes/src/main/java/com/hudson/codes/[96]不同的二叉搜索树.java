package com.hudson.codes;//给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
//
// 示例: 
//
// 输入: 3
//输出: 5
//解释:
//给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
//
//   1         3     3      2      1
//    \       /     /      / \      \
//     3     2     1      1   3      2
//    /     /       \                 \
//   2     1         2                 3 
// Related Topics 树 动态规划 
// 👍 922 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution96 {
    // 通过，时间只要5%，空间84.80%
    // 分析
    // 很清晰的思路：遍历1~n，所有遍历到的作为根节点
    // 其实我们分析n=3的情况可以发现，所有结果
    // 其实是以根节点是2的树分开的两部分类似，只不过上下颠倒了的，因此可以根据
    // n的情况，计算一半即可。例如n是奇数，结果是1~n/2的结果*2 + 中间数
    // 如果n是偶数，那么只需要计算1~n/2即可，最后乘以2。（注意，1~n/2之间不能再简化，因此取值范围是1~n）
    public static int numTrees(int n) {
        if(n <= 0) return 0;
        if(n == 1) return 1;
        if(n == 2) return 2;
        int target = n >> 1;
        int count = 0;
        for (int i = 1; i <= target; i++) {
            // 遍历根节点的情况
            count += backtrack(1, n, i);
        }
        count = count << 1; // 结果翻倍
        if((n & 1) != 0){// 如果是奇数，还需要进一步判断中间的情况
            count += backtrack(1, n, target + 1);
        }
        return count;
    }

    // 根据root把整体划分为左边（左子树）和右边（右子树）
    private static int backtrack(int leftStart, int rightEnd, int root){
        int leftMul = 0;
        // 【错误】，leftStart错误写成了<=1
        if(leftStart < 1 || leftStart >= root){
            // 没有左节点的情况
        }else{
            // 有左节点，统计左节点可以出现的次数
            for (int i = leftStart; i < root; i++) {
                // 以i作为左根节点，所有的左子树，最大（最右）是root-1
                // 【错误】左右边界确认出错，左边界哪能是i-1
                leftMul += backtrack(/*i - 1*/leftStart, root - 1, i);
            }
        }
        int rightMul = 0;
        if(rightEnd <= root){// 如果右节点的终止数小于等于根，则说明没有右节点

        }else{
            for (int i = root + 1; i <= rightEnd; i++) {
                // 所有右子树，最小（最左）是root+1
                // 【错误】左右边界确认出错
                rightMul += backtrack(root + 1, rightEnd, i);
            }
        }
        return (leftMul == 0 ? 1 : leftMul)  * (rightMul == 0 ? 1 : rightMul);
    }


    // 时间100%， 空间84.27%
    // 脑子瓦特了，没有想到动态规划
    // 令f(i)表示以i（i不是位置，是数字）为根节点的数目，
    // 令g(n)表示n个节点可以形成的数目
    // 那么
    // f(i) = g(i - 1) * g(n - i)
    // 而g(n)=f(1) + f(2) + ... + f(n)
    // 我们最终求的就是g(n)
    public static int numTrees2(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;// 这样才能保证后面结果正常
        dp[1] = 1;
        if(n == 1) return 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            // 它的结果是前面所有f(i)的和
            for (int j = 1; j <= i; j++) {
                // 累加 f(j) = g(j - 1) * g(i - j)，注意把j当成i，外部循环的i当成n
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
