package com.beijing.tenfingers.bean;

import java.util.ArrayList;

/**
 * 问题单例
 */
public class QuestionSingleInstance {

        private ArrayList<QuestionList> lists=new ArrayList<>();
        public ArrayList<QuestionList> getInstance(ArrayList<QuestionList> lists){
            if (lists.size()==0) {
                synchronized (QuestionList.class) {
                    if (lists.size()==0) {
                        this.lists.addAll(lists);
                    }
                }
            }
            return lists;
        }

    public ArrayList<QuestionList> getLists() {
        return lists;
    }

    public void setLists(ArrayList<QuestionList> lists) {
        this.lists = lists;
    }
}
