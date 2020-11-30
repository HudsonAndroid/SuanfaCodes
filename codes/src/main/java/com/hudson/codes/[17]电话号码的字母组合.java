package com.hudson.codes;//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
//
// 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。 
//
// 
//
// 示例: 
//
// 输入："23"
//输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
// 
//
// 说明: 
//尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。 
// Related Topics 字符串 回溯算法 
// 👍 1021 👎 0


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution17 {
    
    public static List<String> letterCombinations(String digits) {
        if(digits != null){
            if(digits.length() == 0){
                return new ArrayList<>();
            }
            List<String> result = new ArrayList<>();
            // 装载数字对应的字符
            Map<Character, String> letterMaps = new HashMap<>();
            letterMaps.put('2', "abc");
            letterMaps.put('3', "def");
            letterMaps.put('4', "ghi");
            letterMaps.put('5', "jkl");
            letterMaps.put('6', "mno");
            letterMaps.put('7', "pqrs");
            letterMaps.put('8', "tuv");
            letterMaps.put('9', "wxyz");

            iterateCombine(digits, letterMaps, 0, result, null);
            return result;
        }
        return null;
    }

    // 回溯法
    private static void iterateCombine(String digits, Map<Character, String> letterMaps,
                                       int curIndex, List<String> result, StringBuilder sb) {
        if(curIndex >= digits.length()){
            result.add(sb.toString());
            return ;
        }
        String s = letterMaps.get(digits.charAt(curIndex));
        for (int i = 0; i < s.length(); i++) {
            if(curIndex == 0){
                sb = new StringBuilder();
            }
            sb.append(s.charAt(i));
            iterateCombine(digits,letterMaps, curIndex + 1, result, sb);
            // 退回去，回溯， 注意：移除的是当前下标的字符，是curIndex，不是i
            sb.deleteCharAt(curIndex);
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)
