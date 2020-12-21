package com.hudson.codes;//给定一个二叉树，判断其是否是一个有效的二叉搜索树。
//
// 假设一个二叉搜索树具有如下特征： 
//
// 
// 节点的左子树只包含小于当前节点的数。 
// 节点的右子树只包含大于当前节点的数。 
// 所有左子树和右子树自身必须也是二叉搜索树。 
// 
//
// 示例 1: 
//
// 输入:
//    2
//   / \
//  1   3
//输出: true
// 
//
// 示例 2: 
//
// 输入:
//    5
//   / \
//  1   4
//     / \
//    3   6
//输出: false
//解释: 输入为: [5,1,4,null,null,3,6]。
//     根节点的值为 5 ，但是其右子节点值为 4 。
// 
// Related Topics 树 深度优先搜索 递归 
// 👍 865 👎 0


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
class Solution98 {
    private static /*int*/long pre = Long.MIN_VALUE;

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

    // 【注意，下面是错误写法】
    // 【错误1】是左子树所有元素都不能大于根节点，右子树所有都不能小于根节点
    // 因此遗漏判断
    // 仅通过判断当前节点左节点和左节点的子节点小于当前节点；当前节点的右节点和右节点的子节点大于当前节点是无效的
    // 用例[120,70,140,50,100,130,160,20,55,75,110,119,135,150,200]【先序遍历】，右子树的119小于根节点120
    // 即，仅靠当前节点的儿子、孙子判断是否有效还不足以证明
    // 必须确保左子树所有节点小于根节点，右子树的所有节点大于根节点
    // 不能这么判断
    public static boolean isValidBST(TreeNode root) {
        if(root != null){
            int rootVal = root.val;
            if(root.left != null){
                // 除了左节点的根节点要小于root之外，左节点的左右子节点也要小于root
                boolean grandsonValid = (root.left.left == null ? true : root.left.left.val < rootVal) &&
                        (root.left.right == null ? true : root.left.right.val < rootVal);
                if(rootVal > root.left.val && grandsonValid){
                    if(!isValidBST(root.left)){
                        return false;
                    }
                }else{
                    return false;
                }
            }
            // 继续判断右子树
            if(root.right != null){
                // 除了右节点的根节点要大于root之外，右节点的左右子节点也要大于root
                boolean grandsonValid = (root.right.right == null ? true : root.right.right.val > rootVal) &&
                        (root.right.left == null ? true : root.right.left.val > rootVal);
                if(rootVal < root.right.val && grandsonValid){
                    if(!isValidBST(root.right)){
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }
        return true;
    }


    // 既然是二叉搜索树，那么中序遍历必然是从小到大的
    // 因此，我们保留一个pre表示前一个节点，判断当前节点大于前一个节点即可
    // 唯一的错误就是，外界输入Integer.MIN_VALUE的情况下，会导致pre与输入判断相等，导致返回false
    // wtf，因此pre要改成long
    public static boolean isValidBST2(TreeNode root){
        if(root == null){
            return true;
        }
        // 先访问左节点
        if(root.left != null){
            if(!isValidBST2(root.left)){
                return false;
            }
        }
        // 再访问根节点
        if(root.val <= pre){
            return false;
        }
        pre = root.val;
        // 最后访问右节点
        if(root.right != null) {
            if(!isValidBST2(root.right)){
                return false;
            }
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
