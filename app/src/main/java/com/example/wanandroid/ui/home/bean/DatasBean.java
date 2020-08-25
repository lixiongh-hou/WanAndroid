package com.example.wanandroid.ui.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: 雄厚
 * Date: 2020/8/13
 * Time: 11:18
 */
@Entity
public class DatasBean implements Parcelable {
    /**
     * apkLink :
     * audit : 1
     * author : 美团技术团队
     * canEdit : false
     * chapterId : 417
     * chapterName : 美团技术团队
     * collect : false
     * courseId : 13
     * desc :
     * descMd :
     * envelopePic :
     * fresh : false
     * id : 14664
     * link : https://mp.weixin.qq.com/s/Pu9rfxOgx0tRnopQcZdsvA
     * niceDate : 1天前
     * niceShareDate : 16小时前
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1596643200000
     * realSuperChapterId : 407
     * selfVisible : 0
     * shareDate : 1596725812000
     * shareUser :
     * superChapterId : 408
     * superChapterName : 公众号
     * tags : [{"name":"公众号","url":"/wxarticle/list/417/1"}]
     * title : 干货提炼 | SIGIR 2020信息检索国际顶会美团专场 （附视频链接）
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */

    /**主键是否自动增长，默认为false*/
    @PrimaryKey(autoGenerate = true)
    private int primaryKeyId = 0;
    private String apkLink;
    private int audit;
    private String author;
    private boolean canEdit;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String descMd;
    private String envelopePic;
    private boolean fresh;
    private int id;
    private String link;
    private String niceDate;
    private String niceShareDate;
    private String origin;
    private String prefix;
    private String projectLink;
    private long publishTime;
    private int realSuperChapterId;
    private int selfVisible;
    private long shareDate;
    private String shareUser;
    private int superChapterId;
    private String superChapterName;
    private String title;
    private int type;
    private int userId;
    private int visible;
    private int zan;
    private int originId;
    private boolean top = false;
    private boolean isShow = false;

    public int getPrimaryKeyId() {
        return primaryKeyId;
    }

    public void setPrimaryKeyId(int primaryKeyId) {
        this.primaryKeyId = primaryKeyId;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }
    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescMd() {
        return descMd;
    }

    public void setDescMd(String descMd) {
        this.descMd = descMd;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getRealSuperChapterId() {
        return realSuperChapterId;
    }

    public void setRealSuperChapterId(int realSuperChapterId) {
        this.realSuperChapterId = realSuperChapterId;
    }

    public int getSelfVisible() {
        return selfVisible;
    }

    public void setSelfVisible(int selfVisible) {
        this.selfVisible = selfVisible;
    }

    public long getShareDate() {
        return shareDate;
    }

    public void setShareDate(long shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.primaryKeyId);
        dest.writeString(this.apkLink);
        dest.writeInt(this.audit);
        dest.writeString(this.author);
        dest.writeByte(this.canEdit ? (byte) 1 : (byte) 0);
        dest.writeInt(this.chapterId);
        dest.writeString(this.chapterName);
        dest.writeByte(this.collect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.courseId);
        dest.writeString(this.desc);
        dest.writeString(this.descMd);
        dest.writeString(this.envelopePic);
        dest.writeByte(this.fresh ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeString(this.link);
        dest.writeString(this.niceDate);
        dest.writeString(this.niceShareDate);
        dest.writeString(this.origin);
        dest.writeString(this.prefix);
        dest.writeString(this.projectLink);
        dest.writeLong(this.publishTime);
        dest.writeInt(this.realSuperChapterId);
        dest.writeInt(this.selfVisible);
        dest.writeLong(this.shareDate);
        dest.writeString(this.shareUser);
        dest.writeInt(this.superChapterId);
        dest.writeString(this.superChapterName);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeInt(this.userId);
        dest.writeInt(this.visible);
        dest.writeInt(this.zan);
        dest.writeInt(this.originId);
        dest.writeByte(this.top ? (byte) 1 : (byte) 0);
    }

    public DatasBean() {
    }

    protected DatasBean(Parcel in) {
        this.primaryKeyId = in.readInt();
        this.apkLink = in.readString();
        this.audit = in.readInt();
        this.author = in.readString();
        this.canEdit = in.readByte() != 0;
        this.chapterId = in.readInt();
        this.chapterName = in.readString();
        this.collect = in.readByte() != 0;
        this.courseId = in.readInt();
        this.desc = in.readString();
        this.descMd = in.readString();
        this.envelopePic = in.readString();
        this.fresh = in.readByte() != 0;
        this.id = in.readInt();
        this.link = in.readString();
        this.niceDate = in.readString();
        this.niceShareDate = in.readString();
        this.origin = in.readString();
        this.prefix = in.readString();
        this.projectLink = in.readString();
        this.publishTime = in.readLong();
        this.realSuperChapterId = in.readInt();
        this.selfVisible = in.readInt();
        this.shareDate = in.readLong();
        this.shareUser = in.readString();
        this.superChapterId = in.readInt();
        this.superChapterName = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
        this.userId = in.readInt();
        this.visible = in.readInt();
        this.zan = in.readInt();
        this.originId = in.readInt();
        this.top = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DatasBean> CREATOR = new Parcelable.Creator<DatasBean>() {
        @Override
        public DatasBean createFromParcel(Parcel source) {
            return new DatasBean(source);
        }

        @Override
        public DatasBean[] newArray(int size) {
            return new DatasBean[size];
        }
    };
}
