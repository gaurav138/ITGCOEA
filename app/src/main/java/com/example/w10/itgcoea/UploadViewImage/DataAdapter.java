package com.example.w10.itgcoea.UploadViewImage;

/**
 * Created by W10 on 25/02/2018.
 */


public class DataAdapter
{
    public String ImageURL;
    public String ImageTitle;
    public String ImageDate;
    public String ImageSubject;

    public String getImageDate() {
        return ImageDate;
    }

    public void setImageDate(String imageDate) {
        ImageDate = imageDate;
    }

    public String getImageSubject() {
        return ImageSubject;
    }

    public void setImageSubject(String imageSubject) {
        ImageSubject = imageSubject;
    }

    public String getImageUrl() {

        return ImageURL;
    }

    public void setImageUrl(String imageServerUrl) {

        this.ImageURL = imageServerUrl;
    }

    public String getImageTitle() {

        return ImageTitle;
    }

    public void setImageTitle(String Imagetitlename) {

        this.ImageTitle = Imagetitlename;
    }

}