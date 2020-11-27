package com.hudson.codes;//给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
//
// 示例 1: 
//
// 输入: 123
//输出: 321
// 
//
// 示例 2: 
//
// 输入: -123
//输出: -321
// 
//
// 示例 3: 
//
// 输入: 120
//输出: 21
// 
//
// 注意: 
//
// 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231, 231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。 
// Related Topics 数学 
// 👍 2370 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution7 {
    public static int reverse(int x) {
        int res = 0;
        while(x != 0){
            int pop = x % 10;
            //处理溢出问题，分两个方向，正数溢出和负数溢出
            if(res > Integer.MAX_VALUE / 10 ||
                    (res == Integer.MAX_VALUE / 10 && pop > 7)){
                //如果当前没有需要 *10的数已经大于了最大数/10，那么溢出
                //如果当前没有 *10的数等于最大数/10，但是同时接下来的一位数已经 >7，那么溢出
                return 0;
            }
            if(res < Integer.MIN_VALUE / 10 ||
                    (res == Integer.MIN_VALUE / 10 && pop < -8)){
                return 0;
            }
            res *= 10;
            res += pop;
            x /= 10;
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
