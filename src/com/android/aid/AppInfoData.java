/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;






import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;

/**
 * @author wangpeifeng
 *
 */
public class AppInfoData {
	private Context context;
	private String category;
	private Drawable icon;
	private String label;
	private String packageName;
	private String versionName;
	private int versionCode;
	private String state;
	private int price;
	private String path;

	private String fileName;

	private String url;

	private String stamp;

	private String update_time;

	private String install_time;
	
	private String install_action;

	private String install_action_time;
	
	private String launch_time;
	
	private String launch_by_me;

	private String location;

	private long file_length;

	private long downloaded_length;

	private String notes;
	
	private boolean my;
	private String my_category;
	
	private boolean download_stop = false;
	private String download_status = Data.DB_VALUE_DOWNLOAD_DOWNLOADING;
	private String start_time = null;
	private String stop_time = null;
	
	

	/**
	 * 
	 */
	public AppInfoData(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
		downloaded_length=-1;
		file_length = 0;
		
		icon = null;
		
		my = false;
		my_category = Data.DB_VALUE_CATEGORY_NULL;
	}

	public void setLaunchTime(String time){
		launch_time = time;
	}
	public String getLaunchTime(){
		return launch_time;
	}
	
	public void setLaunchByMe(String launch_by_me){
		this.launch_by_me = launch_by_me;
	}
	public String getLaunchByMe(){
		return launch_by_me;
	}
	
	public void setStatus(String status){
		download_status = status;
		
	}
	public String getStatus(){
		return download_status;
	}
	
	public void setStartTime(String time){
		this.start_time = time;
	}
	public String getStartTime(){
		return start_time;
	}
	
	public void setStopTime(String time){
		this.stop_time = time;
	}
	public String getStopTime(){
		return stop_time;
	}
	
	public void setPrice(int price){
		this.price = price;
	}
	public int getPrice(){
		return price;
	}
	
	public void setDownloadStop(boolean download_stop){
		this.download_stop = download_stop;
	}
	public boolean getDownloadStop(){
		return download_stop;
	}
	
	public void setAppMy(boolean my){
		this.my = my;
	}
	public boolean getAppMy(){
		return my;
	}
	
	public void setAppMyCategory(String category){
		my_category = category;
	}
	public String getAppMyCategory(){
		return my_category;
	}
	
	public void setCategory(String category){
		this.category = category;
	}
	public String getCategory(){
		return category;
	}
	
	public void setIcon(Drawable icon){
		this.icon = icon;
		
	}
	public Drawable getIcon(){
		return icon;
	}

	public void setLabel(String label){
		this.label = label; 
	}
	
	public String getLabel(){
		return label;
	}
	
	public void setPackage(String packageName){
		this.packageName = packageName;
	}
	public String getPackage(){
		return packageName;
	}
	
	public void setVersionName(String versionName){
		this.versionName = versionName;
		
	}
	public String getVersionName(){
		return versionName;
	}
	
	public void setVersionCode(int versionCode){
		this.versionCode = versionCode;
	}
	public int getVersionCode(){
		return versionCode;
	}
	
	public void setFileName(String name){
		fileName = name;
	}
	public String getFileName(){
		return fileName;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setState(String state){
		this.state = state;
	}
	public String getState(){
		return state;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}
	
	public void setSavPath(){
		if(Data.DB_VALUE_CATEGORY_ICON.equals(category)){
			path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/icon/"+packageName+Data.DB_VALUE_DOWNLOADING_SUFFIX;
			
		}
		else{
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+fileName+Data.DB_VALUE_DOWNLOADING_SUFFIX;
		}
	}
	/*
	public String  getTempPath(){
		
		if(Data.DB_VALUE_CATEGORY_ICON.equals(category)){
			return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/icon/"+packageName+Data.DB_VALUE_DOWNLOADING_SUFFIX;
			
		}
		else{
			return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+fileName+Data.DB_VALUE_DOWNLOADING_SUFFIX;
		}
	}
	*/
	public String getSavedPath(){
		if(Data.DB_VALUE_CATEGORY_ICON.equals(category)){
			return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/icon/"+packageName;
			
		}
		else{
			return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+fileName;
		}
		
	}
	
	public String getIconPath(){
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/icon/"+packageName+".png";
		
	}
	
	
	
/*	
	public void addPathSuffix(){
		if(Data.DB_VALUE_CATEGORY_ICON.equals(category)){
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/icon/"+packageName+Data.DB_VALUE_DOWNLOADING_SUFFIX;
			
		}
		else{
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+fileName+Data.DB_VALUE_DOWNLOADING_SUFFIX;
		}
	}
	public void removePathSuffix(){
		
		File file = new File(path);

		path = path.replace(Data.DB_VALUE_DOWNLOADING_SUFFIX, "");
				file.renameTo(new File(path));	

	}
*/	
	public void setStamp(String stamp){
		this.stamp = stamp;
		
	}
	public String getStamp(){
		return stamp;
	}
	
	
	public void setInstallTime(String time){
		this.install_time = time;
	}
	public String getInstallTime(){
		return install_time;
	}
	
	public void setUpdateTime(String time){
		this.update_time = time;
	}
	public String getUpdateTime(){
		return this.update_time;
	}
	
	public void setInstallActionTime(String time){
		install_action_time = time;
	}
	public String getInstallActionTime(){
		return install_action_time;
	}
	
	public void setInstallAction(String action){
		install_action = action;
		
	}
	public String getInstallAction(){
		return install_action;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	public String getLocation(){
		return location;
	}
	
	public void setFileLength(long length){
		this.file_length = length;
	}
	public long getFileLength(){
		return file_length;
	}
	
	
	public void setDownloadedLength(long length){
		this.downloaded_length = length;
	}
	public long getDownloadedLength(){
		return downloaded_length;
	}
	
	public void setNotes(String notes){
		this.notes = notes;
	}
	public void addNotes(String notes){
		this.notes += notes;
	}
	public String getNotes(){
		return notes;
	}
	
/*	
	public void checkPackageDownloaded(AppInfoData appInfoData){
		
		ArrayList<String> listFile = GetFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),
				"apk",true);
		Iterator iterator = listFile.iterator();
		while(iterator.hasNext()){
			String  filename = (String) iterator.next();
            AppInfoData apkInfoData = getApkFileInfo(context, filename);
			if(apkInfoData != null){  
				 if(appInfoData.packageName.equals(apkInfoData.getPackage())){
		            	if(apkInfoData.getVersionCode()>appInfoData.getVersionCode()){
		            		appInfoData.setPath(filename);
		            		appInfoData.setVersionCode(apkInfoData.getVersionCode());
		            		
		            	}
		            	appInfoData.setIcon(apkInfoData.getIcon());
		            	appInfoData.setState("Downloaded");
		            }
				
	        }     			

		}		
	}
	
	public void checkPackageInstalled(AppInfoData appInfoData){
		PackageManager packageManager =context.getPackageManager();  
		
		ArrayList<ApplicationInfo> listApp = (ArrayList<ApplicationInfo>) packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
		
		Iterator iterator = listApp.iterator();
		int i = 1;
		List<PackageInfo> listPackage = packageManager.getInstalledPackages(PackageManager.GET_INTENT_FILTERS);
		
		
		while(iterator.hasNext()){
			ApplicationInfo appInfo = (ApplicationInfo) iterator.next();
			String packageName = appInfo.packageName;
			if(appInfo.packageName.equals(appInfoData.getPackage())){
				appInfoData.setState("Installed");
				appInfoData.setIcon(appInfo.loadIcon(packageManager));
			}

		}
	
	
	}
	*/
	/*
	public static ArrayList<String > GetFiles(String Path, String Extension, boolean IsIterative)  //搜索目录，扩展名，是否进入子文件夹
	{
		ArrayList<String> lstFile = new ArrayList<String>();  //结果 List
		File[] files = new File(Path).listFiles();
	    for (int i = 0; i < files.length; i++)
	    {
	        File f = files[i];
	        if (f.isFile())
	        {
	        	String ext = f.getPath().substring(f.getPath().length() - Extension.length()).toUpperCase();
	            if (ext.equals(Extension.toUpperCase()))  //判断扩展名
	                lstFile.add(f.getPath());
	  
	            if (!IsIterative)
	                break;
	        }
	        else if (f.isDirectory() && f.getPath().indexOf("/.") == -1)  //忽略点文件（隐藏文件/文件夹）
	            GetFiles(f.getPath(), Extension, IsIterative);
	    }
	    
	    return lstFile;
	}
	*/

    /** 
     * 获取未安装的apk信息 
     *  
     * @param ctx 
     * @param apkPath 
     * @return 
     */  
	/*
    public AppInfoData getApkFileInfo(Context ctx, String apkPath) {  
        System.out.println(apkPath);  
        File apkFile = new File(apkPath);  
        if (!apkFile.exists() || !apkPath.toLowerCase().endsWith(".apk")) {  
            System.out.println("文件路径不正确");  
            return null;  
        }  
        
        AppInfoData appInfoData = new AppInfoData(context);  
        
        String PATH_PackageParser = "android.content.pm.PackageParser";  
        String PATH_AssetManager = "android.content.res.AssetManager";  
        try {  
            //反射得到pkgParserCls对象并实例化,有参数  
            Class<?> pkgParserCls = Class.forName(PATH_PackageParser);  
            Class<?>[] typeArgs = {String.class};  
            Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);  
            Object[] valueArgs = {apkPath};  
            Object pkgParser = pkgParserCt.newInstance(valueArgs);  
              
            //从pkgParserCls类得到parsePackage方法  
            DisplayMetrics metrics = new DisplayMetrics();  
            metrics.setToDefaults();//这个是与显示有关的, 这边使用默认  
            typeArgs = new Class<?>[]{File.class,String.class,  
                                    DisplayMetrics.class,int.class};  
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(  
                    "parsePackage", typeArgs);  
              
            valueArgs=new Object[]{new File(apkPath),apkPath,metrics,0};  
              
            //执行pkgParser_parsePackageMtd方法并返回  
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,  
                    valueArgs);  
              
            //从返回的对象得到名为"applicationInfo"的字段对象    
            if (pkgParserPkg==null) {  
                return null;  
            }  
            Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(  
                    "applicationInfo");  
              
            //从对象"pkgParserPkg"得到字段"appInfoFld"的值  
            if (appInfoFld.get(pkgParserPkg)==null) {  
                return null;  
            }  
            ApplicationInfo info = (ApplicationInfo) appInfoFld  
                    .get(pkgParserPkg);           
              
            //反射得到assetMagCls对象并实例化,无参  
            Class<?> assetMagCls = Class.forName(PATH_AssetManager);            
            Object assetMag = assetMagCls.newInstance();  
            //从assetMagCls类得到addAssetPath方法  
            typeArgs = new Class[1];  
            typeArgs[0] = String.class;  
            Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(  
                    "addAssetPath", typeArgs);  
            valueArgs = new Object[1];  
            valueArgs[0] = apkPath;  
            //执行assetMag_addAssetPathMtd方法  
            assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);  
              
              
            //得到Resources对象并实例化,有参数  
            Resources res = ctx.getResources();  
            typeArgs = new Class[3];  
            typeArgs[0] = assetMag.getClass();  
            typeArgs[1] = res.getDisplayMetrics().getClass();  
            typeArgs[2] = res.getConfiguration().getClass();  
            Constructor<Resources> resCt = Resources.class  
                    .getConstructor(typeArgs);  
            valueArgs = new Object[3];  
            valueArgs[0] = assetMag;  
            valueArgs[1] = res.getDisplayMetrics();  
            valueArgs[2] = res.getConfiguration();  
            res = (Resources) resCt.newInstance(valueArgs);  
              
              
            // 读取apk文件的信息   
            if (info!=null) {  
                if (info.icon != 0) {// 图片存在，则读取相关信息  
                    Drawable icon = res.getDrawable(info.icon);// 图标  
                    appInfoData.setIcon(icon);  
                    }  
                if (info.labelRes != 0) {  
                    String name = (String) res.getText(info.labelRes);// 名字  
                    appInfoData.setLabel(name);  
                }else {  
                    String apkName=apkFile.getName();  
                    appInfoData.setLabel(apkName.substring(0,apkName.lastIndexOf(".")));  
                }  
                String pkgName = info.packageName;// 包名           
                appInfoData.setPackage(pkgName);  
            }else {  
                return null;  
            }             
            PackageManager pm = ctx.getPackageManager();  
            PackageInfo packageInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);  
            if (packageInfo != null) {  
                appInfoData.setVersionName(packageInfo.versionName);//版本号  
                appInfoData.setVersionCode(packageInfo.versionCode);//版本码  
            }  
            return appInfoData;  
        } catch (Exception e) {   
            e.printStackTrace();  
        }  
        return null;  
    }  
*/
}
