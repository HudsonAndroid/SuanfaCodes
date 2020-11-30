package com.hudson.codes;//给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i,
//ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。 
//
// 说明：你不能倾斜容器。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：[1,8,6,2,5,4,8,3,7]
//输出：49 
//解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。 
//
// 示例 2： 
//
// 
//输入：height = [1,1]
//输出：1
// 
//
// 示例 3： 
//
// 
//输入：height = [4,3,2,1,4]
//输出：16
// 
//
// 示例 4： 
//
// 
//输入：height = [1,2,1]
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// n = height.length 
// 2 <= n <= 3 * 104 
// 0 <= height[i] <= 3 * 104 
// 
// Related Topics 数组 双指针 
// 👍 2009 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution11 {
    public int maxArea(int[] height) {
        if(height != null && height.length >= 2){
            int left = 0;
            int right = height.length - 1;
            int max = 0;
            while(left < right){
                // 错误1：不需要+1的
                max = Math.max(max, (right - left /*+ 1*/) * Math.min(height[left], height[right]));
                //比较两边的值，取更小的往中间移动（因为更小的决定了高度，移动更小的才有可能找出更大的值;移动大的只可能 <=当前的）
                if(height[left] < height[right]){
                    left ++;
                }else{
                    right --;
                }
            }
            return max;
        }
        return -1;
    }

    public int maxArea2(int[] height) {
        if(height != null && height.length >= 2){
            int left = 0;
            int right = height.length - 1;
            int max = 0;
            while(left < right){
                max = height[left] < height[right] ? Math.max(max, (right - left) * height[left++])
                        : Math.max(max, (right - left) * height[right --]);
            }
            return max;
        }
        return -1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
