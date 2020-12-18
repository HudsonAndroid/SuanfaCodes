package com.hudson.codes;//给定一个增序排列数组 nums ，你需要在 原地 删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。
//
// 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。 
//
// 
//
// 说明： 
//
// 为什么返回数值是整数，但输出的答案是数组呢？ 
//
// 请注意，输入数组是以“引用”方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。 
//
// 你可以想象内部操作如下： 
//
// 
//// nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
//int len = removeDuplicates(nums);
//
//// 在函数里修改输入数组对于调用者是可见的。
//// 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
//for (int i = 0; i < len; i++) {
//    print(nums[i]);
//} 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,1,2,2,3]
//输出：5, nums = [1,1,2,2,3]
//解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。 你不需要考虑数组中超出新长度后面的元素。
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,0,1,1,1,1,2,3,3]
//输出：7, nums = [0,0,1,1,2,3,3]
//解释：函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为 0, 0, 1, 1, 2, 3, 3 。 你不需要考虑数组中超出新长度后面
//的元素。
// 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 3 * 104 
// -104 <= nums[i] <= 104 
// nums 按递增顺序排列 
// 
// Related Topics 数组 双指针 
// 👍 335 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution80 {
    // 易错，虽然简单
    // 时间：88.06% 空间：69.44%
    // 同一个数字异或结果是0
    // 思路
    //1112233344
    //112x233344
    //1122x33344
    //112233x344
    //112233xx44
    //1122334xx4
    //11223344xx
    // 每发现一个多余的数，标记为x或者其他，然后不断后移
    public static int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        // 【错误3】因为我们直接取i=1，因此要排除长度为1的情况
        if(nums.length == 1) return 1;
        int referNum = nums[0];
        boolean isFull = false;// 是否已经达到了最大允许数2
        int invalidStartIndex = -1;
        for (int i = 1; i < nums.length; i++) {
            if((referNum ^ nums[i]) == 0){// 如果当前数字与参考数相同
                if(!isFull){ // 没有超过2个
                    isFull = true;
                }else {
                    if(invalidStartIndex == -1){
                        invalidStartIndex = i;// 如果非法位置没有初始化过，就初始化
                    }
                    continue;// 不能再判断无效数后移
                }
//                else{
//                    // 已经满了，因此把这个位置标记为x,由于没法标记，因此直接标记无效数的起始位置invalidIndex
//                    // 由于是排序数组的原因，如果当前i位置的数比invalidStartIndex的位置数大，那么交换两个数
//                }
            }else{
                // 与上一个数不同，因此修改参考数
                referNum = nums[i];
                // 【错误2】忘了恢复isFull
                isFull = false;
            }
            // 如果之前已经发现了无效数，就需要后移一位无效数序列（把第一个无效位与当前位交换）
            if(invalidStartIndex != -1){
                int tmp = nums[invalidStartIndex];
                nums[invalidStartIndex] = nums[i];
                nums[i] = tmp;
                // 【错误1】咱们忘了更新invalidStartIndex,注意新的位置是++,而不是i
                invalidStartIndex++;
            }
        }
        // 【错误4】如果原始输入已经符合要求了
        if(invalidStartIndex == -1){
            return nums.length;
        }
        return invalidStartIndex;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
