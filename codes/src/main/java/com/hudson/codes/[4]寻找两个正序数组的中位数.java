package com.hudson.codes;//给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
//
// 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？ 
//
// 
//
// 示例 1： 
//
// 输入：nums1 = [1,3], nums2 = [2]
//输出：2.00000
//解释：合并数组 = [1,2,3] ，中位数 2
// 
//
// 示例 2： 
//
// 输入：nums1 = [1,2], nums2 = [3,4]
//输出：2.50000
//解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
// 
//
// 示例 3： 
//
// 输入：nums1 = [0,0], nums2 = [0,0]
//输出：0.00000
// 
//
// 示例 4： 
//
// 输入：nums1 = [], nums2 = [1]
//输出：1.00000
// 
//
// 示例 5： 
//
// 输入：nums1 = [2], nums2 = []
//输出：2.00000
// 
//
// 
//
// 提示： 
//
// 
// nums1.length == m 
// nums2.length == n 
// 0 <= m <= 1000 
// 0 <= n <= 1000 
// 1 <= m + n <= 2000 
// -106 <= nums1[i], nums2[i] <= 106 
// 
// Related Topics 数组 二分查找 分治算法 
// 👍 3445 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution4 {
    // 简单暴力法：合并数组，然后求中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = 0,n = 0;
        if(nums1 != null){
            m = nums1.length;
        }
        if(nums2 != null){
            n = nums2.length;
        }
        int[] nums = new int[n + m];
        if(n == 0){
            //如果第二个数组为空，则直接计算
            if(m % 2 == 0){
                //错误1： 中位数=（长度/2-1 + 下一个数）/2。注意：我们平时口语的第几个数，都是从1开始，没有说第0个数
                //错误2：必须除以double类型，否则会自动转为int
                return (nums1[m /2] + nums1[m/2 - 1]) /2.0;
            }else{
                return nums1[m/2];//偶数的话，刚好是中间那个数，下标刚好是 length/2
            }
        }
        if(m == 0){
            //如果第一个数组为空，则直接计算
            if(n % 2 == 0){
                return (nums2[n /2] + nums2[n/2 - 1]) /2.0;
            }else{
                return nums2[n/2];
            }
        }
        //合并数组
        int index = 0, i = 0, j = 0;
        while(index < (m + n)){
            if(i == m){
                // 第一个数组已经达到边界了
                while(j != n){
                    nums[index++] = nums2[j++];
                }
                break;
            }
            if(j == n){
                while(i != m){
                    nums[index++] = nums1[i++];
                }
                break;
            }
            if(nums1[i] > nums2[j]){
                nums[index++] = nums2[j++];
            }else{
                nums[index++] = nums1[i++];
            }
        }
        int length = nums.length;
        if(length % 2 == 0){
            return (nums[length/2] + nums[length/2 - 1])/2.0;
        }else{
            return nums[length/2];
        }
    }

    //优化，由于我们只需要找到中间的数，未必一定要合并数组，因此找到指定数即可
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int m = 0,n = 0;
        if(nums1 != null){
            m = nums1.length;
        }
        if(nums2 != null){
            n = nums2.length;
        }
        int all = m + n;
        int firstIndex = 0, secondIndex = 0;
        int left = -1, right = -1;
        //由于不管和是奇数个还是偶数个，都要遍历 all/2+1次，因此
        for (int i = 0; i < all / 2 + 1; i++) {
            left = right;
            //与方法1类似，一直走下去，直到目标
            // 需要让第一个数组继续下去的理由是： 当前下标没有到最后且当前的值小于第二个数组的值; 当前下标没有到最后且第二个数组已经遍历完了
            // 错误1： 括号内的表达式放反了
            if(firstIndex < m && (secondIndex >= n || nums1[firstIndex] < nums2[secondIndex])){
                right = nums1[firstIndex++];
            }else{
                right = nums2[secondIndex++];
            }
        }
        // 使用 >> 运算符表示 /2更加高效；使用 & 1表示%2
        if((all & 1) == 0){
            return (left + right) /2.0;
        }
        return right;
    }

    // 详细参考：https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-2/
    //法3 分半切除法
    // 以 k/2 作为划分线，对nums1和nums2进行切除，一次性排除一大部分数字
    public static double findMedianSortedArrays3(int[] nums1, int[] nums2) {
        int m = 0,n = 0;
        if(nums1 != null){
            m = nums1.length;
        }
        if(nums2 != null){
            n = nums2.length;
        }
        int all = m + n;
        if((all & 1) == 0){
            // 1 2 3 4 找到第2和第3大的数   4 / 2  4 / 2 + 1  注意：第k大的数，并不是下标为k的数，是下标为k - 1的数
            return (findKthNum(nums1, 0, m -1, nums2, 0, n - 1, all >> 1)
                    + findKthNum(nums1, 0, m -1, nums2, 0, n - 1, (all >> 1) + 1)) * 0.5;
        }else{
            // 1 2 3 4 5 找到第3大的数是  5 / 2 + 1
            return findKthNum(nums1, 0, m - 1,nums2, 0, n - 1, (all >> 1) + 1);
        }
    }

    // 找到第k大的数字
    private static int findKthNum(int[] nums1,int start1, int end1, int[] nums2,int start2, int end2, int k) {
        int firstLen = end1 - start1 + 1;
        if(firstLen == 0){//第一个数组已经为空了
            // 找到第二个数组的第k大数即可
            return nums2[start2 + k - 1];
        }
        int secondLen = end2 - start2 + 1;
        if(secondLen == 0){//第二个数组为空了
            return nums1[start1 + k - 1];
        }
        if(k == 1){
            return Math.min(nums1[start1], nums2[start2]);
        }
        int index1 = start1 + Math.min(k / 2, firstLen) - 1;// 确定第一个数组的第k/2个数， 错误1：没有加start1
        int index2 = start2 + Math.min(k / 2, secondLen) - 1;
        if (nums1[index1] < nums2[index2]) {
            //第一个数组比第二个数组的第 index1个数小，因此第一个数组小于等于 index1这个数排除；并且更换成寻找第 k - (index1 - start1 + 1)
            return findKthNum(nums1, index1 + 1, end1, nums2, start2, end2, k - (index1 - start1 + 1));
        }else{
            return findKthNum(nums1, start1, end1, nums2, index2 + 1, end2, k - (index2 - start2 + 1));
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
