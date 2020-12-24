package com.hudson.codes;//给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。
//
// 
//
// 示例： 
//
// 输入：3
//输出：
//[
//  [1,null,3,2],
//  [3,2,null,1],
//  [3,1,null,null,2],
//  [2,1,3],
//  [1,null,2,null,3]
//]
//解释：
//以上的输出对应以下 5 种不同结构的二叉搜索树：
//
//   1         3     3      2      1
//    \       /     /      / \      \
//     3     2     1      1   3      2
//    /     /       \                 \
//   2     1         2                 3
// 
//
// 
//
// 提示： 
//
// 
// 0 <= n <= 8 
// 
// Related Topics 树 动态规划 
// 👍 733 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.List;

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
class Solution95 {
    // 太难了，做不出来
    //
    // 主要原因有1）无法判断什么时候，加入结果 2）回溯法的话会修改引用结果 3）需要先遍历所有可能的左子树再确定右子树，那确定过程中是相互嵌套的
    private static void printTreeNode(TreeNode node){
        if(node != null){
            // 注意检查leetcode结果出来的方式，我这里没继续做，所以没检查
            System.out.print(" " + node.val);
            if(node.left != null){
                printTreeNode(node.left);
            }else{
                System.out.print(" null");
            }
            if(node.right != null){
                printTreeNode(node.right);
            }else{
                System.out.print(" null");
            }
        }
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

    // 需要一个存储最初节点的变量，以便存储到result
    private static void backTrack(List<TreeNode> result, int leftStart, int rightEnd, TreeNode root, int maxLimit) {
        int rootVal = root.val;
        if(leftStart < 1 || leftStart >= root.val){
            // 无左子树（不能小于1，也不能大于根节点）
        }else{
            // 有左子树，遍历所有情况
            for (int i = leftStart; i < rootVal; i++) {
                root.left = new TreeNode(i);
                backTrack(result,leftStart, rootVal - 1, root.left, maxLimit);
                root.left = null;//回溯，回溯之后，就没法添加到结果集里面了，wtf，这道题我真不会了
            }
        }
        // 判断右节点
        if(rightEnd <= rootVal || rightEnd > maxLimit){
            // 没有右子树（不能大于最大值，也不能小于根节点）
        }else{
            // 有右子树
            for (int i = rootVal + 1; i <= rightEnd; i++) {
                root.right = new TreeNode(i);
                backTrack(result,rootVal + 1, rightEnd, root.right, maxLimit);
                root.right = null;// 回溯
            }
        }
    }

    private static void copy(TreeNode copy, TreeNode res){
        copy.val = res.val;
        if(res.left != null){
            copy.left = new TreeNode();
            copy(copy.left, res.left);
        }
        if(res.right != null){
            copy.right = new TreeNode();
            copy(copy.right, res.right);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
