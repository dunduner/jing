package com.jing.study.my_hashmap;

import java.util.Map;

/**
 * @author zhangning
 * @date 2020/11/2
 */
public class MyHashMap<K, V> implements MyMap<K, V> {
    //1.定义一个容器用来存放元素， 但不立即初始化，使用懒加载方式
    Node<K, V>[] table = null;

    //2.定义容器的默认大小
    static int DEFAULT_INITIAL_CAPACITY = 16;
    //负载因子 泊松分布
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    //4.记录当前容器实际大小
    static int size = 0;


    @Override
    public V put(K key, V value) {
        //懒加载，判断数组是否为空
        if (table == null) {
            table = new Node[DEFAULT_INITIAL_CAPACITY];
        }

        //如果数组长度大于  就resize()
        if (size > DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR) {
            resize();
        }
        //获取index
        int index = getIndex(key, DEFAULT_INITIAL_CAPACITY);
        System.out.println("获取的index：" + index);
        //根据角标  找到对应的node
        Node<K, V> node = table[index];
        if (node == null) {
            //说明此数组位置没有过数据
            Node<K, V> kvNode = new Node<>(key, value, null);
            table[index] = kvNode;
            size++;
            return table[index].getValue();
        } else {
            //有数据
            Node<K, V> newNode = node;
            while (newNode != null) {
                if (key.equals(newNode.getKey()) || key == newNode.getKey()) {
                    newNode.setValue(value);
//                    TODO
//                    size++;//感觉没有必要加
                    return newNode.getValue();
                }
                newNode = newNode.getNextNode();
            }
            //没有重复的key
            table[index] = new Node<K, V>(key, value, table[index]);
            size++;
            return table[index].getValue();
        }
    }


    //扩充操作
    private void resize() {
        System.out.println("进入 resize逻辑-------------------------------");
        //1.创建新的table长度扩展为以前的两倍
        int newLength = DEFAULT_INITIAL_CAPACITY * 2;
        Node<K, V>[] newtable = new Node[newLength];
        //2.将以前table中的取出，并重新计算index存入
        for (int i = 0; i < table.length; i++) {
            Node<K, V> oldtable = table[i];
            while (oldtable != null) {
                //将table[i]的位置赋值为空,
                table[i] = null;

                //方法1：重新计算index，然后按照put时候的方法进行放值，此种方法会不停的new对象会造成效率比较低
                K key = oldtable.getKey();
                int index = getIndex(key, newLength);
                newtable[index] = new Node<K, V>(key, oldtable.getValue(), newtable[index]);
                oldtable = oldtable.getNextNode();

                //方法2：
                //计算新的index值
//                K key = oldtable.getKey();
//                int index = getIndex(key, newLength);
//
//                //将以前的nextnode保存下来
//                Node<K, V> nextNode = oldtable.getNextNode();
//
//                //将newtable的值赋值在oldtable的nextnode上，如果以前是空，则nextnode也是空
//                oldtable.setNextNode(newtable[index]);
//                newtable[i] = oldtable;
//
//                //将以前的nextcode赋值给oldtable以便继续遍历
//                oldtable = nextNode;
            }

        }
        //3.将新的table赋值回老的table
        table = newtable;
        DEFAULT_INITIAL_CAPACITY = newLength;
        newtable = null; //释放内存
    }

    /**
     * 获取index
     *
     * @param key
     * @param length
     * @return
     */
    public int getIndex(K key, int length) {
        int hashCode = key.hashCode();
//        System.out.println(key+"的hashCode："+hashCode);
        int index = hashCode % length;
        return index;
    }

    @Override
    public V get(K k) {
        int index = getIndex(k, DEFAULT_INITIAL_CAPACITY);
        Node<K, V> node = table[index];
        if (k.equals(node.getKey()) || k == node.getKey()) {
            return node.getValue();
        } else {
            Node<K, V> nextNode = node.getNextNode();
            while (nextNode != null) {
                if (k.equals(nextNode.getKey()) || k == nextNode.getKey()) {
                    return nextNode.getValue();
                }
            }
        }

        return null;
    }

    @Override
    public void delete(K key) {

    }

    @Override
    public int size() {
        return size;
    }


    // 测试方法.打印所有的链表元素
    public void print() {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            System.out.print("下标位置[" + i + "]");
            while (node != null) {
                System.out.print("-----[ key:" + node.getKey() + ",value:" + node.getValue() + "]-----");
                node = node.getNextNode();
            }
            System.out.println();
        }

    }


    public static void main(String[] args) {
        MyHashMap<String, String> extHashMap = new MyHashMap<String, String>();
        extHashMap.put("1号", "1号");// 0
        extHashMap.put("2号", "1号");// 1
        extHashMap.put("3号", "1号");// 2
        extHashMap.put("4号", "1号");// 3
        extHashMap.put("6号", "1号");// 4
        extHashMap.put("7号", "1号");
        extHashMap.put("14号", "1号");

        extHashMap.put("22号", "1号");
        extHashMap.put("26号", "1号");
        extHashMap.put("27号", "1号");
        extHashMap.put("28号", "1号");
        extHashMap.put("66号", "66");
        extHashMap.put("30号", "1号");
        System.out.println("扩容前数据....");
        extHashMap.print();
        System.out.println("扩容后数据....");
        extHashMap.put("31号", "1号");
//        extHashMap.put("66号", "123466666");
        extHashMap.print();
        // 修改3号之后
//        System.out.println(extHashMap.get("66号"));

    }
}

@SuppressWarnings("hiding")
class Node<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;
    private Node<K, V> nextNode; //下一节点

    public Node(K key, V value, Node<K, V> nextNode) {
        super();
        this.key = key;
        this.value = value;
        this.nextNode = nextNode;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }

    public Node<K, V> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<K, V> nextNode) {
        this.nextNode = nextNode;
    }

    public void setKey(K key) {
        this.key = key;
    }

    //判断是否还有下一个节点
        /*private boolean hasNext() {
            return true;
        }*/

}
