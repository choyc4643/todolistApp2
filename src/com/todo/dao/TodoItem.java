package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
	private String category;
    private String title;
    private String desc;
    private String due_date;
    private String current_date;
    private String withWhom;
    private int importance;
    private int is_completed;


    public TodoItem(String category, String title, String desc, String due_date, String withWhom, int importance, int is_completed){
        this.category=category;
        this.title=title;
        this.desc=desc;
        this.due_date = due_date;
        this.withWhom = withWhom;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date = f.format(new Date());
        this.importance = importance;
        this.is_completed = is_completed;
    }
    
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
    
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    @Override
    public String toString() {
    	return id + " [" + category + "] " + title + " - " + desc + " - "+ withWhom + " - " + due_date + " - " + current_date + " - " + importance + " - "+ is_completed + "\n";
    }

	public String getWithWhom() {
		return withWhom;
	}

	public void setWithWhom(String withWhom) {
		this.withWhom = withWhom;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}
	
    

}
