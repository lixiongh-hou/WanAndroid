package com.example.wanandroid.bean;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 11:34
 */
public class PromptBean {
    /**图片*/
    private int image;
    /**内容*/
    private String content;
    /**主题颜色*/
    private int themeColor;

    public PromptBean() {
    }

    public PromptBean(int image, String content) {
        this.image = image;
        this.content = content;
    }

    public PromptBean(String content, int themeColor) {
        this.content = content;
        this.themeColor = themeColor;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
    }
}
