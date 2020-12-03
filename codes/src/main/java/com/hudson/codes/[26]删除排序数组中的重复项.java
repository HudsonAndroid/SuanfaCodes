package com.hudson.codes;//给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
//
// 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。 
//
// 
//
// 示例 1: 
//
// 给定数组 nums = [1,1,2], 
//
//函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。 
//
//你不需要考虑数组中超出新长度后面的元素。 
//
// 示例 2: 
//
// 给定 nums = [0,0,1,1,1,2,2,3,3,4],
//
//函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
//
//你不需要考虑数组中超出新长度后面的元素。
// 
//
// 
//
// 说明: 
//
// 为什么返回数值是整数，但输出的答案是数组呢? 
//
// 请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。 
//
// 你可以想象内部操作如下: 
//
// // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
//int len = removeDuplicates(nums);
//
//// 在函数里修改输入数组对于调用者是可见的。
//// 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
//for (int i = 0; i < len; i++) {
//    print(nums[i]);
//}
// 
// Related Topics 数组 双指针 
// 👍 1736 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution26 {
    // 个人觉得这个题目有难度

    // 思路：由于不能使用额外的内存空间，因此只能在原数组上操作
    // 根据题意，我们的目前其实就是把所有重复的数字移到数组的末端
    // 并只保留一个即可。
    // 我们最终的期望是，数字是连续增加的，而不会有重复数字
    // 因此考虑使用两个指针，p和q，p（包括自己）前面的数字保持不重复
    // 最初状态下,q = p + 1， 比较两个数，如果不同，那么p所代表的
    // 前面数字不重复可以后移，因此p和q同时后移；如果相同，那么我们
    // 需要找到一个数字替换掉p+1的位置（因为它和p相同），以确保数字不同
    // 我们一直找，直到找到不同的数字，并替换，这个时候，p又可以往前
    // 移动一格，q也往前移一格【因为前面交换后，当前p的值必然比旧的p+1的值大，升序的】,
    // 我们只需要保证p+1的位置与p的位置不重复，并且维持住升序状态，其他不需要管
    // 终止条件：q到末尾了
    // 1 2 2 3 3 4 =>  1 2 3 2 3 4 => 1 2 3 4 3 2
    public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        int p = 0;
        // 注意： q后面不受p的影响
        // 直到找到不重复的数字，跟p+1交换
        for (int q = 1; q < nums.length; q++) {
            if(nums[q] != nums[p]){
                nums[p + 1] = nums[q];
                // 这时不重复的end下标加1
                p ++;
            }
        }
        return p + 1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
