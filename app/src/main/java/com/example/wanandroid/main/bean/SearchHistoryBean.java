package com.example.wanandroid.main.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: 雄厚
 * Date: 2020/8/18
 * Time: 11:36
 */
@Entity
public class SearchHistoryBean {

    @PrimaryKey(autoGenerate = true)
    private int primaryKeyId = 0;
    private String name;

    public int getPrimaryKeyId() {
        return primaryKeyId;
    }

    public void setPrimaryKeyId(int primaryKeyId) {
        this.primaryKeyId = primaryKeyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
