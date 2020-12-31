package com.hudson.codes;//给定一个非空二叉树，返回其最大路径和。
//
// 本题中，路径被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点。 
//
// 
//
// 示例 1： 
//
// 输入：[1,2,3]
//
//       1
//      / \
//     2   3
//
//输出：6
// 
//
// 示例 2： 
//
// 输入：[-10,9,20,null,null,15,7]
//
//   -10
//   / \
//  9  20
//    /  \
//   15   7
//
//输出：42 
// Related Topics 树 深度优先搜索 递归 
// 👍 830 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution124 {
    // 这个题目题解没看懂，是照着别人的写的【困难】
    public int maxPathSum(Main.TreeNode root) {
        if(root == null) return 0;
        dfs(root);
        return max;
    }

    static int max = Integer.MIN_VALUE;

    private static int dfs(Main.TreeNode root){
        if(root == null) return 0;
        // 计算左边分支最大值，左边分支如果是负数，那么还不如不选
        int leftMax = Math.max(0, dfs(root.left));
        // 计算右边分支最大值，右边分支如果是负数，那么还不如不选
        int rightMax = Math.max(0, dfs(root.right));
        // 使用left->root->right作为路径与已经计算过的最大值比较
        max = Math.max(max, root.val + leftMax + rightMax);
        // 返回经过root的单边最大分支给当前的root的父节点计算使用
        return root.val + Math.max(leftMax, rightMax);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
