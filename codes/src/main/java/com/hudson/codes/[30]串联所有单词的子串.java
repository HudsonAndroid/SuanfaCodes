package com.hudson.codes;//给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
//
// 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。 
//
// 
//
// 示例 1： 
//
// 输入：
//  s = "barfoothefoobarman",
//  words = ["foo","bar"]
//输出：[0,9]
//解释：
//从索引 0 和 9 开始的子串分别是 "barfoo" 和 "foobar" 。
//输出的顺序不重要, [9,0] 也是有效答案。
// 
//
// 示例 2： 
//
// 输入：
//  s = "wordgoodgoodgoodbestword",
//  words = ["word","good","best","word"]
//输出：[]
// 
// Related Topics 哈希表 双指针 字符串 
// 👍 387 👎 0


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution30 {
    // 通过，时间超过40.88%，空间超过52.38%
    // 滑动窗口 + Map比对
    // 这道题拿到最直观的思路就是滑动窗口
    // 可是问题是，我们比对的目标是一个变动的状态，即所有子串的排列
    // 我们问题的症结就在于比对的目标状态太多了，不可能针对每一个都比对一次。
    // 那么关键就在于怎么解决比对的逻辑。
    // 由于是是所有排列，我们并不关系怎么排列的，因此需要联想到Map结构
    // 在我们滑动窗口中记录刚好匹配的字符串出现的次数，与我们实际目标的字符串
    // 次数，如果两者字符串出现次数相同，那么比对成功，返回结果
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        // 确定滑动窗口的长度，同时使用一个HashMap作为目标words，以用于遍历过程中的对比对象
        int len = 0;
        HashMap<String, Integer> target = new HashMap<>();
        for (String word : words) {
            Integer count = target.get(word);
            count = count == null ? 0 : count;
            target.put(word, ++count);
            len += word.length();
        }
        // 开始滑动窗口
        int left = 0;
        int right = len - 1;
        // 根据题意，目标words中每个word长度相同，因此记录长度
        int eachLen = words[0].length();
        HashMap<String, Integer> divide = new HashMap<>();
        while(right < s.length()){
            divide.clear();//清空
            // 判断该窗口内的字符串是否符合要求
            int wordStart = left;
            boolean isNeedMatch = true;
            while(wordStart + eachLen - 1 <= right){
                String divideWord = s.substring(wordStart, wordStart + eachLen);
                Integer count = target.get(divideWord);// 获取参考中是否有该字符串
                if(count != null){// 说明比对到了某个字符串
                    // 把比对到的，增加到记录的map中
                    // 【错误1】我们加的是自己重新累加的，不是使用target的结果
                    Integer divideCount = divide.get(divideWord);
                    divideCount = divideCount == null ? 0 : divideCount;
                    divide.put(divideWord, ++divideCount);
                    // 这是直接增加一个字符串的长度比对
                    wordStart += eachLen;
                    continue;// 继续比对
                }
                // 没有比对到，直接结束，因此该窗口不符合要求
                isNeedMatch = false;// 如果有一个不匹配，直接后面两个HashMap就不再需要比对情况了
                break;
            }
            if(isNeedMatch){
                // 比对两个HashMap结果，如果所包含的字符串次数相同，则符合题意
                boolean isMatch = true;
                for (String key : target.keySet()) {
                    if(!target.get(key).equals(divide.get(key))){
                        // 如果有一个不匹配，直接失败，
                        isMatch = false;
                        break;
                    }
                }
                if(isMatch){
                    result.add(left);
                }
            }
            // 滑动窗口右移，由于长度固定，因此同时增加
            left ++;
            right ++;
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
