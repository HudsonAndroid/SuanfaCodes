package com.hudson.codes;//运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制 。
//
// 
// 
// 实现 LRUCache 类： 
//
// 
// LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存 
// int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。 
// void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上
//限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。 
// 
//
// 
// 
// 
//
// 进阶：你是否可以在 O(1) 时间复杂度内完成这两种操作？ 
//
// 
//
// 示例： 
//
// 
//输入
//["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
//[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
//输出
//[null, null, null, 1, null, -1, null, -1, 3, 4]
//
//解释
//LRUCache lRUCache = new LRUCache(2);
//lRUCache.put(1, 1); // 缓存是 {1=1}
//lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
//lRUCache.get(1);    // 返回 1
//lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
//lRUCache.get(2);    // 返回 -1 (未找到)
//lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
//lRUCache.get(1);    // 返回 -1 (未找到)
//lRUCache.get(3);    // 返回 3
//lRUCache.get(4);    // 返回 4
// 
//
// 
//
// 提示： 
//
// 
// 1 <= capacity <= 3000 
// 0 <= key <= 3000 
// 0 <= value <= 104 
// 最多调用 3 * 104 次 get 和 put 
// 
// Related Topics 设计 
// 👍 1078 👎 0


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
//class LRUCache {
//
//    public LRUCache(int capacity) {
//
//    }
//
//    public int get(int key) {
//
//    }
//
//    public void put(int key, int value) {
//
//    }
//}


// LRU cache算法
// 最简单的办法就是继承自LinkedHashMap
// 直接利用LinkedHashMap的特性
class LRUCache1 extends LinkedHashMap<Integer,Integer> {
    private int maxSize;

    public LRUCache1(int capacity) {
        // 注意：传给LinkedHashMap的第一个参数是初始的容器存储大小，不是LinkedHashMap的访问顺序时管理的大小
        super(capacity, 0.75f, true);
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);// 我们设置了访问顺序后，LinkedHashMap会帮我们完成
    }

    // 注意：LinkedHashMap默认是不会移除最老元素的，需要我们自己处理
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        // 返回是否超标
        return size() > maxSize;
    }
}


//public static void main(String[] args){
//    LRUCache2<Integer, Integer> cache = new LRUCache2<>(2);
//    cache.put(1,1);
//    cache.put(2,2);
//    Integer value = cache.get(1);
//    System.out.println("第一个位置元素时"+value);
//    print(cache);
//    cache.put(3,3);
//    print(cache);
//    Integer result = cache.get(2);
//    System.out.println(" "+result);
//}

//private static void print(LRUCache2<Integer, Integer> cache){
//    LRUCache2.Node head = cache.head;
//    // 从尾部开始
//    LRUCache2.Node node = cache.head.before;
//    while(node != head){
//        System.out.print(" "+node.value);
//        node = node.before;
//    }
//    System.out.println();
//}

class LRUCache extends LRUCache2<Integer,Integer> {

    public LRUCache(int capacity) {
        super(capacity);

    }

    public int get(int key) {
        Integer result = super.get(key);
        return result == null ? -1 : result;
    }

    public void put(int key, int value) {
        super.put(key, value);
    }
}

// 法2 自己实现双向链表
// 65.34%  59.31%
// 当然，面试时不能使用上面的方法
// 因此我们需要手写功能。
// 实际上，我们整个LRU算法实际上是为了管理缓存用的，因此才需要
// 是一个HashMap结构，然后因为要访问顺序，所以增加了双向链表的能力
// 而LinkedList(是队列的一种)其实就是一种双向链表，但我们这里先不使用这种方式实现
// 手写一个双向链表
class LRUCache2<Key, Value> {

    class Node {
        Key key; // 注意，key仅在检查是否超标时从map中移除元素有用
        Value value;
        Node before;
        Node after;

        // 【错误1】key是需要保存起来的，以便后面删除双向链表的最老节点时可以用于同步删除map中对应元素
        public Node(Key key, Value value){
            this.key = key;
            this.value = value;
        }
    }

    /*private*/ Node head;// 头部节点没有实际存储值，仅是辅助作用
    private int capacity;// 存储最大容量

    // 缓存的实体，双向链表只是辅助作用
    // 如果我们不是继承自HashMap的时候，我们应该存储的value类型应该是Node类型，
    // 因为这样我们才能通过Map定位到是双向链表中的哪一个节点
//        private Map<Key, Value> map = new HashMap<>();
    private Map<Key, Node> map = new HashMap<>();

    public LRUCache2(int capacity) {
        this.capacity = capacity;
        head = new Node(null, null);// head 存储的值无实际意义
        // 初始化双向链表，最新元素在尾部，最旧元素在head的next
        head.after = head;
        head.before = head;
    }

    public Value get(Key key) {
        // 从Map中获取该节点
        Node node = map.get(key);
        if(node == null){
            return null;
        }
        // 有这个节点，那么先把访问位置放到最新的位置上去
        removeNode(node);
        // 再添加到尾部去
        addNewNode(node);
        return node.value;
    }

    public void put(Key key, Value value) {
        // 首先判断是否已经存在该节点
        Node node = map.get(key);
        if(node == null){
            // 直接添加到尾部
            Node newNode = new Node(key, value);
            addNewNode(newNode);
            // 同时需要更新到map中
            map.put(key, newNode);
            // 添加后判断是否超标
            checkAndRemove();
        }else{
            // 已经存在该节点，更新值
            node.value = value;
            // 移除该元素，并添加到尾部
            removeNode(node);
            addNewNode(node);
        }
    }

    private void checkAndRemove(){
        int size = map.size();
        if(size > capacity){
            // 超标了，需要删除最老元素
            Node oldestNode = head.after;
            // 是最老元素的下一个拼接到头部上
            Node oldestNext = oldestNode.after;
            head.after = oldestNext;
            oldestNext.before = head;
            // 同时我们从Map中移除最老元素
            map.remove(oldestNode.key);
        }
    }

    // 添加新元素到最尾部
    private void addNewNode(Node node){
        // head.before是最后一个节点，那么新加入的节点应该放到这个的后面
        Node oldLast = head.before;
        oldLast.after = node;
        node.before = oldLast;
        head.before = node;// 最后一个节点替换成新的节点
        node.after = head;// 最后一个节点的after是头节点
    }

    // 移除双向链表的某一个节点
    private void removeNode(Node node){
        // 列草稿，可以看出来
        node.after.before = node.before;
        node.before.after = node.after;
    }
}


//// 法3 使用LinkedList这个双向链表（队列）
//// 10%  97.41%
//// 我们使用LinkedList + HashMap来完成
//class LRUCache3<Key, Value> {
//
//    class Node {
//        Key key; // 注意，key仅在检查是否超标时从map中移除元素有用
//        Value value;
//
//        // 【错误1】key是需要保存起来的，以便后面删除双向链表的最老节点时可以用于同步删除map中对应元素
//        public Node(Key key, Value value){
//            this.key = key;
//            this.value = value;
//        }
//    }
//
//    private int capacity;// 存储最大容量
//
//    // 缓存的实体，双向链表只是辅助作用
//    // 如果我们不是继承自HashMap的时候，我们应该存储的value类型应该是Node类型，
//    // 因为这样我们才能通过Map定位到是双向链表中的哪一个节点
////        private Map<Key, Value> map = new HashMap<>();
//    private Map<Key, LRUCache3.Node> map = new HashMap<>();
//    private LinkedList<Node> list = new LinkedList<>();
//
//    public LRUCache3(int capacity) {
//        this.capacity = capacity;
//    }
//
//    public Value get(Key key) {
//        LRUCache3.Node node = map.get(key);
//        if(node == null) return null;
//        // 存在该元素，那么把该元素从LinkedList中移除
//        list.remove(node);
//        list.addLast(node);// 添加到最后
//        return node.value; // 不晓得这里为什么报错
//    }
//
//    public void put(Key key, Value value) {
//        LRUCache3.Node node = map.get(key);
//        if(node == null){
//            // 添加新元素
//            node = new LRUCache3.Node(key, value);
//            // 添加到最后面
//            list.addLast(node);
//            map.put(key, node);
//            // 检查是否超标
//            checkAndRemove();
//        }else{
//            // 当前节点已经在缓存中，更新
//            node.value = value;
//            // 把当前位置移除并添加到最后
//            list.remove(node);
//            list.addLast(node);
//        }
//    }
//
//    private void checkAndRemove(){
//        if(list.size() > capacity){
//            // 移除最旧元素
//            LRUCache3.Node node = list.removeFirst();
//            // map同步移除
//            map.remove(node.key);
//        }
//    }
//}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
//leetcode submit region end(Prohibit modification and deletion)
