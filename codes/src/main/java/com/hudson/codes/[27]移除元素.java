package com.hudson.codes;//给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
//
// 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。 
//
// 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。 
//
// 
//
// 示例 1: 
//
// 给定 nums = [3,2,2,3], val = 3,
//
//函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
//
//你不需要考虑数组中超出新长度后面的元素。
// 
//
// 示例 2: 
//
// 给定 nums = [0,1,2,2,3,0,4,2], val = 2,
//
//函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
//
//注意这五个元素可为任意顺序。
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
// // nums 是以“引用”方式传递的。也就是说，不对实参作任何拷贝
//int len = removeElement(nums, val);
//
//// 在函数里修改输入数组对于调用者是可见的。
//// 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
//for (int i = 0; i < len; i++) {
//    print(nums[i]);
//}
// 
// Related Topics 数组 双指针 
// 👍 724 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution27 {
    // 下面算法通过，值得注意的是特例情况[3,3,3,3] 3或者 [1] 1
    // 时间复杂度超过100%用户，空间复杂度超过47.81%的用户
    // 还是双指针，思路和26题差不多，我们目标是把等于目标的数移动到数组末尾
    // 使用两个指针，分别指向一头一尾，如果尾指针等于目标值，前移直到找到不等于目标值的数；
    // 如果头指针等于目标值，停止，与尾指针交换；如果不相等，前移
    // 全程保证头指针小于尾指针
    public static int removeElement(int[] nums, int val) {
        if(nums == null || nums.length == 0) return 0;
        int head = 0;
        int tail = nums.length - 1;
        while(head < tail){
            while(head < tail && nums[tail] == val){
                tail --;
            }
            while(head < tail && nums[head] != val){
                head ++;
            }
            // 使得所有等于该值的数移动到数组尾部去
            if(head < tail){
                int tmp = nums[tail];
                nums[tail] = nums[head];
                nums[head] = tmp;
            }
        }
        // 错误1：针对特殊情况，因为经过上面操作后，head或者说tail的位置可能也等于val
        if(nums[head] == val){
            head --;// 排除这个数字
        }
        // 由于我们求的是新数组长度，最终head == tail，因此长度等于 head或者tail 加1
        return head + 1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
