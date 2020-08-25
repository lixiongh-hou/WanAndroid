package com.example.wanandroid.dao.search;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wanandroid.main.bean.SearchHistoryBean;

/**
 * @author: 雄厚
 * Date: 2020/8/18
 * Time: 11:45
 */
@Database(entities = { SearchHistoryBean.class }, version = 1,exportSchema = false)
public abstract class SearchDataBase extends RoomDatabase {
    /**
     * 抽象一个DAO
     * @return
     */
    public abstract SearchDao getSearchDao();
    /**数据库名称*/
    private static final String DB_NAME = "SearchDaoDao.db";
    private static volatile SearchDataBase instance;
    static synchronized SearchDataBase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static SearchDataBase create(Context context){
        return Room.databaseBuilder(
                context,
                SearchDataBase.class,
                DB_NAME
        ).build();
    }
}
