package com.example.wanandroid.api;

import com.example.mvpbase.bean.BaseBean;
import com.example.wanandroid.main.bean.MyShareBean;
import com.example.wanandroid.main.bean.PointsRankingBean;
import com.example.wanandroid.main.bean.QuestionArticleBean;
import com.example.wanandroid.main.bean.SearchBean;
import com.example.wanandroid.main.bean.SearchHotKeyBean;
import com.example.wanandroid.main.bean.SquareListBean;
import com.example.wanandroid.main.bean.UserPointsBean;
import com.example.wanandroid.main.bean.UserPointsListBean;
import com.example.wanandroid.ui.home.bean.ArticleBean;
import com.example.wanandroid.ui.home.bean.BannerBase;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.wanandroid.ui.login.bean.LogInBean;
import com.example.wanandroid.main.bean.MyCollectionBean;
import com.example.wanandroid.ui.navigation.bean.NavigationBean;
import com.example.wanandroid.ui.project.bean.ProjectListBean;
import com.example.wanandroid.ui.system.bean.SystemBean;
import com.example.wanandroid.wechat.bean.WeChatListBean;
import com.example.wanandroid.wechat.bean.WeChatNameBean;
import com.google.gson.JsonElement;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 15:37
 * 接口地址
 */
public interface ApiService {

    /**
     * 首页Banner
     * @return
     */
    @GET(UrlParam.Banner.URL)
    Observable<BaseBean<List<BannerBase>>> getBanner();

    /**
     * 首页文章数据
     * @param page
     * @return
     */
    @GET(UrlParam.Article.URL)
    Observable<BaseBean<ArticleBean>> getArticle(
            @Path("page") String page
    );
    /**
     * 首页文章置顶
     * @return
     */
    @GET(UrlParam.ArticleTop.URL)
    Observable<BaseBean<List<DatasBean>>> getArticleTop();

    /**
     * 登录
     * @return
     */
    @FormUrlEncoded
    @POST(UrlParam.LogIn.URL)
    Observable<BaseBean<LogInBean>> logIn(
            @FieldMap Map<String, Object> map
    );

    /**
     * 收藏文章
     * @param id
     * @return
     */
    @POST(UrlParam.Collect.URL)
    Observable<BaseBean<JsonElement>> collect(
            @Path("id") String id
    );
    /**
     * 取消收藏文章
     * @param id
     * @return
     */
    @POST(UrlParam.UnCollect.URL)
    Observable<BaseBean<JsonElement>> unCollect(
            @Path("id") String id
    );

    /**
     * 取消我的收藏文章
     * @param id
     * @param originId
     * @return
     */
    @POST(UrlParam.UnMyCollect.URL)
    Observable<BaseBean<JsonElement>> unMyCollect(
            @Path("id") String id, @Query("originId") String originId
    );

    /**
     * 我的收藏
     * @param id
     * @return
     */
    @GET(UrlParam.MyCollect.URL)
    Observable<BaseBean<MyCollectionBean>> getMyCollect(
            @Path("page") String id
    );

    /**
     * 添加文章
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(UrlParam.AddCollection.URL)
    Observable<BaseBean<JsonElement>> addCollection(
            @FieldMap Map<String, Object> map
    );

    /**
     * 获取体系数据
     * @return
     */
    @GET(UrlParam.System.URL)
    Observable<BaseBean<List<SystemBean>>> getSystem();

    /**
     * 获取体系下的数据
     * @param page
     * @param cid
     * @return
     */
    @GET(UrlParam.SystemDetailed.URL)
    Observable<BaseBean<ArticleBean>> getSystemDetailed(
            @Path("page") String page, @Query("cid") String cid
    );

    /**
     * 公众号名称
     * @return
     */
    @GET(UrlParam.WeChatName.URL)
    Observable<BaseBean<List<WeChatNameBean>>> getWeChatName();

    /**
     * 公众号名称对应的数据
     * @param id
     * @param page
     * @return
     */
    @GET(UrlParam.WeChatList.URL)
    Observable<BaseBean<WeChatListBean>> getWeChatList(
            @Path("id") String id, @Path("page") String page
    );

    /**
     * 项目分类
     * @return
     */
    @GET(UrlParam.ProjectTree.URL)
    Observable<BaseBean<List<WeChatNameBean>>> getProjectTree();


    /**
     * 项目列表数据
     * @param page
     * @param cid
     * @return
     */
    @GET(UrlParam.ProjectList.URL)
    Observable<BaseBean<ProjectListBean>> getProjectList(
            @Path("page") String page, @Query("cid") String cid
    );

    /**
     * 搜索热词
     * @return
     */
    @GET(UrlParam.SearchHotKey.URL)
    Observable<BaseBean<List<SearchHotKeyBean>>> searchHotKey();

    /**
     * 搜索关键词
     * @param page
     * @param k
     * @return
     */
    @POST(UrlParam.Search.URL)
    Observable<BaseBean<SearchBean>> search(
            @Path("page") String page, @Query("k") String k
    );

    /**
     * 获取排行榜
     * @param page
     * @return
     */
    @GET(UrlParam.PointsRanking.URL)
    Observable<BaseBean<PointsRankingBean>> getPointsRanking(
            @Path("page") String page
    );

    /**
     * 获取用户积分
     * @return
     */
    @GET(UrlParam.UserPoints.URL)
    Observable<BaseBean<UserPointsBean>> getUserPoints();

    /**
     * 用户获取积分列表
     * @param page
     * @return
     */
    @GET(UrlParam.UserPointsList.URL)
    Observable<BaseBean<UserPointsListBean>> getUserPointsList(
            @Path("page") String page
    );

    /**
     * 广场类别数据
     * @param page
     * @return
     */
    @GET(UrlParam.Square.URL)
    Observable<BaseBean<SquareListBean>> getSquareList(
            @Path("page") String page
    );

    /**
     * 分享文章
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(UrlParam.ShareArticles.URL)
    Observable<BaseBean<JsonElement>> shareArticles(
            @FieldMap Map<String, Object> map
    );

    /**
     * 我的分享
     * @param page
     * @return
     */
    @GET(UrlParam.MyShare.URL)
    Observable<BaseBean<MyShareBean>> myShare(
            @Path("page") String page
    );

    /**
     * 删除自己分享
     * @param id
     * @return
     */
    @POST(UrlParam.DelMyShare.URL)
    Observable<BaseBean<JsonElement>> delMyShare(
            @Path("id") String id
    );

    /**
     * 每日一问
     * @param page
     * @return
     */
    @GET(UrlParam.QuestionArticle.URL)
    Observable<BaseBean<QuestionArticleBean>> getQuestionArticle(
            @Path("page") String page
    );

    /**
     * 导航数据
     * @return
     */
    @GET(UrlParam.Navigation.URL)
    Observable<BaseBean<List<NavigationBean>>> getNavigation();

}
