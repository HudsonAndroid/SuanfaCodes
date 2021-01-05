package com.hudson.codes;//将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
//
// 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下： 
//
// L   C   I   R
//E T O E S I I G
//E   D   H   N
// 
//
// 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。 
//
// 请你实现这个将字符串进行指定行数变换的函数： 
//
// string convert(string s, int numRows); 
//
// 示例 1: 
//
// 输入: s = "LEETCODEISHIRING", numRows = 3
//输出: "LCIRETOESIIGEDHN"
// 
//
// 示例 2: 
//
// 输入: s = "LEETCODEISHIRING", numRows = 4
//输出: "LDREOEIIECIHNTSG"
//解释:
//
//L     D     R
//E   O E   I I
//E C   I H   N
//T     S     G 
// Related Topics 字符串 
// 👍 917 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution6 {
    // 思路：按照正常顺序排下去，那么只是切换行的问题，没有别的问题
    // 因此用3个字符串记录各个行的值
    // 【根据题意，它只是让我们输出结果，并不一定是要用一个二位数组记录结果，因此这个题目本身只是一个翻转的功能而已，不要想太复杂】
    public String convert(String s, int numRows) {
        if(numRows == 1){
            return s;
        }
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }
        int lineIncrementFlag = -1;
        int row = 0;
        for (int i = 0; i < s.length(); i++) {
            rows.get(row).append(s.charAt(i));
            if(row == 0 || row == numRows - 1){
                lineIncrementFlag = -lineIncrementFlag;
            }
            row += lineIncrementFlag;
        }
        StringBuilder result = new StringBuilder();
        for(StringBuilder sb: rows){
            result.append(sb);
        }
        return result.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
