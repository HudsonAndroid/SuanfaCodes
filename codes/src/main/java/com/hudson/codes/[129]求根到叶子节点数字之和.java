package com.hudson.codes;//给定一个二叉树，它的每个结点都存放一个 0-9 的数字，每条从根到叶子节点的路径都代表一个数字。
//
// 例如，从根到叶子节点路径 1->2->3 代表数字 123。 
//
// 计算从根到叶子节点生成的所有数字之和。 
//
// 说明: 叶子节点是指没有子节点的节点。 
//
// 示例 1: 
//
// 输入: [1,2,3]
//    1
//   / \
//  2   3
//输出: 25
//解释:
//从根到叶子节点路径 1->2 代表数字 12.
//从根到叶子节点路径 1->3 代表数字 13.
//因此，数字总和 = 12 + 13 = 25. 
//
// 示例 2: 
//
// 输入: [4,9,0,5,1]
//    4
//   / \
//  9   0
// / \
//5   1
//输出: 1026
//解释:
//从根到叶子节点路径 4->9->5 代表数字 495.
//从根到叶子节点路径 4->9->1 代表数字 491.
//从根到叶子节点路径 4->0 代表数字 40.
//因此，数字总和 = 495 + 491 + 40 = 1026. 
// Related Topics 树 深度优先搜索 
// 👍 297 👎 0


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
class Solution129 {
//    public static void main(String[] args){
//        TreeNode root = new TreeNode(4);
//        root.left = new TreeNode(9);
//        root.right = new TreeNode(0);
//        root.left.left = new TreeNode(5);
//        root.left.right = new TreeNode(1);
//        int i = sumNumbers(root);
//        System.out.println(" "+i);
//    }

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
    
    // 这道题直接使用这种放到leetcode上运行与我们运行结果不一致，然后去掉了static之后，正常了，不知道是什么原因
    // 100%  50.72%
    // 我擦嘞，都不用考虑溢出的吗？
    static int sum = 0;
    // 遍历所有节点到叶子节点，然后计算和
    // 回溯法
    // 唯一的问题是，溢出了怎么办
    public static int sumNumbers(TreeNode root) {
        backtrack(root, 0);
        return sum;
    }

    private static void backtrack(TreeNode node, int curValue){
        if(node == null) return ;
        curValue = curValue * 10 + node.val;
        if(/*node != null && */node.left == null && node.right == null) {
            sum += curValue;
            return ;
        }
        backtrack(node.left, curValue);
        backtrack(node.right, curValue);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
