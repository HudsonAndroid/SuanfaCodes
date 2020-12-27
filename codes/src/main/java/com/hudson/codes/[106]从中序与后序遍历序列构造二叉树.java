package com.hudson.codes;//根据一棵树的中序遍历与后序遍历构造二叉树。
//
// 注意: 
//你可以假设树中没有重复的元素。 
//
// 例如，给出 
//
// 中序遍历 inorder = [9,3,15,20,7]
//后序遍历 postorder = [9,15,7,20,3] 
//
// 返回如下的二叉树： 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// 
// Related Topics 树 深度优先搜索 数组 
// 👍 422 👎 0


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
class Solution106 {
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

    // 46.57%   85.43%
    // 中序遍历和后序遍历构造出结果
    // 与上一题类似，我们首先能通过postorder找出根节点
    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTreeInner(inorder, 0, inorder.length - 1,
                postorder, 0, postorder.length - 1);
    }

    private static TreeNode buildTreeInner(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd){
        // 首先找出根节点
        if(postStart <= postEnd){
            int rootVal = postorder[postEnd];
            TreeNode root =  new TreeNode(rootVal);
            int rootIndex = -1;
            // 继续找中序遍历中的
            for (int i = inStart; i <= inEnd; i++) {
                if(inorder[i] == rootVal){
                    rootIndex = i;
                    break;
                }
            }
            // 计算右边的长度
            int len = inEnd - rootIndex;
            // 由于后续遍历的是先左后右，最后根
            int newPostStart = postEnd - len;
            root.right = buildTreeInner(inorder, rootIndex + 1, inEnd, postorder, newPostStart, postEnd - 1);
            root.left = buildTreeInner(inorder,inStart, rootIndex - 1, postorder, postStart, newPostStart - 1);
            return root;
        }
        return null;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
