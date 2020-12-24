package com.hudson.codes;//给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。 
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
// 返回锯齿形层序遍历如下： 
//
// 
//[
//  [3],
//  [20,9],
//  [15,7]
//]
// 
// Related Topics 栈 树 广度优先搜索 
// 👍 345 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution103 {
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
    
    // 100%  65.73%
    // 我们尝试不使用栈实现
    // 即Z字型走势
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, false, 0, root);
        return result;
    }


    private static void backtrack(List<List<Integer>> result, boolean reverse, int level, TreeNode node){
        if(node == null) return ;
        if(result.size() <= level){
            // 需要新建一个集合存储新一层的数据
            List<Integer> item = new ArrayList<>();
            result.add(item);
        }
        // 我们给当前的节点所属的层的集合添加该元素的值
        List<Integer> curLevelList = result.get(level);
        if(reverse){ // reverse的情况与level绑定
            curLevelList.add(0,node.val);// 如果是反向，那么添加到第一个位置
        }else{
            curLevelList.add(node.val);
        }
        // 继续
        backtrack(result, !reverse, level + 1, node.left);
        backtrack(result, !reverse, level + 1, node.right);
    }



    // 98.42%   65.73% 
    // 使用栈的方法
    // 这里题目其实可能使用队列还比栈简单。因为我们遍历node必然先left后right
    // 会导致必然先出right后出left，例如
    //     3
    //  9     20
    //      15    7
    // 在节点20往9的过程中，遍历20节点时，必然先left15后right7，这导致right 7 在后
    // 因为是20往9出发，下一层应该也按照这种方式遍历子节点，即从right到left，
    // 因此，我们不得不保留一个flag标识来表明从left->right还是right->left
    public static List<List<Integer>> zigzagLevelOrder2(TreeNode root){
        if(root == null) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> helpStack = new Stack<>();// 备用栈用于协助在某一层数据出栈时存储工作，完成一层的时候，交换两者功能
        Stack<TreeNode> tmp;
        int levelCount = 1;
        int nextLevelCount = 0;
        stack.push(root);
        boolean reverse = false;
        while(!stack.isEmpty()){
            List<Integer> item = new ArrayList<>();
            // 先出栈，把当前层的所有数据全部pop出来
            while(levelCount > 0){
                TreeNode node = stack.pop();
                item.add(node.val);
                // 其实呢，如果是栈的话，按照下面逻辑，必然最后right先出来，left后出来，因此需要一个flag
                if(!reverse){
                    if(node.left != null){
                        helpStack.push(node.left); // 仅一个栈在pop之后push会影响结果
                        nextLevelCount++;
                    }
                    if(node.right != null){
                        helpStack.push(node.right);
                        nextLevelCount++;
                    }
                }else{
                    if(node.right != null){
                        helpStack.push(node.right);
                        nextLevelCount++;
                    }
                    if(node.left != null){
                        helpStack.push(node.left); // 仅一个栈在pop之后push会影响结果
                        nextLevelCount++;
                    }
                }
                levelCount--;
            }
            // 这一层出去完毕，添加到最终结果中
            result.add(item);
            levelCount = nextLevelCount;
            nextLevelCount = 0;
            // 交换两个栈的功能
            tmp = stack;
            stack = helpStack;
            helpStack = tmp;
            //
            reverse = !reverse;
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
