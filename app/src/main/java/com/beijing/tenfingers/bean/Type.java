package com.beijing.tenfingers.bean;

import java.util.List;

/**
 * Created by qishouqing on 2018/8/23.
 */

public class Type {


    /**
     * id : 1
     * parent_id : 0
     * name : 零售银行产品
     * child : [{"id":9,"parent_id":1,"name":"基金类","child":[{"id":29,"parent_id":9,"name":"薪金煲","child":[]}]},{"id":10,"parent_id":1,"name":"保险类","child":[]},{"id":11,"parent_id":1,"name":"贵金属类","child":[]},{"id":12,"parent_id":1,"name":"三方存管类","child":[]},{"id":13,"parent_id":1,"name":"客户推荐","child":[]},{"id":14,"parent_id":1,"name":"其他业务","child":[]}]
     */

    private int id;
    private int parent_id;
    private String name;
    private String tag;
    private List<Type> child;

    public Type() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Type> getChild() {
        return child;
    }

    public void setChild(List<Type> child) {
        this.child = child;
    }
}
