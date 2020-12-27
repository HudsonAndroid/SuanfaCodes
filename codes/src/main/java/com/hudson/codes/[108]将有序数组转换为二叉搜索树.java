package com.hudson.codes;//将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
//
// 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。 
//
// 示例: 
//
// 给定有序数组: [-10,-3,0,5,9],
//
//一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
//
//      0
//     / \
//   -3   9
//   /   /
// -10  5
// 
// Related Topics 树 深度优先搜索 
// 👍 661 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution108 {

    // 100%  93.36%

    //注意：leetcode的打印方式是先根，再左，再右，不会进一步推到左右节点的子节点上去，是层序遍历
    // 先序遍历打印
    private static void printTreeNode(TreeNode node){
        if(node == null) return ;
        System.out.print(" "+node.val);
        printTreeNode(node.left);
        printTreeNode(node.right);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 二叉搜索树要尽可能平衡，那么就是要root需要取在中间位置
    //
    public static TreeNode sortedArrayToBST(int[] nums) {
        if(nums == null || nums.length == 0) return null;
        int middle = (nums.length - 1) >> 1;
        TreeNode root = new TreeNode(nums[middle]);
        backtrack(nums, 0, middle - 1, root);
        backtrack(nums, middle + 1, nums.length - 1, root);
        return root;
    }

    private static void backtrack(int[] nums, int start, int end, TreeNode root){
        if(start > end || end < 0 || start >= nums.length) return ;
        // 尽可能取中间位置
        int middle = (start + end) >> 1;
        TreeNode curNode = new TreeNode(nums[middle]);
        if(root.val > nums[middle]){
            root.left = curNode;
        }else{
            root.right = curNode;
        }
        backtrack(nums, start, middle - 1, curNode);
        backtrack(nums, middle + 1, end, curNode);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
