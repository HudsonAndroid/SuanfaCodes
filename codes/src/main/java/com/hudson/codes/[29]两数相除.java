package com.hudson.codes;//给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
//
// 返回被除数 dividend 除以除数 divisor 得到的商。 
//
// 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2 
//
// 
//
// 示例 1: 
//
// 输入: dividend = 10, divisor = 3
//输出: 3
//解释: 10/3 = truncate(3.33333..) = truncate(3) = 3 
//
// 示例 2: 
//
// 输入: dividend = 7, divisor = -3
//输出: -2
//解释: 7/-3 = truncate(-2.33333..) = -2 
//
// 
//
// 提示： 
//
// 
// 被除数和除数均为 32 位有符号整数。 
// 除数不为 0。 
// 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231, 231 − 1]。本题中，如果除法结果溢出，则返回 231 − 1。 
// 
// Related Topics 数学 二分查找 
// 👍 463 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution29 {

    // 这道题确实有难度（当然指的是使用第二种方法，本身第一种方法并不能通过），主要就是考虑各种边界问题

    // 法1：力扣的所有测试用例都通过，但是显示超出时间限制，所以不能使用这种方式。哈哈哈哈，因此我们需要提高效率

    // 判断正负情况，然后使用正数计算，不断地用被除数减去除数，直到被除数小于除数
    // 最后把减去的次数添加正负号返回

    // 会有溢出的情况出现，例如Integer.MIN_VALUE / -1的时候
    // 而且，如果我们把负数当正数来算的话，Integer.MIN_VALUE转
    // 正数会溢出。
    // 因此我们不应该把数当正数来处理，而是应该当负数来处理
    // 这样就避免了溢出情况。
    public static int divide(int dividend, int divisor) {
        // 特殊情况，特殊处理，因此Integet.MIN_VALUE / -1得到的数超过了Integer.MAX_VALUE【溢出】
        if(dividend == Integer.MIN_VALUE && divisor == -1){
            return Integer.MAX_VALUE;
        }
        // 用positive确定结果的正负号
        boolean positive = true;
        if(dividend < 0){
//            dividend = -dividend;
            positive = !positive;
        }else{
            dividend = -dividend; // 全部当负数处理
        }
        if(divisor < 0){
//            divisor = -divisor;
            positive = !positive;
        }else{
            divisor = -divisor;
        }
        // 不断用正数被除数减去正数除数
        // 首先，如果除数小于被除数，那么直接返回0
        if(divisor < dividend) return 0;
        int mul = 0;
        while(dividend <= divisor){
            dividend -= divisor;
            mul ++;
        }
        return positive ? mul : -mul;
    }

    // 通过，时间复杂度超过100%的用户，空间超过87.03%的用户
    // 法2：合理办法
    // 根据前面的思路，超出了时间限制，原因在于，如果除数太小，被除数太大，需要减的次数太多
    // 而我们上面方法中依然减去除数，这导致效率极低。
    // 为此我们需要针对情况，增加除数（翻倍），以接近被除数；如果翻倍后的除数还是小于被除数，
    // 那么我们继续翻倍，直到找到第一个翻倍的除数大于被除数的数，那么上一个除数统计出有多少个
    // 原始除数；然后继续计算出去上一个除数剩下的值中有多少个原始除数。
    // 值得注意的是，翻倍过程中有可能会出现溢出问题。
    public static int divide2(int dividend, int divisor) {
        // 特殊情况，特殊处理，因此Integet.MIN_VALUE / -1得到的数超过了Integer.MAX_VALUE【溢出】
        // 【错误4】因为返回结果result必然是个正数，那么可能结果是Integer.MIN_VALUE对应的正值（不考虑溢出的话）
        // 所以我们要在这里就直接阻断这种情况，被除数是最小值时，既要判断divisor是-1的情况，也要判断
        // 是1的情况，这样能避免实际结果是最小值时，result(正数)无法承载的情况
        if(dividend == Integer.MIN_VALUE){
            if(divisor == -1){
                return Integer.MAX_VALUE;
            }else if(divisor == 1){
                return Integer.MIN_VALUE;
            }
            // others, we ignore
        }
        // 用positive确定结果的正负号
        boolean positive = true;
        if(dividend < 0){
//            dividend = -dividend;
            positive = !positive;
        }else{
            dividend = -dividend; // 全部当负数处理
        }
        if(divisor < 0){
//            divisor = -divisor;
            positive = !positive;
        }else{
            divisor = -divisor;
        }
        int result = divide2Inner(dividend, divisor);
        return positive ? result : -result;
    }

    // 需要确保输入dividend和divisor都是负数，以避免出现溢出问题
    public static int divide2Inner(int dividend, int divisor) {
        // dividend（负数）大于divisor（负数）的话，返回0
        if(dividend > divisor) return 0;
        // 因为下一步操作可能溢出，因此判断【错误3：忘了对divisor增加2的溢出进行判定】
        if(divisor < Integer.MIN_VALUE >> 1){
            // 也就是说，除数比Integer.MIN_VALUE还小；而且经过上一步判断，被除数比它更小，因此必然返回1
            return 1;
        }
        int newDivisor = divisor << 1;
        int result = 1;
        boolean isOverBreak = false;
        while(newDivisor >= dividend){// 【这里为了避免绝对值判定，需要外界确保参数全是负数】
            result = result << 1;// 记录当前的值
            // 翻倍前，判断是否会溢出【错误1：判断是否溢出的条件是当前值已经小于了最小值的一半了】
            if(newDivisor < Integer.MIN_VALUE >> 1/* || newDivisor == Integer.MAX_VALUE >> 1*/){
                // 翻倍的话，会出现溢出，因此终止
                isOverBreak = true;
                break;
            }
            newDivisor = newDivisor << 1;// 翻倍
        }
        // 继续剩下的一部分的翻转判断 【错误2： 我们需要恢复上次翻倍大于被除数的除数值，即缩小两倍】
        // 【错误2】我们有可能在循环中执行到判断溢出的条件后break出来的，这个时候，还没有执行翻倍操作，因此不需要恢复处理
        // 因此需要一个标志决定是否恢复
        return result + divide2Inner(dividend - (isOverBreak ? newDivisor : (newDivisor >> 1)), divisor);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
