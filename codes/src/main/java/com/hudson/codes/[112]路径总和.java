package com.hudson.codes;//给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
//
// 说明: 叶子节点是指没有子节点的节点。 
//
// 示例: 
//给定如下二叉树，以及目标和 sum = 22， 
//
//               5
//             / \
//            4   8
//           /   / \
//          11  13  4
//         /  \      \
//        7    2      1
// 
//
// 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。 
// Related Topics 树 深度优先搜索 
// 👍 484 👎 0


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
class Solution112 {
    // 很容易出错
    // 100%  68.39%
    //【1.一定是保证等于0即可，其他情况不考虑，因为有可能是负数】
    // 【2.必须保证叶子节点】
    public boolean hasPathSum(Main.TreeNode root, int sum) {
        if(root == null) return false;
        if(root.val == sum && root.left == null && root.right == null) return true;
        if(!hasPathSum(root.left, sum - root.val)){
            return hasPathSum(root.right, sum - root.val);
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
