package com.example.demo;

public class Book {
	private int id;
	private String title;
	private String author;
	private String discription;
	private String dateofissue;
	private String pages;
	private String category;
	public Book() {
		
	}
	public Book(int id, String title, String author, String discription, String dateofissue, String pages, String category) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.discription = discription;
		this.dateofissue = dateofissue;
		this.pages = pages;
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getDateofissue() { return dateofissue; }
	public void setDateofissue(String dateofissue) {
		this.dateofissue = dateofissue;
	}
	public String getPages() { return pages; }
	public void setPages(String pages) { this.pages = pages; }
	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }
	
}
