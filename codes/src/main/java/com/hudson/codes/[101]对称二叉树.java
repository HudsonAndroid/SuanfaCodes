package com.hudson.codes;//给定一个二叉树，检查它是否是镜像对称的。
//
// 
//
// 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。 
//
//     1
//   / \
//  2   2
// / \ / \
//3  4 4  3
// 
//
// 
//
// 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的: 
//
//     1
//   / \
//  2   2
//   \   \
//   3    3
// 
//
// 
//
// 进阶： 
//
// 你可以运用递归和迭代两种方法解决这个问题吗？ 
// Related Topics 树 深度优先搜索 广度优先搜索 
// 👍 1164 👎 0


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
class Solution101 {
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
    
    // 100% 82.29%
    // 很简单，既然对称，那么先左后根后右 = 先右后根后左
    public static boolean isSymmetric(TreeNode root) {
        return compare(root, root);
    }

    private static boolean compare(TreeNode root, TreeNode copy){
        if(root == null && copy == null) return true;
        if(root == null || copy == null) return false;
        if(root.val == copy.val){
            // 继续判断, left是否等于right
            if(compare(root.left, copy.right)){
                return compare(root.right, copy.left);
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
