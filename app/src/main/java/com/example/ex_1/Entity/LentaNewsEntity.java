package com.example.ex_1.Entity;

public class LentaNewsEntity {
    private int idlentaNews;
    private String nameNews;
    private String data;
    private String time;
    private String text;
    private String urlPictureNews;
    private String authorNews;

    public LentaNewsEntity(int idlentaNews, String data, String time, String nameNews,
                           String text, String urlPictureNews, String authorNews) {
        this.idlentaNews = idlentaNews;
        this.data = data;
        this.time = time;
        this.text = text;
        this.urlPictureNews = urlPictureNews;
        this.authorNews = authorNews;
        this.nameNews = nameNews;
    }

    public LentaNewsEntity() {
    }

    public String getNameNews() {
        return nameNews;
    }

    public void setNameNews(String nameNews) {
        this.nameNews = nameNews;
    }

    public int getIdlentaNews() {
        return idlentaNews;
    }

    public void setIdlentaNews(int idlentaNews) {
        this.idlentaNews = idlentaNews;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrlPictureNews() {
        return urlPictureNews;
    }

    public void setUrlPictureNews(String urlPictureNews) {
        this.urlPictureNews = urlPictureNews;
    }

    public String getAuthorNews() {
        return authorNews;
    }

    public void setAuthorNews(String authorNews) {
        this.authorNews = authorNews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LentaNewsEntity that = (LentaNewsEntity) o;
        if (idlentaNews != that.idlentaNews) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (urlPictureNews != null ? !urlPictureNews.equals(that.urlPictureNews) : that.urlPictureNews != null)
            return false;
        if (authorNews != null ? !authorNews.equals(that.authorNews) : that.authorNews != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = idlentaNews;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (urlPictureNews != null ? urlPictureNews.hashCode() : 0);
        result = 31 * result + (authorNews != null ? authorNews.hashCode() : 0);
        return result;
    }
}
