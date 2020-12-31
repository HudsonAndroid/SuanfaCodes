package com.hudson.codes;//给定一个非空字符串 s 和一个包含非空单词的列表 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
//
// 说明： 
//
// 
// 拆分时可以重复使用字典中的单词。 
// 你可以假设字典中没有重复的单词。 
// 
//
// 示例 1： 
//
// 输入: s = "leetcode", wordDict = ["leet", "code"]
//输出: true
//解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
// 
//
// 示例 2： 
//
// 输入: s = "applepenapple", wordDict = ["apple", "pen"]
//输出: true
//解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
//     注意你可以重复使用字典中的单词。
// 
//
// 示例 3： 
//
// 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
//输出: false
// 
// Related Topics 动态规划 
// 👍 792 👎 0


import java.util.HashSet;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution139 {
//    public static void main(String[] args){
//        ArrayList<String> wordDict = new ArrayList<>();
//        wordDict.add("a");
//        wordDict.add("aa");
//        wordDict.add("aaa");
//        wordDict.add("aaaa");
//        wordDict.add("aaaaa");
//        wordDict.add("aaaaaa");
//        wordDict.add("aaaaaaa");
//        wordDict.add("aaaaaaaa");
//        wordDict.add("aaaaaaaaa");
//        wordDict.add("aaaaaaaaaa");
//        boolean leetcode = wordBreak("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab", wordDict);
//        System.out.println(" "+leetcode);
//    }


    // 超出时间限制，用例通过率36/43，最后执行了上面的用例
    // 直观办法，使用HashSet
    // 定义两个指针
    // 一个作为起点，另一个作为终点
    // 但实际上，像leetlecode, [le, leet, code]这样的
    // 第一个发现是le，我们这时不应该认为它符合要求，而应该是le与后面的et组合成leet
    // 所以这个问题使用HashSet的方式解法十分复杂
    // 但也是个办法，我们可以把某个前缀去掉后，把剩下的继续判断
    // 即回溯法
    public static boolean wordBreak(String s, List<String> wordDict) {
        if(s == null || wordDict == null || wordDict.size() == 0) return false;
        HashSet<String> map = new HashSet<>(wordDict);
        return backtrack(s, 0, map);
    }

    private static boolean backtrack(String s, int start, HashSet<String> map){
        if(start == s.length()) return true;
        int right = start + 1;
        while(right <= s.length()){
            String cur = s.substring(start, right);
            if(map.contains(cur)){
                // 当前符合要求，继续判断剩余的
                if(backtrack(s, right, map)){
                    return true;
                }
            }
            // 不符合要求，扩大窗口
            right++;
        }
        return false;
    }


    // 36.04%  29.93%
    // 动态规划
    // 该问题可以划分为一个子问题
    // f(i)表示到下标为i的位置时，前面的是否可以被wordDict中的表示
    // 那么f(j)则等于遍历前面所有的f(0)~f(j-1),如果其中有一个f(x)是true的,
    // 继续判断x到j直接的字符串是否在wordDict中，如果在，那么f(j)也是true的
    // 如果所有的情况都不符合，那么f(j)就是false的
    public static boolean wordBreak2(String s, List<String> wordDict) {
        if(s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) return false;
        boolean dp[] = new boolean[s.length() + 1];
        // 初始化dp[0]
//        if(wordDict.contains(s.substring(0,1))){
//            dp[0] = true;
//        }
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            // 计算出dp[1]~dp[s.length() - 1]
            boolean hasFind = false;
            for (int j = 0; j < i; j++) {
                // 【错误1】 由于如果是从头开始某几个字符组成的包含，但之前的都不符合，那必然有问题，
                // 因此，我们需要定义dp[0]表示没有字符的情况下结果
                if(dp[j]){
                    // 判断从j(不包括，由于dp[0]表示没有字符的情况，因此不用+1)到i（包括）是否符合要求
                    if(wordDict.contains(s.substring(j, i))){
                        // 找到一个，就不需要继续找了
                        dp[i] = true;
                        hasFind = true;
                        break;
                    }
                }
            }
            if(!hasFind){
                dp[i] = false;
            }
        }
        return dp[s.length()];
    }

}
//leetcode submit region end(Prohibit modification and deletion)
