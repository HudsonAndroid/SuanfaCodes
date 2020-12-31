package com.hudson.codes;//给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
//
// 返回 s 所有可能的分割方案。 
//
// 示例: 
//
// 输入: "aab"
//输出:
//[
//  ["aa","b"],
//  ["a","a","b"]
//] 
// Related Topics 深度优先搜索 动态规划 回溯算法 
// 👍 448 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution131 {

//    public static void main(String[] args){
//        List<List<String>> aab = partition("aab");
//        for (List<String> strings : aab) {
//            for (String string : strings) {
//                System.out.print(" "+string);
//            }
//            System.out.println();
//        }
//    }


    // 32.74%   18.49%
    // 回溯法
    // 例如aab，我们需要查看所有的情况是
    // a a b
    // a ab
    // aa b
    // aab
    // 实际上我们会发现，所有子集实际上是一个，分割出a，然后剩下的继续分割
    // 分割出aa，剩下的继续分割，
    // 分割出aab，剩下的继续分割,
    // 如果其中任何一个出现不是回文串的情况，就结束
    // 如果到了最后无法分割了发现且判断正确，则添加到集合中
    public static List<List<String>> partition(String s) {
        if(s == null || s.length() == 0) return new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        split(s, result, 0, s.length(), new ArrayList<String>());
        return result;
    }

    // item不需要重新new，因为每次加入结果，我们都是以这个item创建的新的list
    private static void split(String s, List<List<String>> result, int start, int end, List<String> item){
        if(start == end){
            // 那么已经符合要求了，那么添加
            result.add(new ArrayList<String>(item));
            return ;
        }
        // 从分割第一个字符开始，逐步分割
        for (int i = start; i < end; i++) {
            // 检查分割出来的字符串是否符合要求
            if(isRightAnswer(s, start, i)){
                // 符合要求，添加到临时集合中
                item.add(s.substring(start, i + 1));
                // 把剩余的部分分割
                split(s, result, i + 1, end, item);
                // 回溯
                item.remove(item.size() - 1);
            }
            // 不符合要求，继续从头部开始分割
        }
    }

    private static boolean isRightAnswer(String s, int left, int right){
        if(left > right) return false;
        while(left <= right){
            if(s.charAt(left) == s.charAt(right)){
                left ++;
                right--;
                continue;
            }
            return false;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
