package com.example.wanandroid.main.bean;

import com.example.wanandroid.ui.home.bean.DatasBean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/24
 * Time: 10:41
 */
public class MyShareBean {


    /**
     * coinInfo : {"coinCount":301,"level":4,"rank":"2431","userId":72836,"username":"1**20461606"}
     * shareArticles : {"curPage":1,"datas":[{"apkLink":"","audit":0,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":14946,"link":"http://www.jiarui.com","niceDate":"刚刚","niceShareDate":"刚刚","origin":"","prefix":"","projectLink":"","publishTime":1598237966000,"realSuperChapterId":493,"selfVisible":0,"shareDate":1598237966000,"shareUser":"18720461606","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"测试","type":0,"userId":72836,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":1}
     */

    private CoinInfoBean coinInfo;
    private ShareArticlesBean shareArticles;

    public CoinInfoBean getCoinInfo() {
        return coinInfo;
    }

    public void setCoinInfo(CoinInfoBean coinInfo) {
        this.coinInfo = coinInfo;
    }

    public ShareArticlesBean getShareArticles() {
        return shareArticles;
    }

    public void setShareArticles(ShareArticlesBean shareArticles) {
        this.shareArticles = shareArticles;
    }

    public static class CoinInfoBean {
        /**
         * coinCount : 301
         * level : 4
         * rank : 2431
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
    }

    public static class ShareArticlesBean {
        /**
         * curPage : 1
         * datas : [{"apkLink":"","audit":0,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":14946,"link":"http://www.jiarui.com","niceDate":"刚刚","niceShareDate":"刚刚","origin":"","prefix":"","projectLink":"","publishTime":1598237966000,"realSuperChapterId":493,"selfVisible":0,"shareDate":1598237966000,"shareUser":"18720461606","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"测试","type":0,"userId":72836,"visible":0,"zan":0}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 1
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

    }
}
