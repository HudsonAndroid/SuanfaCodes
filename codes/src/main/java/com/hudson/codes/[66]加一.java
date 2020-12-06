package com.hudson.codes;//给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
//
// 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。 
//
// 你可以假设除了整数 0 之外，这个整数不会以零开头。 
//
// 
//
// 示例 1： 
//
// 
//输入：digits = [1,2,3]
//输出：[1,2,4]
//解释：输入数组表示数字 123。
// 
//
// 示例 2： 
//
// 
//输入：digits = [4,3,2,1]
//输出：[4,3,2,2]
//解释：输入数组表示数字 4321。
// 
//
// 示例 3： 
//
// 
//输入：digits = [0]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= digits.length <= 100 
// 0 <= digits[i] <= 9 
// 
// Related Topics 数组 
// 👍 592 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution66 {
    public int[] plusOne(int[] digits) {
        if(digits == null || digits.length == 0) return null;
        int over = 1;// 因为需要+1
        int sum;
        for (int i = digits.length - 1; i >= 0; i--) {
            sum = over + digits[i];
            over = sum >= 10 ? 1 : 0;
            digits[i] = sum % 10;
        }
        // 判断最后是否还有溢出，如果有，那么需要增加结果
        if(over == 1){
            int[] result = new int[digits.length + 1];
            result[0] = 1;
            for (int i = 0; i < digits.length; i++) {
                result[i+1] = digits[i];
            }
            return result;
        }
        return digits;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
