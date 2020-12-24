package com.hudson.codes;//给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。
//
// 示例 1: 
//
// 输入: 1->2->3->3->4->4->5
//输出: 1->2->5
// 
//
// 示例 2: 
//
// 输入: 1->1->1->2->3
//输出: 2->3 
// Related Topics 链表 
// 👍 413 👎 0

//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution82 {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // 时间 76.43%，空间：69.65%
    // 本题与第83题类似，但是这里算法不同
    // 分析 1 1 2 2 3 4 4 5
    // 我们结果得到的是3 5
    // 需要注意特殊情况：1）所有都是重复的 2）只有最后一个不是重复的
    public static ListNode deleteDuplicates(ListNode head) {
        if(head == null) return null;
        ListNode newHead = null;
        ListNode combineNode = null;
        ListNode preNode = head;
        ListNode node = head.next;
        while(node != null){
            // 我们确保，进入第一个循环的内部时，preNode与node不同，否则进入第二个循环过滤
            // 我们先判断preNode是否和node相同，如果相同，那么可能后面还有相同的元素
            // 如果不同，那么preNode可以作为一个不重复元素保留，node需要后面进一步判断是否后面还有与node相同的元素
            // 因此，上面操作其实可以合并，后一种情况下，preNode = node, node = node.next即可
            while(node != null && preNode.val != node.val){
                // preNode可以作为不重复元素保留
                if(newHead == null){
                    newHead = preNode;
                }else{
                    combineNode.next = preNode;
                }
                combineNode = preNode;
                // 同时，我们需要继续判断node与后面的元素是否相同
                preNode = node;
                node = node.next;
            }
            if(node == null) break;// 这个不能删除，错误示例 1 1 1 2 3
            // 到这里，preNode的值必然与node值相同，那么我们需要排除这部分内容
            while(node != null && preNode.val == node.val){
                node = node.next; // preNode不需要增加，因为我们
            }
//            if(node == null) break;
            // 如果走到了这里，说明preNode和node不同，由于preNode与之前部分有一些相同的内容，因此preNode不是不重复元素
            // 因此继续判断以node为不同元素的情况是否重复
            preNode = node;
            if(node != null){
                node = node.next;
            }
        }
        // 有可能最后一个节点是不重复的，因此我们需要拼接上去
        if(preNode != null /*&& node == null*/){
            if(combineNode != null){
                combineNode.next = preNode;
                combineNode = preNode;
            }else{
                // 说明前面没有任何节点，那么直接返回该节点
                return preNode;
            }
        }
        // 【错误1】易错点，最后需要断开尾节点
        if(combineNode != null){
            combineNode.next = null;
        }
        return newHead;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
