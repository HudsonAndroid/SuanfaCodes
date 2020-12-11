package com.hudson.codes;//给定一个非负整数数组，你最初位于数组的第一个位置。
//
// 数组中的每个元素代表你在该位置可以跳跃的最大长度。 
//
// 你的目标是使用最少的跳跃次数到达数组的最后一个位置。 
//
// 示例: 
//
// 输入: [2,3,1,1,4]
//输出: 2
//解释: 跳到最后一个位置的最小跳跃数是 2。
//     从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
// 
//
// 说明: 
//
// 假设你总是可以到达数组的最后一个位置。 
// Related Topics 贪心算法 数组 
// 👍 764 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution45 {
    // 这个题目官方解答和我最初思路类似，就是从后往前找，找距离目标位置最远的数
    // 可是问题是这一步你是保证了只需要使用一步完成，万一前面一定需要很多次才能完成，
    // 而我们一开始的跳到某个位置，这个位置比上面那个位置距离目标更近，但使用次数更少呢？
    //  .^.^......^  需要3步
    //  .......^..^ 只要2步
    // 然鹅官方好像就是这种方式解答的？如何证明是正确的？是最优解？
    // 所以这个问题，目前还不清楚哦
    public int jump(int[] nums) {
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
