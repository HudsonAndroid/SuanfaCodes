package com.hudson.codes;//给定一个字符串 s1，我们可以把它递归地分割成两个非空子字符串，从而将其表示为二叉树。
//
// 下图是字符串 s1 = "great" 的一种可能的表示形式。 
//
//     great
//   /    \
//  gr    eat
// / \    /  \
//g   r  e   at
//           / \
//          a   t
// 
//
// 在扰乱这个字符串的过程中，我们可以挑选任何一个非叶节点，然后交换它的两个子节点。 
//
// 例如，如果我们挑选非叶节点 "gr" ，交换它的两个子节点，将会产生扰乱字符串 "rgeat" 。 
//
//     rgeat
//   /    \
//  rg    eat
// / \    /  \
//r   g  e   at
//           / \
//          a   t
// 
//
// 我们将 "rgeat” 称作 "great" 的一个扰乱字符串。 
//
// 同样地，如果我们继续交换节点 "eat" 和 "at" 的子节点，将会产生另一个新的扰乱字符串 "rgtae" 。 
//
//     rgtae
//   /    \
//  rg    tae
// / \    /  \
//r   g  ta  e
//       / \
//      t   a
// 
//
// 我们将 "rgtae” 称作 "great" 的一个扰乱字符串。 
//
// 给出两个长度相等的字符串 s1 和 s2，判断 s2 是否是 s1 的扰乱字符串。 
//
// 示例 1: 
//
// 输入: s1 = "great", s2 = "rgeat"
//输出: true
// 
//
// 示例 2: 
//
// 输入: s1 = "abcde", s2 = "caebd"
//输出: false 
// Related Topics 字符串 动态规划 
// 👍 171 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution87 {
    // 根据题意，必然最终结果是分割成原始字符相邻的两大块，
    // 什么意思呢？假设原始字符串是s，分割成子节点是S1+S2
    // 选择一个非叶子节点，结果中S1内部的字符是不会发生变化的
    // S2也一样，只不过可能顺序会发生变化。即使你选择根节点
    // 交换也一样。
    // 也就是说，一旦某一个节点下面已经分成了两个子节点之后，
    // 两个子节点中的元素不会出现某个元素跳到另一个子节点中去的情况（注意：
    // 如果有重复字符，我们是以面向对象的方式思考的，也是符合要求的）
    // 例如 原始是abcde，扰乱字符串是caebd，我们会发现，caebd无法
    // 被abcde正确的分割成两部分（这两部分字符相同，顺序可能不同），因此不符合要求
    // 【总结】也就是说s = (s1) + (s2)
    // 扰乱字符串s' = (s1的的可能结果) + (s2的可能结果) || (s2的可能结果) + (s1的的可能结果)
    // 很明显，这便转换为了一个递归的问题了
    // （注意：这里不是动态规划，例如 abcd + e，实际e对结果有影响，会出现a bcde这种情况，
    // 因为我们动态规划计算中，只能求出上一个的dp，但像上面这样，增加e后的dp != s1(abcd所有情况) + s2(e所有情况)，而是还要考虑a bcde, ab cde。。。4)）

    // 法1：超出时间限制，哈哈哈，必然的
    public static boolean isScramble(String s1, String s2) {
        // 因此题目意思就变成了,保持扰乱字符串s2不变，尝试把s1转成s2
        if(s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) return false;
        // 如果两个字符串长度不同，直接不符合
        if(s1.length() != s2.length()) return false;

        List<String> result = maybeStr(s1);
        for (String s : result) {
            if(s.equals(s2)){
                return true;
            }
        }
        return false;
    }

    private static List<String> maybeStr(String s){
        List<String> result = new ArrayList<>();
        if(s.length() == 1){
            result.add(s);
            return result;
        }
        // 需要逐步分割，从1开始，直到len - 2（包括）【错误1】遍历条件出错
        // A B C 可以分割2次，因此次数是 len - 1
        List<String> s1,s2;
        for (int i = 0; i < s.length() - 1; i++) {
            // 不包含末尾的，因此是i，从下标0开始
            s1 = maybeStr(s.substring(0, i + 1));
            s2 = maybeStr(s.substring(i + 1));
            // 可能不交换，也可能交换
            for (String first : s1) {
                for (String second : s2) {
                    result.add(first + second);
                    result.add(second + first);
                }
            }
        }
        return result;
    }


    // 其实呢，本题实际还是可以用动态规划思路解决的
    // 令 f(i, j)为所有可能结果(i < j)，那么f(i, j) = f(i, i) + f(i + 1, j) || ...
    // 这将会导致结果很复杂，不仅如此，其实我们只是想判断是否相等，而不是所有情况都要尝试
    // 已经发现了就不需要继续了

    // 实际上，s = s1 + s2，那么判断s1和s2的是否与目标s'的结果相同
    // 那么我们应该边分组边对比两边是否符合
    // 在s1 = great中头部取一部分,例如gr
    // 在s2 = rgeat中取相等长度的一部分，那么该部分可能在前面，也可能在最后
    // 因此分两种情况
    // 1) 取rg，那么gr与rg的情况是否符合呢？需要进一步判断【相当于s1=gr,s2=rg，递归】
    //   1.1）继续在gr中头部取一部分，在rg中取等长的一部分，需要进一步判断
    //      1.1.1)继续。。。
    // 2) 取at，那么gr与ta的情况是否符合呢？需要进一步判断【相当于s1=gr,s2=at，递归】
    //   2.1)继续在gr中头部取一部分，在at中取等长的一部分，需要进一步判断
    //      2.1.1)继续。。。
    // 3）接着继续判断剩下部分，因为上面判断了gr部分，那么应该在上面两种情况下继续判断剩余内容是否符合要求
    // 例如，假设我们取了rg，那么剩余的是eat；第二个字符串我们取了rg，那么也是eat，那么这两个的怎么判断
    // 是否符合要求呢？跟前面的一样。
    // 实际我们发现，上述结果其实唯一能真正计算出来的就是单个字符的情况。
    // 令dp[i][j][len]表示从s1中i开始长度为len的字符串是否能转变成从s2中j开始长度为len的字符串
    // 讲真，这种解法不是我做出来的，题解我也看了半天
    public static boolean isScramble2(String s1, String s2){
        char[] chs1 = s1.toCharArray();
        char[] chs2 = s2.toCharArray();
        int n = s1.length();
        int m = s2.length();
        if(n != m) return false;
        boolean[][][] dp = new boolean[n][m][n + 1];// n=m,至于为什么+1，因为不存在len=0的情况，因此需要多一位存储
        // 先计算单个字符从情况
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j][1] = chs1[i] == chs2[j];
            }
        }
        // 枚举长度为2~n的情况
        for (int len = 2; len <= n; len++) {
            // 枚举s1中起点位置，并保持长度
            for (int i = 0; i <= n - len; i++) {
                // 枚举s2中起点位置，并保持长度
                for (int j = 0; j <= m - len; j++) {
                    // 由于其结果呢，依赖于当前从s1中取出的S1与s2中取出的S2的判断结果
                    // 在S1与S2的比对中，只要有一个符合要求，就可以把dp[i][j][len]设置成true，即符合
                    // 因此S1与s2的比对其实是与上面操作类似，只不过我们因为优先计算了长度为1的值，那么长度为2的值
                    // 直接可以推导出来
                    // 长度可以是1~len-1  （len = S1.length()）
                    for (int subLen = 1; subLen <= len - 1; subLen++) {
                        // 只要有一种情况满足，那么当前dp[i][j][len]就满足，即可退出该循环
                        if(dp[i][j][subLen] && dp[i + subLen][j + subLen][len - subLen]){// 取S1前面一部分与S2前面一部分对比，后面与后面对比
                            dp[i][j][len] = true;
                            break;
                        }
                        // 还有一种情况，S1的前面一部分与S2的后面一部分对比，后面一部分与前面一部分对比
                        if(dp[i][j + len - subLen][subLen] && dp[i + subLen][j][len - subLen]){
                            dp[i][j][len] = true;
                            break;
                        }
                    }
                }
            }
        }
        return dp[0][0][n];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
