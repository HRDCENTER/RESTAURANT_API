package kh.com.kshrd.restaurant.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import kh.com.kshrd.restaurant.enums.ImageType;

public class Image implements Serializable {

	/**	
	 * 
	 */
	private static final long serialVersionUID = 4602078173925266957L;
	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("TITLE")
	private String title;
	
	@JsonIgnore
	private String description;
	
	@JsonIgnore
	private Restaurant restaurant;
	
	@JsonProperty("URL")
	private String url;
	
	@JsonIgnore
	private ImageType type;
	
	@JsonIgnore
	private String status;
	
	@JsonIgnore
	private String createdDate;
	
	@JsonIgnore
	private User createdBy;
	
	//@JsonProperty("INDEX")
	@JsonIgnore
	private Integer index;
	
	@JsonProperty("THUMBNAIL_URL")
	private String thumbnailUrl;
	
	@JsonIgnore
	private String isThumbnail;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ImageType getType() {
		return type;
	}
	public void setType(ImageType type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public String getIsThumbnail() {
		return isThumbnail;
	}
	public void setIsThumbnail(String isThumbnail) {
		this.isThumbnail = isThumbnail;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	@Override
	public String toString() {
		return "Image [id=" + id + ", title=" + title + ", description=" + description + ", restaurant=" + restaurant
				+ ", url=" + url + ", type=" + type + ", status=" + status + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", index=" + index + ", thumbnailUrl=" + thumbnailUrl + ", isThumbnail="
				+ isThumbnail + "]";
	}
}
