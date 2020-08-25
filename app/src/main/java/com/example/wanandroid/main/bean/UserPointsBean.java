package com.example.wanandroid.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: 雄厚
 * Date: 2020/8/19
 * Time: 15:12
 */
public class UserPointsBean implements Parcelable {


    /**
     * coinCount : 165
     * level : 2
     * rank : 3568
     * userId : 72836
     * username : 1**20461606
     */

    private int coinCount;
    private int level;
    private String rank;
    private int userId;
    private String username;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.coinCount);
        dest.writeInt(this.level);
        dest.writeString(this.rank);
        dest.writeInt(this.userId);
        dest.writeString(this.username);
    }

    public UserPointsBean() {
    }

    protected UserPointsBean(Parcel in) {
        this.coinCount = in.readInt();
        this.level = in.readInt();
        this.rank = in.readString();
        this.userId = in.readInt();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<UserPointsBean> CREATOR = new Parcelable.Creator<UserPointsBean>() {
        @Override
        public UserPointsBean createFromParcel(Parcel source) {
            return new UserPointsBean(source);
        }

        @Override
        public UserPointsBean[] newArray(int size) {
            return new UserPointsBean[size];
        }
    };
}
