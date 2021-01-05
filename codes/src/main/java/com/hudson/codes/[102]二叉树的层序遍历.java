package com.hudson.codes;//给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
//
// 
//
// 示例： 
//二叉树：[3,9,20,null,null,15,7], 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回其层序遍历结果： 
//
// 
//[
//  [3],
//  [9,20],
//  [15,7]
//]
// 
// Related Topics 树 广度优先搜索 
// 👍 723 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution102 {
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

    //93.34%  76.49%
    // 队列特性，先进先出
    //
    public static List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null) return new ArrayList<>();
        // 队列，LinkedList
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);//入队列
        int levelCount = 1;
        int nextLevelCount = 0;
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> item = new ArrayList<>();
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            // 每次出队列都需要对当前节点的left和right入队列，而不是根据levelCount决定
//            if(levelCount > 0){
//                // 说明当前层还有
//                item.add(node.val);
//                // 为下一层做准备
//                if(node.left != null){
//                    queue.offer(node.left);
//                    nextLevelCount++;
//                }
//                if(node.right != null){
//                    queue.offer(node.right);
//                    nextLevelCount++;
//                }
//            }else{
//                // 当前层已经没有了，因此切换到下一层
//                levelCount = nextLevelCount;
//                nextLevelCount = 0;
//                // 把上一层的结果加入到最终列表中
//                result.add(item);
//                item = new ArrayList<>();
//                item.add(node.val);
//            }
            if(levelCount <= 0){
                // 当前层已经没有了，因此切换到下一层
                levelCount = nextLevelCount;
                nextLevelCount = 0;
                // 把上一层的结果加入到最终列表中
                result.add(item);
                item = new ArrayList<>();
            }
            // 为下一层做准备
            if(node.left != null){
                queue.offer(node.left);
                nextLevelCount++;
            }
            if(node.right != null){
                queue.offer(node.right);
                nextLevelCount++;
            }
            item.add(node.val);
            levelCount--;
        }
        // 最后一层结果需要添加
        result.add(item);
        return result;
    }

    // 另一种方法  DFS,不用管是啥
    // 根据层数，把结果放入到指定位置的集合中
    public static List<List<Integer>> levelOrder2(TreeNode root) {
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
            result.add(item);
        }
        // 注意，这里不能直接使用item，应该是最后一个的列表，因为在下面代码中，会把item引用传递给left和right，而left和right必须使用另一层的同一个集合
        result.get(level).add(node.val);// 把当前的值加入当前层的列表中
        // 继续添加它的左右子节点
        backtrack(result, item, node.left, level + 1);
        backtrack(result, item, node.right, level + 1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
