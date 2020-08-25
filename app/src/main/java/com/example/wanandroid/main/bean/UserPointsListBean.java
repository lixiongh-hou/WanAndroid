package com.example.wanandroid.main.bean;


import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/20
 * Time: 9:29
 */
public class UserPointsListBean {


    /**
     * curPage : 0
     * datas : [{"coinCount":21,"date":1597886702000,"desc":"2020-08-20 09:25:02 签到 , 积分：10 + 11","id":279637,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":20,"date":1597800234000,"desc":"2020-08-19 09:23:54 签到 , 积分：10 + 10","id":278843,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":19,"date":1597713034000,"desc":"2020-08-18 09:10:34 签到 , 积分：10 + 9","id":278042,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":18,"date":1597627372000,"desc":"2020-08-17 09:22:52 签到 , 积分：10 + 8","id":277228,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":17,"date":1597367743000,"desc":"2020-08-14 09:15:43 签到 , 积分：10 + 7","id":275808,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":16,"date":1597281506000,"desc":"2020-08-13 09:18:26 签到 , 积分：10 + 6","id":275059,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":15,"date":1597194631000,"desc":"2020-08-12 09:10:31 签到 , 积分：10 + 5","id":274274,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":14,"date":1597108997000,"desc":"2020-08-11 09:23:17 签到 , 积分：10 + 4","id":273413,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":13,"date":1597024505000,"desc":"2020-08-10 09:55:05 签到 , 积分：10 + 3","id":272585,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":12,"date":1596771587000,"desc":"2020-08-07 11:39:47 签到 , 积分：10 + 2","id":271244,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":11,"date":1596676447000,"desc":"2020-08-06 09:14:07 签到 , 积分：10 + 1","id":270247,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"},{"coinCount":10,"date":1596616703000,"desc":"2020-08-05 16:38:23 签到 , 积分：10 + 0","id":269901,"reason":"签到","type":1,"userId":72836,"userName":"18720461606"}]
     * offset : -20
     * over : false
     * pageCount : 1
     * size : 20
     * total : 12
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
         * coinCount : 21
         * date : 1597886702000
         * desc : 2020-08-20 09:25:02 签到 , 积分：10 + 11
         * id : 279637
         * reason : 签到
         * type : 1
         * userId : 72836
         * userName : 18720461606
         */

        private int coinCount;
        private long date;
        private String desc;
        private int id;
        private String reason;
        private int type;
        private int userId;
        private String userName;

        public int getCoinCount() {
            return coinCount;
        }

        public void setCoinCount(int coinCount) {
            this.coinCount = coinCount;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
