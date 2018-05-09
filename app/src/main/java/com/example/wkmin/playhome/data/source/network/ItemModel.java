package com.example.wkmin.playhome.data.source.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemModel {

    @SerializedName("lastBuildDate")
    @Expose
    private String lastBuildDate;

    @SerializedName("total")
    @Expose
    private Long total;

    @SerializedName("start")
    @Expose
    private Integer start;

    @SerializedName("display")
    @Expose
    private Integer display;

    @SerializedName("items")
    @Expose
    private ArrayList<Item> items;

    public class Item {

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("link")
        @Expose
        private String link;

        @SerializedName("description")
        @Expose
        private String description;

        @SerializedName("bloggername")
        @Expose
        private String bloggername;

        @SerializedName("bloggerlink")
        @Expose
        private String bloggerlink;

        @SerializedName("postdate")
        @Expose
        private String postdate;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBloggername() {
            return bloggername;
        }

        public void setBloggername(String bloggername) {
            this.bloggername = bloggername;
        }

        public String getBloggerlink() {
            return bloggerlink;
        }

        public void setBloggerlink(String bloggerlink) {
            this.bloggerlink = bloggerlink;
        }

        public String getPostdate() {
            return postdate;
        }

        public void setPostdate(String postdate) {
            this.postdate = postdate;
        }
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
