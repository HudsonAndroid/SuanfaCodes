package com.hudson.codes;//给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
//
// 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。 
//
// 
//
// 进阶： 
//
// 
// 你可以不使用代码库中的排序函数来解决这道题吗？ 
// 你能想出一个仅使用常数空间的一趟扫描算法吗？ 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,0,2,1,1,0]
//输出：[0,0,1,1,2,2]
// 
//
// 示例 2： 
//
// 
//输入：nums = [2,0,1]
//输出：[0,1,2]
// 
//
// 示例 3： 
//
// 
//输入：nums = [0]
//输出：[0]
// 
//
// 示例 4： 
//
// 
//输入：nums = [1]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// n == nums.length 
// 1 <= n <= 300 
// nums[i] 为 0、1 或 2 
// 
// Related Topics 排序 数组 双指针 
// 👍 732 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution75 {
    // 感觉这道题属于难题

    // 很简单，使用快排的思路很快解决（其实他就是排序问题）
    // 但题目要求不能使用排序函数，因此我们思考别的方法，并空间O(1)时间O(n)
    // 还是双指针，P0和P1，P0负责找等于0的值，P1负责找到等于1的值
    // 为什么要这样，而不是头尾两个找0和2呢？【其实头尾也可以】
    // 因为我们一旦确定P0的位置是0了，那么P0可以增加1，相应地P1也要增加1，因为
    // 1不能放到P0之前的位置;
    // 直到p1> p0的时候，我们只移动P1
    //p0描述0的右边界,p1描述1的右边界
    //
    //2   0   2   1   1   0   1   0
    //p0
    //p1
    //
    //0   2   2   1   1   0   1   0
    //    p0
    //	p1
    //
    //0   1   2   2   1   0   1   0
    //    p0  p1
    //
    //0   1   1   2   2   0   1   0
    //    p0      p1
    //0   0   1   2   2   1   1   0
    //        p0  p1     (p1>p0,而交换了0，必然把p1换过的位置交换了，因此把p1与该位置交换)
    //
    //0   0   1   1   2   2   1   0
    //		  p0      p1
    // 为什么只考虑0和1的右边界，因为是数组，如果考虑左边界的话，是没法往数组中插入某个数并后移的
    public static void sortColors(int[] nums) {
        if(nums == null || nums.length == 0) return ;
        int p0 = 0;
        int p1 = p0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 0){
                // 那么，该位置可以与p0交换
                swap(nums, i, p0);
                // 如果p0要交换的时候，p1已经跑在前面了，那么p0的位置一定是1，即p1已经换过的位置，
                // 因此1会被换到i的位置上，所以需要把1换到p1现在的位置，并p1要增加1
                if(p0 < p1){
                    swap(nums, p1, i);
                    p1++;
                }
                p0 ++;
                if(p0 > p1) p1 = p0;// p1不可能小于p0
            }else if(nums[i] == 1){
                // 那么该位置可以与p1交换
                swap(nums, i, p1);
                p1 ++;
            }
        }
    }

    // time 100%, 空间 85.32%
    // 头尾的方式，确保p0的位置不是0， p2的位置不是2，然后交换
    // p0从头往后扫过的和p2从尾往前扫过的部分不需要遍历
    //1   0    2   1    1   0
    //p0                    p2
    //0   1    2   1    1   0
    //    p0                p2
    //0   1    0   1    1   2
    //    p0            p2
    //
    //0   0    1   1    1   2
    //        p0        p2
    public static void sortColors2(int[] nums){
        int p0 = 0;
        int p2 = nums.length - 1;
        while (p2 >= 0 && nums[p2] == 2){
            p2--;
        }
        if(p2 < 0) return ;
        while (p0 < nums.length && nums[p0] == 0){
            p0++;
        }
        if(p0 >= nums.length) return;
        if(p0 >= p2) return ;
//        while(p0 < p2){
//
//        }
        for (int i = p0; i < nums.length; i++) {
            if(nums[i] == 0){
                // 把它与p0位置交换
                swap(nums, p0, i);
                // 由于i >= p0，而p0的位置不可能是2，因为已经遍历过了，因此不需要判断交换后nums[i]为2的情况
                // 当然有一种特殊情况，例如 2 1 2
                if(nums[i] == 2){
                    swap(nums, p2, i);
                    p2 --;
                }
                p0++;
                // 确保p0位置不是0
                while(p0 < nums.length && nums[p0] == 0){
                    p0 ++;
                }
            }else if(nums[i] == 2){
                // 把它与p2位置交换
                swap(nums, p2, i);
                // 如果交换后，nums[i]是0那么还需要把它跟p0交换，因为我们能确保i之前没有0再出现了
                if(nums[i] == 0){
                    swap(nums, p0, i);
                    p0++;
                }
                p2--;
                // 确保p2的位置不是2
                while(p2 >= 0 && nums[p2] == 2){
                    p2--;
                }
            }
            // 如果i>=p2了，就不需要再进行
            if(i >= p2) break;
        }
    }

    private static void swap(int[] nums, int first, int second){
        if (first == second) return ;
        int tmp = nums[first];
        nums[first] = nums[second];
        nums[second] = tmp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
