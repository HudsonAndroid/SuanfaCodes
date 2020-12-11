package com.hudson.codes;//给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
//
// 示例 1: 
//
// 输入: num1 = "2", num2 = "3"
//输出: "6" 
//
// 示例 2: 
//
// 输入: num1 = "123", num2 = "456"
//输出: "56088" 
//
// 说明： 
//
// 
// num1 和 num2 的长度小于110。 
// num1 和 num2 只包含数字 0-9。 
// num1 和 num2 均不以零开头，除非是数字 0 本身。 
// 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。 
// 
// Related Topics 数学 字符串 
// 👍 529 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution43 {
    // 分析
    //   7456  （长一点的字符串）
    //    123  （短一点的字符串）
    // --------
    //(2)''(1 + 1)'(2 + 1)'(5 + 1)'8 =        22368
    //(1)'(4)(8 + 1)'(0 + 1)'2 = 14912 * 10= 149120
    // 7456 = 7456 * 100 =                   745600
    //                                     = 917088
    public static String multiply(String num1, String num2) {
        if(num1 == null || num1.length() == 0 || num2 == null || num2.length() == 0) return "";
        String first,second;
        // 确保第一个乘数是更长的字符串
        if(num1.length() > num2.length()){
            first = num1;
            second = num2;
        }else{
            first = num2;
            second = num1;
        }
        List<String> eachSum = new ArrayList<>();
        // 以second作为遍历依据
        for (int i = second.length() - 1; i >= 0; i--) {
            StringBuilder sb = new StringBuilder();
            int mul = second.charAt(i) - '0';
            // 依次乘以mul求单个乘积
            int over = 0;
            for (int j = first.length() - 1; j >= 0; j--) {
                int num = first.charAt(j) - '0';
                int result = num * mul + over;
                sb.append(result % 10);
                over = result / 10;
            }
            // 【错误1】把溢出的最高位忘了
            if(over > 0){
                sb.append(over);
            }
            // 由于结果是反着的，因此翻转
            sb.reverse();
            // 查看是否需要额外附加0
            for (int z = 0; z <(second.length() - 1) - i; z++) {
                sb.append('0');
            }
            eachSum.add(sb.toString());
        }
        // 当把所有计算出来后，求和
        // 最后一个数必然比前面的数长度长，因此以此作为参考
        String last = eachSum.get(eachSum.size() - 1);
        StringBuilder result = new StringBuilder();
        // 求和次数
        int over = 0;
        // 【错误2】，是从后往前加，但是不是直接每个字符串都是s.charAt(i)，也不是倒过来数，而是每个字符串的length - 1 - i（每个字符串的长度减去当前循环的次数）
        for (int i = 0; i < last.length(); i++) {
            int sum = over;
            for (String s : eachSum) {
                int index = s.length() - 1 - i;
                if(index < 0) continue;
                sum += s.charAt(index) - '0';
            }
            result.append(sum % 10);
            over = sum / 10;
        }
        if(over > 0){
            result.append(over);
        }
        String mulResult = result.reverse().toString();
        // 【错误3】如果结果是 '0000'，需要转为只有一个0，而这种情况如果外界输入合法的话，只有最高位为0的情况下会出现
        if(mulResult.length() > 1 && mulResult.charAt(0) == '0'){
            return "0";
        }
        return mulResult;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
