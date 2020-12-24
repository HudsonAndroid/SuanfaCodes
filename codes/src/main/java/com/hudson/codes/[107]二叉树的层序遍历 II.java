package com.hudson.codes;//给定一个二叉树，返回其节点值自底向上的层序遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
//
// 例如： 
//给定二叉树 [3,9,20,null,null,15,7], 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回其自底向上的层序遍历为： 
//
// 
//[
//  [15,7],
//  [9,20],
//  [3]
//]
// 
// Related Topics 树 广度优先搜索 
// 👍 375 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution107 {
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

    // 98.37%  91.04%
    // 跟102类似，只不过反过来
    // 根据层数，把结果放入到指定位置的集合中
    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        if(root == null) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<Integer>(), root, 0);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> item, TreeNode node, int level){
        if(node == null) return ;
        // 如果当前的结果result中的列表数小于等于level，说明进入下一层，那么需要新建一个列表
        if(result.size() <= level){
            item = new ArrayList<>();
            result.add(0, item);// 新的一层应该添加到最开头的位置
        }
        // 注意，这里不能直接使用item，应该是最后一个的列表，因为在下面代码中，会把item引用传递给left和right，而left和right必须使用另一层的同一个集合
        // 当前层的列表应该处在size - 1 - level
        // 如果不是反过来的情况时，是从0往下延伸至level；现在反过来了，原来的第一层（0）是在size - 1的位置，因此往上延伸至level
        result.get(result.size() - 1 - level).add(node.val);// 把当前的值加入当前层的列表中
        // 继续添加它的左右子节点
        backtrack(result, item, node.left, level + 1);
        backtrack(result, item, node.right, level + 1);
    }


    // 当然，还可以使用队列的方式
}
//leetcode submit region end(Prohibit modification and deletion)
