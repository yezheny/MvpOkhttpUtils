package com.pcjz.http.okhttp.server.task;

/**
 * 具有优先级对象的公共类
 */
public class PriorityObject<E> {

    public final Priority priority;
    public final E obj;

    public PriorityObject(Priority priority, E obj) {
        this.priority = priority == null ? Priority.DEFAULT : priority;
        this.obj = obj;
    }
}
