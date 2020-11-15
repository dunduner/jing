package com.jing.study.my_stack;

/**
 * @author zhangning
 * @date 2020/11/2
 */
public class List_Stack<E> implements Stack<E> {
    Object[] data;
    int index = -1;
    int deep;//栈的深度
    int DEFAULT_CAPACITY = 1 << 4;

    public List_Stack(int capacity) {
        if (capacity <= 0) {
            capacity = DEFAULT_CAPACITY;
        }
        deep = capacity;
        data = new Object[deep];
    }
    public List_Stack() {
        this.deep = DEFAULT_CAPACITY;
        this.data = new Object[deep];
    }

    //add
    @Override
    public E push(E e) {
        //栈满了 就不能放了
        if (isFull()) {
            return null;
        }
        //可以放
        int newIndex = index+1;
        data[newIndex] = e;
        index++;
        return e;
    }

    private boolean isFull() {

        if (deep == index + 1) {
            return true;
        } else {
            return false;
        }

    }

    //弹出  并且删除此元素
    @Override
    public E pop() {
        if (index <= -1) {
            System.out.println("栈为空,没得弹了！");
            return null;
        }
        Object o = data[index];
        data[index] = null;
        index--;

        return (E) o;
    }


    @Override
    public E peek() {
        if (index <= -1) {
            System.out.println("栈为空,没得弹了！");
            return null;
        }
        Object o = data[index];
        return (E) o;
    }

    @Override
    public int size() {
        return index+1;
    }

    @Override
    public void print() {
        for (int i = 0; i < data.length; i++) {
            if(data[i]!=null){
                System.out.println(data[i]);
            }
        }

    }


    public static void main(String[] args) {
        List_Stack<String> stringList_stack = new List_Stack<>();

        stringList_stack.push("a1");
        stringList_stack.push("a2");
        stringList_stack.push("a3");
        stringList_stack.push("a4");
        stringList_stack.push("a5");
        stringList_stack.push("a6");

//        stringList_stack.print();
        String pop = stringList_stack.pop();
        System.out.println("弹出元素："+pop);

        System.out.println("弹出后打印");
        stringList_stack.print();
        System.out.println("查看栈顶");
        System.out.println(stringList_stack.peek());

    }
}
