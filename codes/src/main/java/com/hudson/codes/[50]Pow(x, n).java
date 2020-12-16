package com.hudson.codes;//实现 pow(x, n) ，即计算 x 的 n 次幂函数。
//
// 示例 1: 
//
// 输入: 2.00000, 10
//输出: 1024.00000
// 
//
// 示例 2: 
//
// 输入: 2.10000, 3
//输出: 9.26100
// 
//
// 示例 3: 
//
// 输入: 2.00000, -2
//输出: 0.25000
//解释: 2-2 = 1/22 = 1/4 = 0.25 
//
// 说明: 
//
// 
// -100.0 < x < 100.0 
// n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1] 。 
// 
// Related Topics 数学 二分查找 
// 👍 558 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution50 {

    // 方法1：最直观的思路，遍历n次
    // 【错误1】没有考虑到n是负数情况
    // 对n小于0的情况取倒数即可。但要注意溢出问题
    public double myPow(double x, int n) {
        // 我们可以对想x=1和-1做特殊处理，如果n是偶数，那么结果必然是1，如果n是奇数，则分情况
        if(x == 1){
            return 1;
        }
        if(x == -1){
            if((n & 1) == 1){
                return -1;
            }
            return 1;
        }
        // 对x=0特殊处理
        if(x == 0) return 0;
        // 对n=0特殊处理
        if(n == 0) return 1;
        // 那么我们需要分情况判断n是否大于0和是否小于0
        double mul = 1;
        if(n > 0){
            for (int i = 0; i < n; i++) {
                mul *= x;
            }
        }else{
            // 如果n小于0，那么我们需要先转为正数，最后把结果 1/mul，由于这里负数转正数可能溢出，因此需要特殊处理
            if(n == Integer.MIN_VALUE){
                return 0;
            }
            n = -n;
            for (int i = 0; i < n; i++) {
                mul *= x;
            }
            mul = 1 / mul;
        }
        return mul;
    }


    // 通过，时间：98.10%，空间：67.80%
    // 思路很好，但容易写错，特别是递归的逻辑，主要可能是没有分析好
    // 实际上，为了优化，我们可以将乘以x的结果作为
    // 新值，用它乘以自己，那么就能够减少之前x乘以的次数，优化了一半
    // 这种优化思路有点类似第29题
    public static double myPow2(double x, int n) {
        if(x == 1){
            return 1;
        }
        if(x == -1){
            if((n & 1) == 1){
                return -1;
            }
            return 1;
        }
        // 对x=0特殊处理
        if(x == 0) return 0;
        // 对n=0特殊处理
        if(n == 0) return 1;
        // 那么我们需要分情况判断n是否大于0和是否小于0
        double mul = 1;
        if(n > 0){
            mul = pow(x, n - 1, 1, x);
        }else{
            // 如果n小于0，那么我们需要先转为正数，最后把结果 1/mul，由于这里负数转正数可能溢出，因此需要特殊处理
            if(n == Integer.MIN_VALUE){
                return 0;
            }
            n = -n;
            mul = pow(x, n - 1, 1, x);
            mul = 1 / mul;
        }
        return mul;
    }

    /**
     *
     * @param mul 当前乘积结果，它表示已经使用hasUsed个target乘积的结果
     * @param left 还需要乘以多少个
     * @param hasUsed 已经使用了多少个
     * @return
     */
    private static double pow(double mul, int left, int hasUsed, double target){
        if(left <= 0) return mul;
        if(hasUsed <= left){
            // 如果结果还允许乘以一个当前的值，那么继续使用，即mul，相当于hasUsed个target；剩余的继续按照这种思路走
            mul = mul * mul;
            return pow(mul, left - hasUsed, hasUsed * 2, target);
        }else{
            // 当前的剩余不允许继续乘以当前的值，因此从一个乘以开始，即使用1个target，并也是以计算后的结果作为参考值，以减少计算
            // 例如2^7的，我们可以划分为 （2 * 2 * 2 * 2）*（2 * 2 * 2），每个括号都是hasUsed从1开始计算的结果【因为从0开始的话，hasUsed*2永远是0】
            return mul * pow(target, left - 1, 1, target);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
