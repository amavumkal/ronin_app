package com.roninswdstudio.ronin_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;

@Entity
@Table(	name = "exhibit_images")
public class ExhibitImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private Date date;

    private Date uploadDate;

    private String description;

    @Column(name = "image_url")
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    @JsonBackReference
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    @JsonBackReference(value = "category2")
    private Category cat;

    public ExhibitImage() {}

    public ExhibitImage(LinkedHashMap<String, Object> imgMap) {
        try {
            this.id = (long) imgMap.get("id");
        } catch (ClassCastException e) {
            this.id = (long) (int) imgMap.get("id");
        }
        this.title = (String) imgMap.get("title");
        this.date = new Date((long) imgMap.get("date"));
        this.uploadDate = new Date((long) imgMap.get("uploadDate"));
        this.description = (String) imgMap.get("description");
        this.imageURL = (String) imgMap.get("imageURL");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


}
