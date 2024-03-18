package com.marondal.memo.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marondal.memo.common.FileManager;
import com.marondal.memo.post.domain.Post;
import com.marondal.memo.post.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public Post addPost(int userId, String title, String contents, MultipartFile file) {
		
		// file을 특정 경로에 저장한다. 
		// 클라이언트에서 접근가능한 url 정보를 얻는다. 
		String imagePath = FileManager.saveFile(userId, file);
		
		Post post = Post.builder()
					.userId(userId)
					.title(title)
					.contents(contents)
					.imagePath(imagePath)
					.build();
		
		
		return postRepository.save(post);
		
	}
	
	// 특정 사용자의 메모 목록을 돌려준다
	public List<Post> getPostList(int userId) {
		
		return postRepository.findByUserIdOrderByIdDesc(userId);
	}
	
	public Post getPost(int id) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		Post post = optionalPost.orElse(null);
		return post;
		
	}
	
	public Post updatePost(int id, String title, String contents) {
		// 수정 대상 데이터 조회
		// 조회된 객체에서 필요한 값 수정
		// 해당 객체를 저장
		Optional<Post> optionalPost = postRepository.findById(id);
		Post post = optionalPost.orElse(null);
		
		if(post != null) {
			post = post.toBuilder()
				.title(title)
				.contents(contents)
				.build();
			
			post = postRepository.save(post);
		}
		
		return post;
	}
	
	public Post deletePost(int id) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		Post post = optionalPost.orElse(null);
		
		if(post != null) {
			FileManager.removeFile(post.getImagePath());
			postRepository.delete(post);
		}
		
		return post;
		
	}

}
