package com.tedu.pic.controller;

import com.tedu.pic.service.PicService;
import com.tedu.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PicController {
	@Autowired
	private PicService picService;
	//处理图片上传的路径请求
	@RequestMapping("/pic/upload")
	public PicUploadResult picUpload(MultipartFile pic){
		return picService.picUpload(pic);
	}
}
