package com.hudson.codes;//给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
//
// 示例 1： 
//
// 输入: "babad"
//输出: "bab"
//注意: "aba" 也是一个有效答案。
// 
//
// 示例 2： 
//
// 输入: "cbbd"
//输出: "bb"
// 
// Related Topics 字符串 动态规划 
// 👍 2938 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution5 {
    // 中心扩散 + 滑动窗口
    public static String longestPalindrome(String s) {
        if(s != null && s.length() > 0){
            if(s.length() == 1){// 特殊情况，不符合后续算法
                return s;
            }
            String maxStr = s.substring(0,1);// 错误1： 没有考虑到类似ac这样的，回文数是a或c的情况
            int left = 0;
            int right = 1;
            // 窗口大小维持在[1,2]上
            // left最多到s字符串的倒数第二个
            while(left < s.length() - 1 && right < s.length()){
                String tmp = getPalindrome(s,left, right);
                if(tmp.length() > maxStr.length()){
                    maxStr = tmp;
                }
                int windowLen = right - left + 1;
                if(windowLen == 2){
                    // 窗口是2个数，这时减小窗口，left后移
                    left ++;
                }else{
                    right ++;
                }
            }
            return maxStr;
        }
        return null;
    }

    public static String getPalindrome(String s, int left, int right){
        if(left <= right){
            while( left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
                left --;
                right ++;
            }
            return s.substring(left + 1, right); // 头部包含，尾部不包含
        }
        return null;
    }

    // 法2： 动态规划
    // 一般情况下，我们都会维护一个数组保存 f(i)所需要的f(i - 1)的结果，以推导出f(i)的值
    // 而这里率属于特殊情况，有两个参数会影响 left和right，因此需要一个二维数组
    // f(i, j) = f(i - 1, j + 1) && s[i] == s[j]
    // 此外，left <= right
    // 另外，如果right - left + 1 == 3且 s[left] = s[right]，则不需要继续判断，直接符合
    // right - left + 1 == 2且 s[left] = s[right]，则不需要继续判断，直接符合
    // 而right - left + 1 == 1情况更不用判断
    // 因此right - left < 3且头尾相等的情况下，不需要判断
    public static String longestPalindrome2(String s) {
        if(s != null && s.length() > 0){
            if(s.length() == 1){
                return s;
            }
            boolean[][] flags = new boolean[s.length()][s.length()];
            int maxLenBegin = 0;
            int maxLen = 1;
            // 错误2： 如果是aaaaa这样的，那么结果是[0,4]，需要依赖 [1,3]的结果，
            // 因此如果先确定left，遍历right的话，会导致left = 0依赖left = 1的结果
            // 导致失败；因此需要先确定right，遍历left，因为right是从0自增到4的，所以[0,4]依赖[0,3]没有问题
            for (int j = 1; j < s.length(); j++) {
                for (int i = 0; i < j; i++) {
//                 for (int i = 0; i < s.length(); i++) {
//                     for (int j = i; j < s.length(); j++) {
                    if(s.charAt(j) == s.charAt(i)){
                        // 错误3： 是长度小于4，下标差小于3的情况下
                        if(j - i < 3){
//                            maxLen = Math.max(maxLen, j - i + 1);
                            if(j - i + 1 > maxLen){// 只有比之前的大才替换
                                maxLenBegin = i;
                                maxLen = j - i + 1;
                            }
                            flags[i][j] = true;
                        }else{
                            if(flags[i + 1][j - 1]){
//                                maxLen = Math.max(maxLen, j - i + 1);
                                if(j - i + 1 > maxLen){// 只有比之前的大才替换
                                    maxLenBegin = i;
                                    maxLen = j - i + 1;
                                }
                                flags[i][j] = true;
                            }else{
                                flags[i][j] = false;
                            }
                        }
                    }else{
                        flags[i][j] = false;
                    }
                }
            }
            // 错误1： substring是不包含尾部的，因此不需要-1
            return s.substring(maxLenBegin, maxLenBegin + maxLen);
        }
        return null;
    }

    // 额，贴一句，动态规划按道理是减少了计算次数，可是实际提交发现耗时比中心扩散多得多，而且又要耗空间复杂度，gg







    // 【复习】
    // 也是动态规划思路，我们发现i,j的值取决于i+1和j-1的值
    // 那么，我们用i到j的间隔值作为计算依据，填表所有间隔，从之前的间隔值可以推导出
    // 后面的间隔值。间隔值从1开始，因为间隔值0（即i = j）没有参考意义
    public static String longestPalindrome3(String s) {
        if(s.length() == 0) return null;
        int maxLeft = 0, maxRight = 0;// 两边都包含
        int maxLen = 1;
        // f(i,j) = f(i + 1, j - 1) && a[i] = a[j]
        // 例如[1,4]由[2,3]推导而来，因此我们先填表间隔是1的值
        int length = s.length();
        boolean[][] dp = new boolean[length][length];
        int offset = 1;// 间隔从1开始，i = j的忽略，从j - i = 1开始
        while(offset <= length - 1){ // 最大间隔是 0 ~ length - 1
            int i = 0;
            int j = i + offset;
            while(j <= length - 1){
                if(j - i + 1 <= 3){
                    // 直接判定
                    dp[i][j] = s.charAt(i) == s.charAt(j);
                }else{
                    dp[i][j] = dp[i + 1][j - 1] && (s.charAt(i) == s.charAt(j));
                }
                if(dp[i][j]){
                    int len = j - i + 1;
                    if(len > maxLen){
                        maxLen = len;
                        maxLeft = i;
                        maxRight = j;
                    }
                }
                i++;
                j++;
            }
            offset++;// 间隔增大一个单位
        }
        // 【注意，我们上面的maxRight是包含的，因此这里要+1】
        return s.substring(maxLeft,maxRight + 1);
    }
    // 实际上就是参考的是间隔值比它们间隔值小2的dp值
    // 所以，我们实际填表，如果最终要求的是0-length-1是否是回文串（即判定是否是回文串），那么要填到的间隔值大小是length - 1 - 2即可
    // 然后0-length - 1只需要判定下首尾即可

}
//leetcode submit region end(Prohibit modification and deletion)
