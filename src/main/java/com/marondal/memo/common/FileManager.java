package com.marondal.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManager {
	
	// 상수
	public final static String FILE_UPLOAD_PATH = "D:\\dulumaryT\\web\\2024ty\\upload\\memo";
	
	public static String saveFile(int userId, MultipartFile file) {
		
		if(file == null) {
			return null;
		}
		
		// 같은 이름의 파일 처리 
		// 폴더(디렉토리)만들어서 파일 저장
		// 로그인사용자의 userId를 폴더 이름 
		// 현재시간 정보 를 폴더 이름에 포함
		// UNIX TIME : 1970년 1월 1일 부터 흐린시간을 milli second (1/1000) 로 표현한 시간
		// ex ) 2_32908531908
		
		String directoryName = "/" + userId + "_" + System.currentTimeMillis();
		
		// 폴더 생성
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		File directory = new File(directoryPath);
		
		if(!directory.mkdir()) {
			// 디렉토리 생성 실패
			return null;
		}
		
		// 파일 저장
		String filePath = directoryPath + "/" + file.getOriginalFilename();
		
		
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath);
			Files.write(path, bytes);
			
		} catch (IOException e) {
		
			e.printStackTrace();
			// 파일 저장 실패 
			return null;
		}
		
		// 클라이언드에서 접근가능한 경로 - 데이터베이스에 저장
		// 클라이언트에서 접근가능한 경로 리턴
		
		// 파일 경로 : D:\\dulumaryT\\web\\20231026\\springProject\\upload\\memo/2_32908531908/test.png
		// url 경로 규칙 : /images/2_32908531908/test.png
		
		return "/images" + directoryName + "/" + file.getOriginalFilename();
		
	}
	
	public static boolean removeFile(String filePath) { //  /images/2_1710241687236/coffee-7121939_640.jpg

		if(filePath == null) {
			return false;
		}
		
		// 삭제대상 파일 경로 
		String fullFilePath = FILE_UPLOAD_PATH + filePath.replace("/images", "");
		
		Path path = Paths.get(fullFilePath);
		
		// 파일이 존재하는지 
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		// 디렉토리 삭제
		Path dirPath = path.getParent();
		
		if(Files.exists(dirPath)) {
			try {
				Files.delete(dirPath);
			} catch (IOException e) {
				
				e.printStackTrace();
				
				return false;
			}
		}
		
		return true;
		
	}
}
