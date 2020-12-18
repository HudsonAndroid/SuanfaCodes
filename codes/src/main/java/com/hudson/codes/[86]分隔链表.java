package com.hudson.codes;//给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
//
// 你应当保留两个分区中每个节点的初始相对位置。 
//
// 
//
// 示例: 
//
// 输入: head = 1->4->3->2->5->2, x = 3
//输出: 1->2->2->4->3->5
// 
// Related Topics 链表 双指针 
// 👍 286 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution86 {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // 时间18.34%，空间：80.58%
    // 1->4->3->2->5->2   x=3
    // smaller: 1（没有pre）  larger:
    // smaller: 1           larger: 4
    // smaller: 1           larger: 4->3
    // smaller: 1 发现2      larger:
    // 拼接  1->2->4->3-->5->2
    // smaller:             larger: 5
    // smaller:发现2
    // 拼接  1->2->2->4->3->5
    public static ListNode partition(ListNode head, int x) {
        if(head == null) return null;
        ListNode finalHead = null;
        ListNode smallPreNode = null;
        ListNode largeStart = null;
        ListNode largeEnd = null;
        ListNode node = head;
        while(node != null){
            if(node.val < x){
                // 可能前面找出了比x小的node
                if(smallPreNode != null){
                    smallPreNode.next = node;
                }
                // 更新smallPreNode
                smallPreNode = node;
                // 如果之前已经找出了大于等于x的数
                if(largeStart != null && largeEnd != null){
                    //【错误】，没有先保存，这跟交换数组数字是类似的啊
                    ListNode unCheckNode = node.next;
                    node.next = largeStart;
                    largeEnd.next = unCheckNode;
                }
                if(finalHead == null){// 只有小于x的数可以设置为head
                    finalHead = node;
                }
            }else{
                if(largeStart == null){
                    largeStart = node;
                }
                largeEnd = node;
            }
            node = node.next;
        }
        // 【错误1】有可能外界只输入的节点都大于x，这样finalHead就是null了
        if(finalHead == null && largeStart != null){
            return largeStart;
        }
        return finalHead;
    }


    // 还有一个思路，就是使用两个指针，一个存储小的，一个存储大的 todo
}
//leetcode submit region end(Prohibit modification and deletion)
