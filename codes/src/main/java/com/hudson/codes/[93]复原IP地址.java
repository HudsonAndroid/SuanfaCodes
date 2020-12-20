package com.hudson.codes;//给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
//
// 有效的 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。 
//
// 例如："0.1.2.201" 和 "192.168.1.1" 是 有效的 IP 地址，但是 "0.011.255.245"、"192.168.1.312"
// 和 "192.168@1.1" 是 无效的 IP 地址。 
//
// 
//
// 示例 1： 
//
// 输入：s = "25525511135"
//输出：["255.255.11.135","255.255.111.35"]
// 
//
// 示例 2： 
//
// 输入：s = "0000"
//输出：["0.0.0.0"]
// 
//
// 示例 3： 
//
// 输入：s = "1111"
//输出：["1.1.1.1"]
// 
//
// 示例 4： 
//
// 输入：s = "010010"
//输出：["0.10.0.10","0.100.1.0"]
// 
//
// 示例 5： 
//
// 输入：s = "101023"
//输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 3000 
// s 仅由数字组成 
// 
// Related Topics 字符串 回溯算法 
// 👍 471 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution93 {

    // 所有可能结果，因此回溯法
    public static List<String> restoreIpAddresses(String s) {
        if(s == null || s.length() == 0) return new ArrayList<>();
        // 最长只能是12
        if(s.length() > 12) return new ArrayList<>();
        List<String> result = new ArrayList<>();
        backtrack(result, 0, s, new StringBuilder());
        return result;
    }

    // 时间：100%，空间：70.40%
    // 最好是都判断，然后最后去除掉.
    private static void backtrack(List<String> result, int index, String s, StringBuilder item){
        // 如果剩余的长度不符合情况，直接结束
        int leftLen = 4 - index;
        if(s.length() > leftLen * 3 || s.length() < leftLen) return;
        if(index == 3){
            // 最后一位的情况下，把剩余的添加上
            // 【错误3】如果最后一位是0开始的且有多位，不符合
            if(s.charAt(0) == '0' && s.length() > 1) return ;
            if(s.length() == 3){
                // 判断是否超过255
                int first = s.charAt(0) - '0';
                int second = s.charAt(1) - '0';
                int third = s.charAt(2) - '0';
                if(first * 100 + second*10+ third > 255){
                    return;
                }
            }
            item.append(s);
            result.add(item.toString());
            return ;
        }
        // 有三种取值情况
        // 特殊情况，如果第一个是0，那么它必须作为单独的一个
        // 如果三位的情况超过255，那么就不符合要求
        for (int i = 0; i < 3; i++) {
            if(i + 1 > s.length()){// 【错误1】需要终止
                break;
            }
            String divider = s.substring(0, i+1);
            if(divider.length() == 3){
                // 判断是否超过255
                int first = divider.charAt(0) - '0';
                int second = divider.charAt(1) - '0';
                int third = divider.charAt(2) - '0';
                if(first * 100 + second*10+ third > 255){
                    break;
                }
            }
            String old = item.toString();
            item.append(divider).append(".");
            backtrack(result, index + 1, s.substring(i + 1), item);
            // 【错误2】位置应该是divider的长度+一个点的长度
            // 为了简单起见，下次每次都直接clear即可，因为要根据情况判断第三步是否添加了
            item.delete(0, item.length());
            item.append(old); // 回溯
            // 0判断不正确
            if(i == 0 && divider.equals("0")){
                // 必须作为单独的一个
                break;
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
