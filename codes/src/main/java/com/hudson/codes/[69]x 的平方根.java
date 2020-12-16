package com.hudson.codes;//实现 int sqrt(int x) 函数。
//
// 计算并返回 x 的平方根，其中 x 是非负整数。 
//
// 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。 
//
// 示例 1: 
//
// 输入: 4
//输出: 2
// 
//
// 示例 2: 
//
// 输入: 8
//输出: 2
//说明: 8 的平方根是 2.82842..., 
//     由于返回类型是整数，小数部分将被舍去。
// 
// Related Topics 数学 二分查找 
// 👍 556 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution69 {
    // 时间5.62%,空间64.41%
    // 分析，很简单，就是一个遍历的过程
    // 如果一个数要求平方根，那么目标必然在1~x/2之间（边界包括），排除0、1的情况
    public static int mySqrt(int x) {
        if(x < 0) return -1;
        if(x == 0) return 0;
        if(x == 1) return 1;
        // 找到第一个平方大于x的数
        // 【错误1】未考虑到我们判断的是是否比x大，那如果刚好x是最大数，那么在某个位置上平方值是溢出的，必然小于最大数
        int half = x >> 1;
        int oldMul = 1;
        for (int i = 1; i <= half; i++) {
            int mul = i * i;
            if(oldMul > mul){
                // 新的平方小于旧的平方，说明出现了溢出，那么结果直接返回上一个i
                return i - 1;
            }
            if(mul == x) return i;
            if(mul > x) return i - 1;
            oldMul = mul;
        }
        return half;
    }


    // 时间超过 9.36%，空间70.88%，效率还是低啊
    // 上述方法效率很低，为什么
    // 例如，我们是7*7=49,那么我们要找到的位置是 49/2 = 24
    // 但实际上它的距离比我们7的位置远得多。那么有什么办法缩小范围呢？
    // 继续按照上面的思路来
    // 上面一轮下来后，我们范围缩小至 1~24之间
    // 那么我们可以比较下24的平方与目标的差距
    // 24 * 24 / (7 * 7) = (24 / 7) * (24 / 7) = (24 / 7)的平方
    // 咦？那么问题缩小至求 （24/7）平方的平方根，实际上24 / 7 = 3
    // 那么我们把 24 / 3 = 8作为新的右边界，注意得到的结果必然大于等于我们要求的结果
    // 上一轮缩小后，我们继续缩小
    // 再来一次上面的操作
    // (8 * 8) / (7 * 7) = 1
    // 由于结果是1，因此没有必要缩小右边界了，那么以这个作为结束条件判断1~8的数字
    // 其实为了更快速，这时候，我们可以从右边往左边遍历，而不是从1开始遍历
    // 最终能快速得到是7
    // 上面分析中，我们要求出 (24 / 7)平方的平方根，很明显是一个递归操作。
    public static int mySqrt2(int x) {
        if(x < 0) return -1;
        if(x == 0) return 0;
        if(x == 1) return 1;
        int rightSide = x >> 1;
        rightSide = Math.min(rightSide, 46340);// 避免溢出，46340是Integer.MAX_VALUE的平方根
        while(true){
            int times = mySqrt2(rightSide * rightSide / x);// 求出倍数差，以获取更小的右边界
            if(times <= 1){
                // 不需要缩小，因此退出，有可能times是0，因此是<=1
                break;
            }else{
                rightSide = rightSide / times;
            }
        }
        // 重新在1~rightSide中寻找，更快的是从后往前找
        for (int i = rightSide; i >= 1; i--) {
            int mul = i * i;
            if(mul <= x) return i;
        }
        return 1;
    }

    // 法3： 时间56.52%，空间71.31%
    // 使用long避免溢出
    public static int mySqrt3(int x) {
        return mySqrt3Inner(x);
    }

    public static int mySqrt3Inner(long x) {
        if(x < 0) return -1;
        if(x == 0) return 0;
        if(x == 1) return 1;
        long rightSide = x >> 1;
        while(true){
            int times = mySqrt3Inner(rightSide * rightSide / x);// 求出倍数差，以获取更小的右边界
            if(times <= 1){
                // 不需要缩小，因此退出，有可能times是0，因此是<=1
                break;
            }else{
                rightSide = rightSide / times;
            }
        }
        // 重新在1~rightSide中寻找，更快的是从后往前找
        for (long i = rightSide; i >= 1; i--) {
            long mul = i * i;
            if(mul <= x) return (int) i;
        }
        return 1;
    }

    // 时间：100%，空间：77.49%
    // 法4：二分查找
    // 哎哟，我把这个忘了
    public static int mySqrt4(int x){
        if(x < 0) return -1;
        if(x == 0) return 0;
        if(x == 1) return 1;
        long half = x >> 1; // long避免溢出问题
        if(half * half == x) return (int) half;
        // 开始在1-half之间二分查找
        long mul;
        long left = 1;
        long right = half;
        while(left < right){
            // 【错误1】一般出现死循环，例如left = 4, right = 5, half = 4，这种情况下，要尝试修改 +1或者-1
            half = (left + right + 1) >> 1;
            mul = half * half;
            if(mul == x) return (int) half;// 直接强转，肯定不会溢出
            if(mul > x){
                // 【错误2】，这个时候直接排除了half，因此是half - 1
                right = half - 1;
            }else{
                left = half;
            }
        }
        // 如果到这里还没有找到（left = right），那么应该位置就在附近
        if(left * right > x) return (int) (left - 1);
        return (int) left;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
