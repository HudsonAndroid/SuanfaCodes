package com.hudson.codes;//给定一个二叉树，原地将它展开为一个单链表。
//
// 
//
// 例如，给定二叉树 
//
//     1
//   / \
//  2   5
// / \   \
//3   4   6 
//
// 将其展开为： 
//
// 1
// \
//  2
//   \
//    3
//     \
//      4
//       \
//        5
//         \
//          6 
// Related Topics 树 深度优先搜索 
// 👍 666 👎 0


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
class Solution114 {
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
    
    // 100%   91.28%
    // 二叉树转单链表
    // 很简单，先左后右
    // 补充下，最后是左节点都是null，右节点充当next
    public static void flatten(TreeNode root) {
        if(root == null) return ;
        iterate(root);
    }

    // 返回的是拼接后的最后一个节点
    private static TreeNode iterate(TreeNode root) {
        if(root == null) return null;
        // 备份右子树
        TreeNode right = root.right;
        if(root.left != null){
            root.right = root.left;
            TreeNode oldRoot = root;
            root = iterate(root.left);
            // 【注意】这时候，左节点没用了，置为Null
            oldRoot.left = null;
        }
        // 继续判断右子树
        if(right != null){
            root.right = right;
            root = iterate(right);
        }
        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
