/**
 * 
 */
package com.android.aid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;


/**
 * @author wangpeifeng
 *
 */
public class FileProxy {
	private static final String TAG = "FileProxy";
	
	private Context context;
	
	public static void checkIconAsset(Context context,String packageName){
		InputStream iStream = null;
		OutputStream oStream = null;
		try{

			String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
			
			File file = new File(path);
			
			if(!file.exists()){
				file.mkdirs();
			}
			
			file = new File(file.getAbsoluteFile()+"/icon");
			if(!file.exists()){
				file.mkdir();
			}
			
			String iconfile = packageName+Data.DB_VALUE_ICON_SUFFIX;
			Log.i(TAG, "iconfile="+iconfile);
			iStream = context.getAssets().open(iconfile);

//			oStream  = context.openFileOutput(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//	        		+ "/icon/"+packageName+".png", Context.MODE_PRIVATE);

			oStream  = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
	        		+ "/icon/"+packageName+".png",false);	

			byte[] buffer = new byte[1024];
			int length = 0;
			while((length = iStream.read(buffer))>0){
				oStream.write(buffer, 0, length);
			}
				
			oStream.flush();
			
		}catch(Exception e){
			e.printStackTrace();
			Log.i(TAG, "msg:"+e.getMessage()+",cause:"+e.getCause());
		}
		finally{
			try {
				if(oStream!=null)
					oStream.close();
				if(iStream!=null)
					iStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(TAG, "msg:"+e.getMessage()+",cause:"+e.getCause());
			}
			
		}
		
	}
	
	public static String getCategoryAssets(Context context){
		
		String resp = null;

		try{

			File file = new File(context.getFilesDir().getAbsolutePath()+"/"+Data.ANDROIDAID_CATEGORY_FILE);
			
//			if(!file.exists()){
				
				Log.i(TAG, "file not exist "+file.getPath());
				
				InputStream iStream = context.getAssets().open(Data.ANDROIDAID_CATEGORY_FILE);
				//OutputStream oStream  = new FileOutputStream(file,false);
				OutputStream oStream  = context.openFileOutput(Data.ANDROIDAID_CATEGORY_FILE, Context.MODE_PRIVATE);
				
				byte[] buffer = new byte[1024];
				int length = 0;
				while((length = iStream.read(buffer))>0){
					Log.i(TAG, "read length = "+length);
					oStream.write(buffer, 0, length);
				}
				
				oStream.flush();
				oStream.close();
				iStream.close();
			
//			}
			
			FileInputStream inStream = context.openFileInput(Data.ANDROIDAID_CATEGORY_FILE);  
			StringBuffer sb = new StringBuffer();
			int c;
			while((c=inStream.read())!=-1){
				sb.append((char)c);
			}
			inStream.close();
			
			resp = sb.toString();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return resp;
		
	}

	
	public static String getAppRepoAssets(Context context){
		
		String resp = null;

		try{

			File file = new File(context.getFilesDir().getAbsolutePath()+"/"+Data.ANDROIDAID_APP_REPO_FILE);
			
//			if(!file.exists()){
				
				Log.i(TAG, "file not exist "+file.getPath());
				
				InputStream iStream = context.getAssets().open(Data.ANDROIDAID_APP_REPO_FILE);
				//OutputStream oStream  = new FileOutputStream(file,false);
				OutputStream oStream  = context.openFileOutput(Data.ANDROIDAID_APP_REPO_FILE, Context.MODE_PRIVATE);
				
				byte[] buffer = new byte[1024];
				int length = 0;
				while((length = iStream.read(buffer))>0){
					Log.i(TAG, "read length = "+length);
					oStream.write(buffer, 0, length);
				}
				
				oStream.flush();
				oStream.close();
				iStream.close();
			
//			}
			
			FileInputStream inStream = context.openFileInput(Data.ANDROIDAID_APP_REPO_FILE);  
			StringBuffer sb = new StringBuffer();
			int c;
			while((c=inStream.read())!=-1){
				sb.append((char)c);
			}
			inStream.close();
			
			resp = sb.toString();
			
			Log.i(TAG, resp);
			
		}catch(Exception e){
			e.printStackTrace();
			Log.i(TAG, "cause:"+e.getCause()+",msg:"+e.getMessage());
		}
		
		return resp;
		
	}

	
	public static String getDownloadRootUrl(Context context){
		
		String url = null;

		try{

			File file = new File(context.getFilesDir().getAbsolutePath()+"/"+Data.ANDROIDAID_CONFIG_FILE);
//			if(!file.exists()){
				
				Log.i(TAG, "file not exist "+context.getFilesDir().getAbsolutePath()+"/"+Data.ANDROIDAID_CONFIG_FILE);
				
				InputStream iStream = context.getAssets().open(Data.ANDROIDAID_CONFIG_FILE);
				//OutputStream oStream  = new FileOutputStream(file,false);
				OutputStream oStream  = context.openFileOutput(Data.ANDROIDAID_CONFIG_FILE, Context.MODE_PRIVATE);
				
				byte[] buffer = new byte[1024];
				int length = 0;
				while((length = iStream.read(buffer))>0){
					Log.i(TAG, "read length = "+length);
					oStream.write(buffer, 0, length);
				}
				
				oStream.flush();
				oStream.close();
				iStream.close();
			
//			}
			
			FileInputStream inStream = context.openFileInput(Data.ANDROIDAID_CONFIG_FILE);  
			StringBuffer sb = new StringBuffer();
			int c;
			while((c=inStream.read())!=-1){
				sb.append((char)c);
			}
			inStream.close();
			
			JSONObject json = new JSONObject(sb.toString());
			url = json.getString(Data.CONFIG_DOWNLOAD_URL);
			Log.i(TAG, "url = "+url);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(url == null)
			url = Data.DOWNLOAD_URL;
		return url;
		
		
	}
	
	public static String getApiRootUrl(Context context){
		
		String url = null;

		try{

			File file = new File(context.getFilesDir().getAbsolutePath()+"/"+Data.ANDROIDAID_CONFIG_FILE);
//			if(!file.exists()){
				
				Log.i(TAG, "file not exist "+context.getFilesDir().getAbsolutePath()+"/"+Data.ANDROIDAID_CONFIG_FILE);
				
				InputStream iStream = context.getAssets().open(Data.ANDROIDAID_CONFIG_FILE);
				//OutputStream oStream  = new FileOutputStream(file,false);
				OutputStream oStream  = context.openFileOutput(Data.ANDROIDAID_CONFIG_FILE, Context.MODE_PRIVATE);
				
				byte[] buffer = new byte[1024];
				int length = 0;
				while((length = iStream.read(buffer))>0){
					Log.i(TAG, "read length = "+length);
					oStream.write(buffer, 0, length);
				}
				
				oStream.flush();
				oStream.close();
				iStream.close();
			
//			}
			
			FileInputStream inStream = context.openFileInput(Data.ANDROIDAID_CONFIG_FILE);  
			StringBuffer sb = new StringBuffer();
			int c;
			while((c=inStream.read())!=-1){
				sb.append((char)c);
			}
			inStream.close();
			
			JSONObject json = new JSONObject(sb.toString());
			url = json.getString(Data.CONFIG_API_URL);
			Log.i(TAG, "url = "+url);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(url == null)
			url = Data.API_URL;
		return url;
		
	}
	
	public static ArrayList<String> GetFiles(ArrayList<String> listFile, String Path, String Extension, boolean IsIterative)  //����Ŀ¼����չ�����Ƿ�������ļ���
	{
		//Log.i(TAG, "GetFiles "+Path);
	
		try{

			File[] files = new File(Path).listFiles();
			for (int i = 0; i < files.length; i++)
			{
				File f = files[i];
				if (f.isFile())
				{
					String ext = f.getPath().substring(f.getPath().length() - Extension.length()).toUpperCase();
					if (ext.equals(Extension.toUpperCase()))  //�ж���չ��
						listFile.add(f.getPath());
	  
					if (!IsIterative)
						break;
				}
				else if (f.isDirectory() && f.getPath().indexOf("/.") == -1)  //���Ե��ļ��������ļ�/�ļ��У�
					GetFiles(listFile, f.getPath(), Extension, IsIterative);
			}
	    
		}
		catch(Exception e){
			e.printStackTrace();
		}
	    
	    return listFile;
	}
	
    /** 
     * ��ȡδ��װ��apk��Ϣ 
     *  
     * @param ctx 
     * @param apkPath 
     * @return 
     */  
    public static AppInfoData getApkFileInfo(Context ctx, String apkPath) {  
        System.out.println(apkPath);  
        File apkFile = new File(apkPath);  
        if (!apkFile.exists() || !apkPath.toLowerCase().endsWith(".apk")) {  
            System.out.println("�ļ�·������ȷ");  
            return null;  
        }  
        
        AppInfoData appInfoData = new AppInfoData(ctx);  
        
        String PATH_PackageParser = "android.content.pm.PackageParser";  
        String PATH_AssetManager = "android.content.res.AssetManager";  
        try {  
            //����õ�pkgParserCls����ʵ����,�в���  
            Class<?> pkgParserCls = Class.forName(PATH_PackageParser);  
            Class<?>[] typeArgs = {String.class};  
            Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);  
            Object[] valueArgs = {apkPath};  
            Object pkgParser = pkgParserCt.newInstance(valueArgs);  
              
            //��pkgParserCls��õ�parsePackage����  
            DisplayMetrics metrics = new DisplayMetrics();  
            metrics.setToDefaults();//���������ʾ�йص�, ���ʹ��Ĭ��  
            typeArgs = new Class<?>[]{File.class,String.class,  
                                    DisplayMetrics.class,int.class};  
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(  
                    "parsePackage", typeArgs);  
              
            valueArgs=new Object[]{new File(apkPath),apkPath,metrics,0};  
              
            //ִ��pkgParser_parsePackageMtd����������  
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,  
                    valueArgs);  
              
            //�ӷ��صĶ���õ���Ϊ"applicationInfo"���ֶζ���    
            if (pkgParserPkg==null) {  
                return null;  
            }  
            Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(  
                    "applicationInfo");  
              
            //�Ӷ���"pkgParserPkg"�õ��ֶ�"appInfoFld"��ֵ  
            if (appInfoFld.get(pkgParserPkg)==null) {  
                return null;  
            }  
            ApplicationInfo info = (ApplicationInfo) appInfoFld  
                    .get(pkgParserPkg);           
              
            //����õ�assetMagCls����ʵ����,�޲�  
            Class<?> assetMagCls = Class.forName(PATH_AssetManager);            
            Object assetMag = assetMagCls.newInstance();  
            //��assetMagCls��õ�addAssetPath����  
            typeArgs = new Class[1];  
            typeArgs[0] = String.class;  
            Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(  
                    "addAssetPath", typeArgs);  
            valueArgs = new Object[1];  
            valueArgs[0] = apkPath;  
            //ִ��assetMag_addAssetPathMtd����  
            assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);  
              
              
            //�õ�Resources����ʵ����,�в���  
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
              
              
            // ��ȡapk�ļ�����Ϣ   
            if (info!=null) {  
                if (info.icon != 0) {// ͼƬ���ڣ����ȡ�����Ϣ  
                    Drawable icon = res.getDrawable(info.icon);// ͼ��  
                    appInfoData.setIcon(icon);  
                    }  
                if (info.labelRes != 0) {  
                    String name = (String) res.getText(info.labelRes);// ����  
                    appInfoData.setLabel(name);  
                }else {  
                    String apkName=apkFile.getName();  
                    appInfoData.setLabel(apkName.substring(0,apkName.lastIndexOf(".")));  
                }  
                String pkgName = info.packageName;// ����           
                appInfoData.setPackage(pkgName);  
            }else {  
                return null;  
            }             
            PackageManager pm = ctx.getPackageManager();  
            PackageInfo packageInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);  
            if (packageInfo != null) {  
                appInfoData.setVersionName(packageInfo.versionName);//�汾��  
                appInfoData.setVersionCode(packageInfo.versionCode);//�汾��  
            }  
            return appInfoData;  
        } catch (Exception e) {   
            e.printStackTrace();  
        }  
        return null;  
    }  

    /** 
     * ��ȡδ��װ��apk��Ϣ 
     *  
     * @param ctx 
     * @param apkPath 
     * @return 
     */  
    public static void setApkFileIcon(Context ctx, String apkPath,AppInfoData appInfoData) {  
        System.out.println(apkPath);  
        File apkFile = new File(apkPath);  
        if (!apkFile.exists() || !apkPath.toLowerCase().endsWith(".apk")) {  
            System.out.println("�ļ�·������ȷ");  
            return ;  
        }  
        
        
        String PATH_PackageParser = "android.content.pm.PackageParser";  
        String PATH_AssetManager = "android.content.res.AssetManager";  
        try {  
            //����õ�pkgParserCls����ʵ����,�в���  
            Class<?> pkgParserCls = Class.forName(PATH_PackageParser);  
            Class<?>[] typeArgs = {String.class};  
            Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);  
            Object[] valueArgs = {apkPath};  
            Object pkgParser = pkgParserCt.newInstance(valueArgs);  
              
            //��pkgParserCls��õ�parsePackage����  
            DisplayMetrics metrics = new DisplayMetrics();  
            metrics.setToDefaults();//���������ʾ�йص�, ���ʹ��Ĭ��  
            typeArgs = new Class<?>[]{File.class,String.class,  
                                    DisplayMetrics.class,int.class};  
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(  
                    "parsePackage", typeArgs);  
              
            valueArgs=new Object[]{new File(apkPath),apkPath,metrics,0};  
              
            //ִ��pkgParser_parsePackageMtd����������  
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,  
                    valueArgs);  
              
            //�ӷ��صĶ���õ���Ϊ"applicationInfo"���ֶζ���    
            if (pkgParserPkg==null) {  
                return ;  
            }  
            Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(  
                    "applicationInfo");  
              
            //�Ӷ���"pkgParserPkg"�õ��ֶ�"appInfoFld"��ֵ  
            if (appInfoFld.get(pkgParserPkg)==null) {  
                return ;  
            }  
            ApplicationInfo info = (ApplicationInfo) appInfoFld  
                    .get(pkgParserPkg);           
              
            //����õ�assetMagCls����ʵ����,�޲�  
            Class<?> assetMagCls = Class.forName(PATH_AssetManager);            
            Object assetMag = assetMagCls.newInstance();  
            //��assetMagCls��õ�addAssetPath����  
            typeArgs = new Class[1];  
            typeArgs[0] = String.class;  
            Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(  
                    "addAssetPath", typeArgs);  
            valueArgs = new Object[1];  
            valueArgs[0] = apkPath;  
            //ִ��assetMag_addAssetPathMtd����  
            assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);  
              
              
            //�õ�Resources����ʵ����,�в���  
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
              
              
            // ��ȡapk�ļ�����Ϣ   
            if (info!=null) {  
                if (info.icon != 0) {// ͼƬ���ڣ����ȡ�����Ϣ  
                    Drawable icon = res.getDrawable(info.icon);// ͼ��  
                    appInfoData.setIcon(icon);  
                    }  
            }else {  
                return ;  
            }             
            return ;  
        } catch (Exception e) {   
            e.printStackTrace();  
        }  
        return ;  
    }  

    /** 
     * ��ȡδ��װ��apk��Ϣ 
     *  
     * @param ctx 
     * @param apkPath 
     * @return 
     */  
    public static void setAppFileIcon(Context ctx, String iconPath,AppInfoData appInfoData) {  

    	File iconFile = new File(iconPath);
        
        if (!iconFile.exists() || !iconPath.toLowerCase().endsWith(".png")) {  
            return ;  
        }  
        
        Drawable icon;
        
        icon = BitmapDrawable.createFromPath(iconPath);
        Log.i(TAG, iconPath);
        
        appInfoData.setIcon(icon);

        return ;  
    }  



}
