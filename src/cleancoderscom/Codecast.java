package cleancoderscom;

import java.util.Date;

public class Codecast extends Entity {
    private String title;
    private Date publicationDate = new Date();
    private String permalink;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getPermalink() {
        return permalink;
    }
}