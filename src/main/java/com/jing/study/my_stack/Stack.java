package com.jing.study.my_stack;

public interface Stack<E> {


    /**
     * 入栈
     *
     * @param e 入栈元素
     * @return 入栈元素
     */
    E push(E e);

    /**
     * 将栈顶元素弹出
     *
     * @return 栈顶元素
     */
    E pop();

    /**
     * 查看栈顶元素，该方法不会弹出栈顶元素
     *
     * @return 栈顶元素
     */
    E peek();

    /**
     * 查看栈深度
     *
     * @return 栈深度
     */
    int size();

    void print();
}
