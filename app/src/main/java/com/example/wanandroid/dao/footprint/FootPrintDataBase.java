package com.example.wanandroid.dao.footprint;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wanandroid.ui.home.bean.DatasBean;

/**
 * @author: 雄厚
 * Date: 2020/8/13
 * Time: 10:02
 */
@Database(entities = { DatasBean.class }, version = 1,exportSchema = false)
public abstract class FootPrintDataBase extends RoomDatabase {

    /**
     * 抽象一个DAO
     * @return
     */
    public abstract FootprintDao getFootPrintDao();
    /**数据库名称*/
    private static final String DB_NAME = "FootPrintDao.db";
    private static volatile FootPrintDataBase instance;
    static synchronized FootPrintDataBase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static FootPrintDataBase create(Context context){
        return Room.databaseBuilder(
                context,
                FootPrintDataBase.class,
                DB_NAME
        ).build();
    }
}
