package com.hudson.codes;//判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
//
// 示例 1: 
//
// 输入: 121
//输出: true
// 
//
// 示例 2: 
//
// 输入: -121
//输出: false
//解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
// 
//
// 示例 3: 
//
// 输入: 10
//输出: false
//解释: 从右向左读, 为 01 。因此它不是一个回文数。
// 
//
// 进阶: 
//
// 你能不将整数转为字符串来解决这个问题吗？ 
// Related Topics 数学 
// 👍 1323 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution9 {
    // 法1：效率不是非常高
    // 回文数，首尾相等，那么可以转换成数组的问题
    public boolean isPalindrome(int x) {
        //我们可以更加优化，所有的负数，都不可能是回文数，因此
        if(x < 0){
            return false;
        }
        String s = String.valueOf(x);
        int left = 0;
        int right = s.length() - 1;
        while(left < right){
            if(s.charAt(left) == s.charAt(right)){
                left ++;
                right --;
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

    // 把数字翻转一半，这样既不会出现溢出问题，也可以判断结果
    // 原数12321 翻转0 ->  原数12  翻转123 -> 比较翻转的结果和最初原数/(翻转次数 * 10) 或者 原数 == 翻转 /10，因为差一位
    // 原数123321  翻转0 -> 原数 123  翻转123 ->仍然上面方式比较 或者  原数 == 翻转
    public boolean isPalindrome2(int x) {
        //我们可以更加优化，所有的负数，都不可能是回文数，因此
        if(x < 0){
            return false;
        }
        // 由于有特殊情况，例如10， 翻转一次 1  0; 翻转两次 1  1；但这个实际不符合要求
        if(x % 10 == 0 && x != 0){
            // 最后一位是0的情况下，只有0符合，其他都不符合
            return false;
        }
        int reverse = 0;
        while(reverse < x){
            int over = x % 10;
            reverse = reverse * 10 + over;
            x /= 10;
        }
        return reverse == x || reverse / 10 == x;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
