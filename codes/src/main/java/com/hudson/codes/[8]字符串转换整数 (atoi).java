package com.hudson.codes;//请你来实现一个 atoi 函数，使其能将字符串转换成整数。
//
// 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。接下来的转化规则如下： 
//
// 
// 如果第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字字符组合起来，形成一个有符号整数。 
// 假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成一个整数。 
// 该字符串在有效的整数部分之后也可能会存在多余的字符，那么这些字符可以被忽略，它们对函数不应该造成影响。 
// 
//
// 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换，即无法进行有效转换。 
//
// 在任何情况下，若函数不能进行有效的转换时，请返回 0 。 
//
// 提示： 
//
// 
// 本题中的空白字符只包括空格字符 ' ' 。 
// 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231, 231 − 1]。如果数值超过这个范围，请返回 INT_MAX (231
// − 1) 或 INT_MIN (−231) 。 
// 
//
// 
//
// 示例 1: 
//
// 输入: "42"
//输出: 42
// 
//
// 示例 2: 
//
// 输入: "   -42"
//输出: -42
//解释: 第一个非空白字符为 '-', 它是一个负号。
//     我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
// 
//
// 示例 3: 
//
// 输入: "4193 with words"
//输出: 4193
//解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
// 
//
// 示例 4: 
//
// 输入: "words and 987"
//输出: 0
//解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
//     因此无法执行有效的转换。 
//
// 示例 5: 
//
// 输入: "-91283472332"
//输出: -2147483648
//解释: 数字 "-91283472332" 超过 32 位有符号整数范围。 
//     因此返回 INT_MIN (−231) 。
// 
// Related Topics 数学 字符串 
// 👍 906 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution8 {
    // 本题我们需要考虑以下几点：
    // 前面有空格，则去掉；
    // 判断是否有 + -， 有正负号的话，正为1（默认），负为-1
    // 判断是否是数字
    // 如果第一个字符不是数字，则跳出循环，直接返回0，表示失败
    // 如果转换时整数溢出了，需要返回MAX或者MIN
    public static int myAtoi(String s) {
        if(s != null && s.length() > 0){
            int length = s.length();
            // 第一步，判断是否有空格
            int i = 0;
            while(i < length && s.charAt(i) == ' ') i++;
            // 第二步，判断是否是正负号
            int mul = 1;
            if(i < length){
                char c = s.charAt(i);
                if(c == '+'){
                    i ++;
                }else if(c == '-'){
                    i ++;
                    mul = -1;
                }
            }
            // 第三步，判断第一个是否是数字
            if(i < length){
                char c = s.charAt(i);
                int num;
                if(c >= '0' && c <= '9'){
                    num = (c - '0') * mul; //这时就携带上正负号，以避免正数末尾>8溢出
                    i ++;
                }else{
                    return 0;
                }
                while(i < length){
                    c = s.charAt(i);
                    if(c >= '0' && c <= '9'){
                        // 错误:每次都需要*mul，以保证正负情况
                        int tmp = (c - '0') * mul;
                        // 在向左移扩大10倍前，判断是否可能溢出
                        // 错误1：判断是否溢出时，由于存储转换结果的num是一个正整数，因此
                        // 溢出末尾是7，所以不能连带mul判断是否>8； 而是num要携带之前的正负号判断
//                        if(num > Integer.MAX_VALUE / 10 || (num == Integer.MAX_VALUE / 10
//                                && ((mul == 1 && tmp > 7) || (mul == -1 && tmp > 8)))){
//                            // 发生了溢出
//                            return mul == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
//                        }
                        if(num > Integer.MAX_VALUE / 10 || (num == Integer.MAX_VALUE / 10 &&
                                tmp > 7)){
                            return Integer.MAX_VALUE;
                        }
                        if(num < Integer.MIN_VALUE / 10 || (num == Integer.MIN_VALUE / 10 &&
                                tmp < -8)){
                            return Integer.MIN_VALUE;
                        }
                        num = num * 10 + tmp;
                        i ++;
                    }else{
                        break;
                    }
                }
                return num;
            }
        }
        return 0;
    }

    // 针对上面的优化（从提交结果来看，好像优化没有什么体现，空间占用还更多）
    // 在我们判断溢出的时候，主要是由于负数最末尾可以到达8，而正数最大可为7，因此后面计算出来，
    // 还没有*mul确定正负的时候，就可能溢出，但这只有这一种情况才会发生，因此我们可以针对该情况
    // 特殊处理
    // 但实际我们发现，只有末尾刚好 = 8的正数溢出情况下，我们要返回MIN,而这可以和>8的结果合并
    // 即 >= 8，也就是 >7，因此可以直接和正数判断合并
    public static int myAtoi2(String s) {
        if(s != null && s.length() > 0){
            int length = s.length();
            // 第一步，判断是否有空格
            int i = 0;
            while(i < length && s.charAt(i) == ' ') i++;
            // 第二步，判断是否是正负号
            int mul = 1;
            if(i < length){
                char c = s.charAt(i);
                if(c == '+'){
                    i ++;
                }else if(c == '-'){
                    i ++;
                    mul = -1;
                }
            }
            // 第三步，判断第一个是否是数字
            if(i < length){
                char c = s.charAt(i);
                int num;
                if(c >= '0' && c <= '9'){
                    num = c - '0';
                    i ++;
                }else{
                    return 0;
                }
                while(i < length){
                    c = s.charAt(i);
                    if(c >= '0' && c <= '9'){
                        int tmp = c - '0';
                        // 其实这里我们只需要判断是否 >7即可，因为如果是mul = -1，且tmp > 8和tmp =8，我们最终返回的都
                        // 将是Integer.MIN_VALUE
                        if(num > Integer.MAX_VALUE / 10 || (num == Integer.MAX_VALUE / 10
                                && ((/*mul == 1 && */tmp > 7) /*|| (mul == -1 && tmp > 8)*/))){
                            // 发生了溢出
                            return mul == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        }
                        // 在乘以10，加上后一位数前，判断特殊情况
                        num = num * 10 + tmp;
                        i ++;
                    }else{
                        break;
                    }
                }
                return num * mul;
            }
        }
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
