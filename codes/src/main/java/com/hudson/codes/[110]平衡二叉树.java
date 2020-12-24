package com.hudson.codes;//给定一个二叉树，判断它是否是高度平衡的二叉树。
//
// 本题中，一棵高度平衡二叉树定义为： 
//
// 
// 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [3,9,20,null,null,15,7]
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：root = [1,2,2,3,3,null,null,4,4]
//输出：false
// 
//
// 示例 3： 
//
// 
//输入：root = []
//输出：true
// 
//
// 
//
// 提示： 
//
// 
// 树中的节点数在范围 [0, 5000] 内 
// -104 <= Node.val <= 104 
// 
// Related Topics 树 深度优先搜索 递归 
// 👍 547 👎 0


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
class Solution110 {

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
    
    
    // 99.80%  59.36%
    // 很简单，判断每个非叶子节点的左右深度是否超标即可
    //
    public static boolean isBalanced(TreeNode root) {
//        if(root == null) return false;// 我本以为，外界输入null，应该返回false，谁知道啊谁知道，leetcode用例是true
        return isBalancedInner(root);
    }

    private static boolean isBalancedInner(TreeNode root){
        if(root == null) return true;
        if(Math.abs(depth(root.left) - depth(root.right)) > 1){
            // 判断root的左右两边的差距是否小于1
            return false;
        }
        // 继续判断左右子树
        if(isBalancedInner(root.left)){// 左子树平衡之外，右子树也要平衡
            return isBalancedInner(root.right);
        }
        return false;
    }

    private static int depth(TreeNode root){
        if(root == null) return 0;
        return Math.max(depth(root.left), depth(root.right)) + 1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
