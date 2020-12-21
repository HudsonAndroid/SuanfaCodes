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
        Main.TreeNode left;
        Main.TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, Main.TreeNode left, Main.TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //93.34%  76.49%
    // 队列特性，先进先出
    //
    public static List<List<Integer>> levelOrder(Main.TreeNode root) {
        if(root == null) return new ArrayList<>();
        // 队列，LinkedList
        Queue<Main.TreeNode> queue = new LinkedList<>();
        queue.offer(root);//入队列
        int levelCount = 1;
        int nextLevelCount = 0;
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> item = new ArrayList<>();
        while(!queue.isEmpty()){
            Main.TreeNode node = queue.poll();
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
}
//leetcode submit region end(Prohibit modification and deletion)
