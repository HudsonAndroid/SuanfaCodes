package com.hudson.codes;//给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
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
//         /  \    / \
//        7    2  5   1
// 
//
// 返回: 
//
// [
//   [5,4,11,2],
//   [5,8,4,5]
//]
// 
// Related Topics 树 深度优先搜索 
// 👍 395 👎 0


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
class Solution113 {
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

    //100%  91.42%
    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
        if(root == null) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        backtrack(root, sum, new ArrayList<Integer>(), result);
        return result;
    }

    private static void backtrack(TreeNode root, int sum, List<Integer> item, List<List<Integer>> result){
        if(root == null) // 如果节点已经是空的了，那么必然无法比对上sum 【注意，sum不可能为0】
            return ;
        if(root.val == sum && root.left == null && root.right == null){
            // 已经到了叶子节点了，且符合要求
            item.add(root.val);
            result.add(new ArrayList<Integer>(item));
            // 【错误】这边添加了，也要回溯
            item.remove(item.size() - 1);
        }else{
            // 还需要进一步判断
            item.add(root.val);
            // 检查左边
            backtrack(root.left, sum - root.val, item, result);
//            // 回溯【由于节点可能存在重复的数字，因此我们不能仅通过remove(object)操作，容易误删除前面的数】
//            item.remove(item.size() - 1);// 移除最后一个元素
            // 检查右边
            backtrack(root.right, sum - root.val, item, result);
            // 回溯【仅需要回溯一次】
            item.remove(item.size() - 1);// 移除最后一个元素
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
