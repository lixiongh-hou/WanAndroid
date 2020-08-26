package com.example.mvpbase.utils.constant;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 14:55
 */
public interface ConstantUtil {

    /**用于设置该应用的主文件夹; SPUtil保存的文件名;*/
    String APP_NAME             = "SearchDataList";

    /**登录保存的标识*/
    String COOKIE               = "cookie";
    /**用户登录判断是否登录*/
    String USER                 = "user";
    /**默认的主题色*/
    String COLOR                = "color";
    /**夜间模式切换的标题颜色，不是夜间模式就是主题色*/
    String COLOR_TITLE          = "titleColor";
    /**选中的主题色*/
    String COLOR_INDEX          = "colorIndex";
    /**一页显示多少条*/
    String PAGE_SIZE            = "pagesize";
    /**当前页码*/
    String PAGE                 = "page";

    String HEAD                 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=691490842,3707166258&fm=26&gp=0.jpg";


    String NETWORK              = "网络不可用,请检查你的网络";
    /**刷新,加载*/
    int REFRESH = 1;
    int LOADING = 2;
}
