package com.hudson.codes;//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
//
// 
//
// 示例： 
//
// 输入：n = 3
//输出：[
//       "((()))",
//       "(()())",
//       "(())()",
//       "()(())",
//       "()()()"
//     ]
// 
// Related Topics 字符串 回溯算法 
// 👍 1447 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution22 {

    // 提交通过，时间超过 95.97%   空间超过 68.3%

    // 与20题类似，使用栈来解决。注意：需要入栈的是)，但这时我们记录当前的是(， 成对出现
    // 分析题意可以发现：
    // 必须入栈n次且出栈n次；当栈为空的时候，必须入栈或者结束；
    // 当入栈次数已经达到n次的时候，必须出栈
    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        if(n <= 0) return result;
//        Stack<Character> charStack = new Stack<>();
        generateInner(new StringBuilder(), result, 0, new StringBuilder(), n);
        return result;
    }

    private static void generateInner(/*Stack<Character> stack, */StringBuilder stack, List<String> result, int pushTimes, StringBuilder sb, int pushLimit){
        if(/*stack.isEmpty()*/stack.length() == 0 && pushTimes == pushLimit){// 终止条件是栈是空的（即所有元素都已出栈），且已经全部入栈完成
            result.add(sb.toString());
            return ;
        }
        if(/*stack.isEmpty()*/stack.length() == 0){// 栈是空的，因此必须入栈
            sb.append("(");
//            stack.push(")");
            stack.append(')');
            generateInner(stack, result, pushTimes + 1, sb, pushLimit);
        }else if(pushTimes == pushLimit){ // 入栈次数达限，必须出栈
            // 我们已经知道结果，全部出栈
            while (/*!stack.isEmpty()*/stack.length() != 0){
                int lastIndex = stack.length() - 1;
                sb.append(stack.charAt(lastIndex));
                stack.deleteCharAt(lastIndex);
            }
            result.add(sb.toString());
        }else{
            // 栈不是空的，同时入栈次数没有达限，因此有两个选择
            // 先第一种选择
            String resBackup = sb.toString();// 原始结果备份
            sb.append("(");
//            stack.push(")");
            String backup = stack.toString();
            stack.append(")");
            generateInner(stack, result, pushTimes + 1, sb, pushLimit);
            // 恢复【回溯法】   由于我们需要恢复stack的状态，因此使用StringBuilder模拟Stack，因为经过上一行代码使用系统的Stack的话，早就空了
            // 错误1： 不能使用pushTimes作为删除的起始位置，因为有可能已经出栈了，即里面有)，这样下标会增加1
//            sb.delete(pushTimes,sb.length());// 注意：由于上一行代码执行后，已经生成完毕了，因此需要把后面的全部删除【或者跟stack方式一样，使用备份方式】
//            stack.pop();
            sb.delete(0, sb.length());// 清空
            sb.append(resBackup);
            if(stack.length() == 0){
                stack.append(backup);
            }else{
                throw new IllegalStateException("错误状态");
            }

            // 继续另一种情况
//            sb.append(stack.pop());
            int lastIndex = stack.length() - 1;
            sb.append(stack.charAt(lastIndex));
            stack.deleteCharAt(lastIndex);
            generateInner(stack, result, pushTimes, sb, pushLimit);
        }
    }


    // 由于这个参考的是python的代码，而java中需要大量创建ArrayList，效率并不高；时间仅超过10%的人
    // 法2： f(n) = '(' + f(p)的所有情况 + ')' + f(q)对应的所有情况  其中 n - 1 = p + q；   '()'减去一对
    // 因此需要先计算出前面的结果，以推导出后面的结果
    public static List<String> generateParenthesis2(int n) {
        if(n <= 0) return new ArrayList<>();
        List<List<String>> result = new ArrayList<>();// 创建存储结果的list
        // 最初的几个值我们能确定下来
        List<String> zero = new ArrayList<>();
        zero.add(""); // f(0)只有""的情况
        result.add(zero);

        List<String> one = new ArrayList<>();
        one.add("()");// f(1)只有()的情况
        result.add(one);

        // 根据前面的值，推导出后面的结果
        for (int i = 2; i <= n; i++) {
            // 根据 f(n) = '(' + f(p) + ')' + f(q)， 我们推导后面的结果
            List<String> current = new ArrayList<>();
            // p从0到i-1
            for (int j = 0; j <= i - 1; j++) {
                // 因为f(p)需要进一步遍历，因为它是一个集合
                for(String p: result.get(j)){
                    // 固定p的情况下，同时f(q)也有很多种情况
                    for(String q: result.get(i - 1 - j)){
                        current.add("("+p+")" + q);
                    }
                }
            }
            // 计算出了所有i的情况
            result.add(current);
        }
        return result.get(n);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
