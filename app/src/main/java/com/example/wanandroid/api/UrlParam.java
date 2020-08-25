package com.example.wanandroid.api;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 15:38
 * 接口地址
 */
public interface UrlParam {

    interface Base {

        /**
         * 基础地址
         */
        String HOST = "https://www.wanandroid.com/";
    }

    interface Banner{

        String URL = "banner/json";
    }

    /**
     * 首页文章
     */
    interface Article{
        String URL = "article/list/{page}/json";
    }

    /**
     * 首页文章置顶
     */
    interface ArticleTop{
        String URL = "article/top/json";
    }

    /**
     * 登录
     */
    interface LogIn{
        String URL = "user/login";
    }
    /**
     * 收藏文章
     */
    interface Collect{
        String URL = "lg/collect/{id}/json";
    }
    /**
     * 取消收藏文章
     */
    interface UnCollect{
        String URL = "lg/uncollect_originId/{id}/json";
    }

    /**
     * 取消我的收藏文章
     */
    interface UnMyCollect{
        String URL = "lg/uncollect/{id}/json";
    }

    /**
     * 我的收藏
     */
    interface MyCollect{
        String URL = "lg/collect/list/{page}/json";
    }
    /**
     * 添加文章
     */
    interface AddCollection{
        String URL = "lg/collect/add/json";
    }

    /**
     * 体系数据
     */
    interface System{
        String URL = "tree/json";
    }

    /**
     * 体系下的文章
     */
    interface SystemDetailed{
        String URL = "article/list/{page}/json";
    }

    /**
     * 公众号名称
     */
    interface WeChatName{
        String URL = "wxarticle/chapters/json";
    }
    /**
     * 公众号名称对应数据
     */
    interface WeChatList{
        String URL = "wxarticle/list/{id}/{page}/json";
    }

    /**
     * 项目分类
     */
    interface ProjectTree{
        String URL = "project/tree/json";
    }

    /**
     * 项目列表数据
     */
    interface ProjectList{
        String URL = "project/list/{page}/json";
    }

    /**
     * 搜索热词
     */
    interface SearchHotKey{
        String URL = "hotkey/json";
    }

    /**
     * 搜索关键词
     */
    interface Search{
        String URL = "article/query/{page}/json";
    }

    /**
     * 获取积分排行榜
     */
    interface PointsRanking{
        String URL = "coin/rank/{page}/json";
    }

    /**
     * 用户积分
     */
    interface UserPoints{
        String URL = "lg/coin/userinfo/json";
    }

    /**
     * 用户获取积分列表
     */
    interface UserPointsList{
        String URL = "lg/coin/list/{page}/json";
    }

    /**
     * 广场
     */
    interface Square{
        String URL = "user_article/list/{page}/json";
    }

    /**
     * 分享文章
     */
    interface ShareArticles{
        String URL = "lg/user_article/add/json";
    }

    /**
     * 我的分享
     */
    interface MyShare{
        String URL = "user/lg/private_articles/{page}/json";
    }

    /**
     * 删除自己分享
     */
    interface DelMyShare{
        String URL = "lg/user_article/delete/{id}/json";
    }

    /**
     * 每日一问
     */
    interface QuestionArticle{
        String URL = "wenda/list/{page}/json";
    }



}
