/**
 * 
 */
package com.android.aid;

import android.graphics.drawable.Drawable;

/**
 * @author wangpeifeng
 *
 */
public class CategoryInfoData {
	
	private String category_serial;
	private String app_category;
	private String category_dir;
	private String category_status;
	private String category_mapping;
	private String category_stamp;
	private String category_icon;
	private Drawable category_icon_drawable;
	private String category_order;
	private boolean select;
	
	
	
	public CategoryInfoData() {
		
		select = false;;
	}
	
	public void setIcon(Drawable icon){
		category_icon_drawable = icon;
	}
	public Drawable getIcon(){
		return category_icon_drawable;
	}
	
	public void setSelect(boolean select){
		this.select = select;
	}
	public boolean getSelect(){
		return select;
	}
	public void setSerial(String serial){
		this.category_serial = serial;
	}
	public String getSerial(){
		return category_serial;
	}
	
	public void setCategory(String category){
		app_category = category;
	}
	public String getCategory(){
		return app_category;
	}

	public void setDir(String dir){
		category_dir = dir;
	}
	public String getDir(){
		return category_dir;
	}
	
	public void setStatus(String status){
		category_status = status;
	}
	public String getStatus(){
		return category_status;
	}
	
	public void setMapping(String mapping){
		category_mapping = mapping;
	}
	public String getMapping(){
		return category_mapping;
	}
	
	public void setStamp(String stamp){
		category_stamp = stamp;
	}
	public String getStamp(){
		return category_stamp;
	}
}
