package com.example.wanandroid.dao.search;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.wanandroid.main.bean.SearchHistoryBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/18
 * Time: 11:36
 */
@Dao
public interface SearchDao {

    /**
     * 插入一条数据
     * @param data
     * @return
     */
    @Transaction
    @Insert
    long insertSearchHistory(SearchHistoryBean data);

    /**
     * 查询所有数据
     * @return
     */
    @Transaction
    @Query("SELECT * FROM searchhistorybean")
    List<SearchHistoryBean> queryAllSearchHistory();

    /**
     * 根据名称查询某条数据
     * @param name
     * @return
     */
    @Transaction
    @Query("SELECT * FROM searchhistorybean WHERE name = (:name)")
    SearchHistoryBean querySearchHistoryByName(String name);

    /**
     * 删除某条数据
     * @param data
     * @return
     */
    @Transaction
    @Delete
    int deleteSearchHistory(SearchHistoryBean data);

    /**
     * 删除全部
     */
    @Transaction
    @Query("DELETE FROM searchhistorybean")
    void deleteAll();
}
