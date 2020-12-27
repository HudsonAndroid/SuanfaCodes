package com.hudson.codes;//根据一棵树的前序遍历与中序遍历构造二叉树。 
//
// 注意: 
//你可以假设树中没有重复的元素。 
//
// 例如，给出 
//
// 前序遍历 preorder = [3,9,20,15,7]
//中序遍历 inorder = [9,3,15,20,7] 
//
// 返回如下的二叉树： 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7 
// Related Topics 树 深度优先搜索 数组 
// 👍 807 👎 0


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
class Solution105 {

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

    // 48.69%  59.34%
    // 思路对了，就对了
    // 我们知道，preorder的第一个必然是根节点；inorder的对应的根节点把
    // 内部分成左右两个子树
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeInner(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private static TreeNode buildTreeInner(int[] preorder, int start, int end, int[] inorder, int inStart, int inEnd){
        // 从preorder中找出根节点
        if(start <= end){
            int rootVal = preorder[start];
            TreeNode root = new TreeNode(rootVal);
            // 继续从inorder中找出左子树和右子树
            int rootIndex = -1;
            for (int i = inStart; i <= inEnd; i++) {
                if(inorder[i] == rootVal){
                    rootIndex = i;
                    break;
                }
            }
            // 那么左子树就是inStart到rootIndex（不包括）的位置；右子树是rootIndex（不包括）到inEnd的位置
            // 同时，我们preorder的左子树是start + 1延申长度为rootIndex - inStart的内容
            int len = rootIndex - inStart;
            // 【错误1】不是start + 1，就是start
            int preEnd = start /*+ 1 */+ len;
            root.left = buildTreeInner(preorder, start + 1, preEnd, inorder, inStart, rootIndex - 1);
            root.right = buildTreeInner(preorder, preEnd + 1, end, inorder, rootIndex + 1, inEnd);
            return root;
        }
        // 说明找不到根节点，错误，返回null
        return null;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
