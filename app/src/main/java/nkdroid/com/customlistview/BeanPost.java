package nkdroid.com.customlistview;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nirav kalola on 11/8/2014.
 */
public class BeanPost {

    @SerializedName("post_title")
    private String post_title;
    @SerializedName("post_date")
    private String post_date;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("description")
    private String description;

    public BeanPost(String post_title, String post_date, String image_url, String description) {
        this.post_title = post_title;
        this.post_date = post_date;
        this.image_url = image_url;
        this.description = description;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
