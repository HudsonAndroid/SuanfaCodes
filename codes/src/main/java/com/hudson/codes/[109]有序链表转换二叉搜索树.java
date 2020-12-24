package com.hudson.codes;//给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。 
//
// 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。 
//
// 示例: 
//
// 给定的有序链表： [-10, -3, 0, 5, 9],
//
//一个可能的答案是：[0, -3, 9, -10, null, 5], 它可以表示下面这个高度平衡二叉搜索树：
//
//      0
//     / \
//   -3   9
//   /   /
// -10  5
// 
// Related Topics 深度优先搜索 链表 
// 👍 434 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

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
class Solution109 {
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

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    
    //54.45%  88.95%
    // 方法1：转成数组，然后跟第108题一样的解答， 略
    // 方法2：也是找到中间节点。根据链表的特性，我们使用两个
    // 指针，一个指针一次走一步，另一个指针一次走两步，当第二个指针到尾部的时候
    // 第一个指针已经到达了中间位置；那么中间位置就是根节点位置
    // 接着继续递归的过程。
    public static TreeNode sortedListToBST(ListNode head) {
        if(head == null) return null;
        if(head.next == null) return new TreeNode(head.val);
        ListNode slow = head, fast = head;
        ListNode pre = null;
        while(fast.next != null){
            pre = slow;
            slow = slow.next;
            fast = fast.next;
            if(fast.next == null){
                break;
            }
            fast = fast.next;
        }
        // 找到中间位置，继续前半部分和后半部分
        TreeNode root = new TreeNode(slow.val);
        backtrack(head, pre, root);// 前半部分
        backtrack(slow.next, fast, root);// 后半部分
        return root;
    }

    // 递归找出中间位置
    private static void backtrack(ListNode start, ListNode tail, TreeNode root){
        // 由于是排序了的链表，因此必须保证start.val < tail.val
        if(start == null || tail == null || start.val > tail.val) return ;
        // 继续找start ~ tail中间位置
        ListNode slow = start, fast = start;
        ListNode pre = null;
        while(fast.val != tail.val){// 由于排序的，因此可以使用val判断
            pre = slow;
            slow = slow.next;
            fast = fast.next;
            if(fast.val == tail.val){
                break;
            }
            fast = fast.next;
        }
        TreeNode node = new TreeNode(slow.val);
        if(node.val > root.val){
            root.right = node;
        }else{
            root.left = node;
        }
        backtrack(start, pre, node);
        backtrack(slow.next, fast, node);
    }




    // 方法3：这个是leetcode上面的高赞的解法3，但很难理解，因为有一个全局的ptr变量
    static ListNode ptr;
    public static TreeNode sortedListToBST2(ListNode head) {
        if(head == null)return null;
        if(head.next == null)return new TreeNode(head.val);

        ptr = head;
        return buildTree(0, length(head) - 1);
    }

    static TreeNode buildTree(int left, int right){
        if(left > right)return null;
        int mid = left + (right - left + 1) / 2;
        TreeNode root = new TreeNode();
        TreeNode leftTree = buildTree(left, mid - 1);
        root.val = ptr.val;
        ptr = ptr.next;
        root.left = leftTree;
        root.right = buildTree(mid + 1, right);
        return root;
    }

    static int length(ListNode node){
        int len = 0;
        while(node != null){
            len++;
            node = node.next;
        }
        return len;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
