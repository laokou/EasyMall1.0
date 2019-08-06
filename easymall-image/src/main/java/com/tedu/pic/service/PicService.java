package com.tedu.pic.service;

import java.io.File;
import java.util.UUID;

import com.tedu.utils.UploadUtil;
import com.tedu.vo.PicUploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tedu.vo.PicUploadResult;
import com.tedu.utils.UploadUtil;

@Service
public class PicService {

	public PicUploadResult picUpload(MultipartFile pic) {
		PicUploadResult result=new PicUploadResult();
		/*1 .获取后缀判断合法 .jpg .png .gif/docx,txt
		 *2 .生成路径 upload/2/3/4/d/5/d/3/2
		 * D:/img/upload/2/6/4/a/a/5/2/3
		 *image.jt.com/upload/\2\6\4\a\a\5\2\3
		 *3 .目录结构创建出来File
		 *4 .重新定义文件名称,拼接到url地址,和path生成路径
		 *5 .封装数据picUploadResult对象的url返回
		 *6 .流输出到磁盘
		 */
		//拿到原名称 penguin.jpg

		String oName=pic.getOriginalFilename();
		//截取后缀
		String extName=oName.substring
				(oName.lastIndexOf("."));
		//判断
		Boolean legal=extName.matches(".(png|jpg|gif)");
		if(!legal){//后缀非法
			result.setError(1);
			return result;
		}
		//调用一个工具类生成创建的upload其实的8位字符的路径结构
		String dir= UploadUtil.getUploadPath(oName, "upload")+"/";
		//根据原名称生成一个 3k3kdk3k 原名称不变,结果不变的
		//dir=///upload\2\6\4\a\a\5\2\3
		//拼接一个目录地址 E盘存储路径 E:\EasyMall
		String path="E://EasyMall/"+dir;//E://EasyMall//img//upload\2\6\4\a\a\5\2\3
		String urlPath="http://image.jt.com/"+dir;
				//http://image.jt.com//upload\2\6\4\a\a\5\2\3
		//重命名文件,唯一的名称uuid
		String fileName=UUID.randomUUID().toString()+extName;
		//fileName=1212-234ndlwjhfq2-324l2314.jpg
		//创建文件夹path,将图片输出到这个目录中
		File _dir=new File(path);//判断目录是否存在
		if(!_dir.exists()){
			//创建目录
			_dir.mkdirs();
		}
		//输出到目录中
		try{
			pic.transferTo(new File(path+fileName));
			//E://EasyMall//upload\2\6\4\a\a\5\2\3\1212-234ndlwjhfq2-324l2314.jpg
			result.setUrl(urlPath+fileName);
		}catch(Exception e){
			result.setError(1);
			return result;
		}
		return result;
	}

}
