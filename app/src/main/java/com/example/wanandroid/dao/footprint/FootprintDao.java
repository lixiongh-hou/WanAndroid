package com.example.wanandroid.dao.footprint;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.wanandroid.ui.home.bean.DatasBean;

import java.util.List;


/**
 * @author: 雄厚
 * Date: 2020/8/13
 * Time: 9:44
 * 执行数据库操作的DAO
 */
@Dao
public interface FootprintDao {
    /**
     * 插入数据
     * @param data
     * @return
     */
    @Transaction
    @Insert
    long insertFootPrint(DatasBean data);

    /**
     * 更新某条数据
     * @param bean
     * @return
     */
    @Transaction
    @Update()
    int updateFootPrint(DatasBean bean);
    /**
     * 查询数据
     * @return
     */
    @Transaction
    @Query("SELECT * FROM datasBean")
    List<DatasBean> queryAllFootPrint();

    /**
     * 根据ID查询数据
     * @param id
     * @return
     */
    @Transaction
    @Query("SELECT * FROM datasBean WHERE id = (:id)")
    DatasBean queryArticleById(int id);

    /**
     * 删除数据
     * @param data
     * @return
     */
    @Transaction
    @Delete
    int deleteArticle(DatasBean data);

    /**
     * 删除全部
     */
    @Transaction
    @Query("DELETE FROM datasBean")
    void deleteAll();
}
