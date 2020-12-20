package com.hudson.codes;//给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
//
// 
//
// 说明： 
//
// 
// 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。 
// 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。 
// 
//
// 
//
// 示例： 
//
// 
//输入：
//nums1 = [1,2,3,0,0,0], m = 3
//nums2 = [2,5,6],       n = 3
//
//输出：[1,2,2,3,5,6] 
//
// 
//
// 提示： 
//
// 
// -10^9 <= nums1[i], nums2[i] <= 10^9 
// nums1.length == m + n 
// nums2.length == n 
// 
// Related Topics 数组 双指针 
// 👍 717 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution88 {
    // 时间100%，空间57.42%
    // 分析，m的数组长度足够容纳n
    // [1 4 7 0 0 0]
    // 要插入的数组[2 5 6]
    // [1 2 7 0 0 0] 待定4
    // 接下来，继续尝试插入数组的下一个元素5，发现比待定的4还大，那么这时要
    // 插入的应该是4，插入数组待定
    // 那么这种方式是否很好呢？
    // 假设插入数组是[2 3 5]
    // [1 2 7 0 0 0] 待定4
    // 这时你会发现，接下来插入的是3，3比待定数还小，那么结果是
    // [1 2 3 0 0 0] 待定4和7
    // 想象以下，如果不是7，而是一个很大的数，那么待定列表将会超级长
    // 其实这个问题最主要还是由于数组难以移动导致的，由于插入前面，会导致后面可能出现元素堵车的情况
    // 题目已经给定了足够的空间，那从后面往前插入，不就没问题了吗！！！！！！
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
//        if(nums1 == null) 这个我就懒得写了
        int last = m + n - 1;
        int firstLast = m - 1;
        int secondLast = n - 1;
        while(last >= 0){
            if(firstLast >= 0){
                if(secondLast >= 0){
                    if(nums1[firstLast] >= nums2[secondLast]){
                        nums1[last] = nums1[firstLast];
                        firstLast--;
                    }else{
                        nums1[last] = nums2[secondLast];
                        secondLast--;
                    }
                }else{
                    nums1[last] = nums1[firstLast];
                    firstLast--;
                }
            }else{
                if(secondLast >= 0){
                    nums1[last] = nums2[secondLast];
                    secondLast--;
                }
            }
            last--;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
