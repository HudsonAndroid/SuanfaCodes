package com.hudson.codes;//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
//
// 有效字符串需满足： 
//
// 
// 左括号必须用相同类型的右括号闭合。 
// 左括号必须以正确的顺序闭合。 
// 
//
// 注意空字符串可被认为是有效字符串。 
//
// 示例 1: 
//
// 输入: "()"
//输出: true
// 
//
// 示例 2: 
//
// 输入: "()[]{}"
//输出: true
// 
//
// 示例 3: 
//
// 输入: "(]"
//输出: false
// 
//
// 示例 4: 
//
// 输入: "([)]"
//输出: false
// 
//
// 示例 5: 
//
// 输入: "{[]}"
//输出: true 
// Related Topics 栈 字符串 
// 👍 2016 👎 0


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution20 {
    public static boolean isValid(String s) {
        if(s != null){
            if(s.length() == 0) return true;
            Map<Character, Character> reverseMap = new HashMap<>();
            reverseMap.put('(', ')');
            reverseMap.put('[', ']');
            reverseMap.put('{','}');

            Stack<Character> charStack = new Stack<>();
            char[] chars = s.toCharArray();
            for (char curChar : chars) {
                if(curChar == '(' || curChar == '[' || curChar == '{'){
                    charStack.push(reverseMap.get(curChar));// 入栈
                }else{
                    // 表示需要出栈，判断是否与当前栈中顶部元素相同，相同则符合要求并继续，否则不符合直接结束
                    // 错误2：如果外界输入"]"，那么将直接进入出栈操作，这时候将会报错：空栈错误，因此需要增加
                    // 是否空栈判断
                    if(!charStack.isEmpty() && curChar == charStack.pop()){
                        continue;
                    }
                    return false;
                }
            }
            // 错误1：如果外界输入 "["，最终也会被判断是true，因为我们没有判断最后是否成对出现并成对消失
            // 因此需要判断栈是否是空
            return /*true*/charStack.isEmpty();
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
