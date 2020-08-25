package com.example.wanandroid.main.bean;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/19
 * Time: 13:56
 */
public class PointsRankingBean {

    /**
     * curPage : 1
     * datas : [{"coinCount":20647,"level":207,"rank":"1","userId":20382,"username":"g**eii"},{"coinCount":17878,"level":179,"rank":"2","userId":3559,"username":"A**ilEyon"},{"coinCount":17538,"level":176,"rank":"3","userId":29303,"username":"深**士"},{"coinCount":14830,"level":149,"rank":"4","userId":2,"username":"x**oyang"},{"coinCount":11255,"level":113,"rank":"5","userId":27535,"username":"1**08491840"},{"coinCount":11103,"level":112,"rank":"6","userId":28694,"username":"c**ng0218"},{"coinCount":11058,"level":111,"rank":"7","userId":12467,"username":"c**yie"},{"coinCount":11055,"level":111,"rank":"8","userId":3753,"username":"S**phenCurry"},{"coinCount":10936,"level":110,"rank":"9","userId":29185,"username":"轻**宇"},{"coinCount":10747,"level":108,"rank":"10","userId":9621,"username":"S**24n"},{"coinCount":10729,"level":108,"rank":"11","userId":1534,"username":"j**gbin"},{"coinCount":10581,"level":106,"rank":"12","userId":12351,"username":"w**igeny"},{"coinCount":10570,"level":106,"rank":"13","userId":7891,"username":"h**zkp"},{"coinCount":10536,"level":106,"rank":"14","userId":14829,"username":"l**changwen"},{"coinCount":10515,"level":106,"rank":"15","userId":28607,"username":"S**Brother"},{"coinCount":10428,"level":105,"rank":"16","userId":27,"username":"y**ochoo"},{"coinCount":10420,"level":105,"rank":"17","userId":12331,"username":"R**kieJay"},{"coinCount":10386,"level":104,"rank":"18","userId":26707,"username":"p**xc.com"},{"coinCount":10329,"level":104,"rank":"19","userId":833,"username":"w**lwaywang6"},{"coinCount":10281,"level":103,"rank":"20","userId":7809,"username":"1**23822235"},{"coinCount":10266,"level":103,"rank":"21","userId":7710,"username":"i**Cola7"},{"coinCount":10155,"level":102,"rank":"22","userId":29076,"username":"f**ham"},{"coinCount":9990,"level":100,"rank":"23","userId":4886,"username":"z**iyun"},{"coinCount":9970,"level":100,"rank":"24","userId":7590,"username":"陈**啦啦啦"},{"coinCount":9949,"level":100,"rank":"25","userId":2068,"username":"i**Cola"},{"coinCount":9638,"level":97,"rank":"26","userId":2160,"username":"R**iner"},{"coinCount":9638,"level":97,"rank":"27","userId":25128,"username":"f**wandroid"},{"coinCount":9625,"level":97,"rank":"28","userId":25419,"username":"蔡**打篮球"},{"coinCount":9413,"level":95,"rank":"29","userId":3825,"username":"请**娃哈哈"},{"coinCount":9251,"level":93,"rank":"30","userId":30114,"username":"E**an_Jin"}]
     * offset : 0
     * over : false
     * pageCount : 1605
     * size : 30
     * total : 48127
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

    public static class DatasBean {
        /**
         * coinCount : 20647
         * level : 207
         * rank : 1
         * userId : 20382
         * username : g**eii
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
}
