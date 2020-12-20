package com.hudson.codes;//格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个位数的差异。
//
// 给定一个代表编码总位数的非负整数 n，打印其格雷编码序列。即使有多个不同答案，你也只需要返回其中一种。 
//
// 格雷编码序列必须以 0 开头。 
//
// 
//
// 示例 1: 
//
// 输入: 2
//输出: [0,1,3,2]
//解释:
//00 - 0
//01 - 1
//11 - 3
//10 - 2
//
//对于给定的 n，其格雷编码序列并不唯一。
//例如，[0,2,3,1] 也是一个有效的格雷编码序列。
//
//00 - 0
//10 - 2
//11 - 3
//01 - 1 
//
// 示例 2: 
//
// 输入: 0
//输出: [0]
//解释: 我们定义格雷编码序列必须以 0 开头。
//     给定编码总位数为 n 的格雷编码序列，其长度为 2n。当 n = 0 时，长度为 20 = 1。
//     因此，当 n = 0 时，其格雷编码序列为 [0]。
// 
// Related Topics 回溯算法 
// 👍 254 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution89 {
    // 分析
    // 假设输入n=3，因为编码必须以0开始，因此结果开始必须是000
    // 000
    // 010 （也可以选择010或者100，但为了规律，我们这样，因为题意只要求一个，即把某个位取反）
    // 011
    // 001
    // 101
    // 111
    // 110
    // 100
    // 实际上相当于去除最高位的话，n-1结果的镜像, 而且它们的差值是2^(n-1)，这里是4
    // 那n=0 => n=1符合吗（去除最高位，对称，这里去除最高位没了）
    // 0
    // 1
    // 那n=1 =>  n=2呢（去除最高位对称，相互去除高位对称的两个数相差2^1）
    // 00
    // 01
    // 11
    // 10
    public static List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        // 第一位都是0
        result.add(0);
        // 从0往后推导
        for (int i = 1; i <= n; i++) {
            int offset = 1 << (i - 1);
            // 由于之前的结果已经把对称的上半部处理了，因此我们只需要处理高位是1的下半部分
            // 对称是新的最后一位对应原始的第一位，因此新的第一位对应原始的最后一位
            // 之后增加offset
            int j = result.size() - 1;
            for (int m = j; m >= 0; m--) {
                result.add(result.get(m) + offset);
            }
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
