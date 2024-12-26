package com.website.idea.services;
import com.website.idea.models.Posts.Post;
import com.website.idea.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пост не найден с id: " + id));
        post.setTitle(postDetails.getTitle());
        post.setTags(postDetails.getTags());
        post.setDescription(postDetails.getDescription());
        post.setAuthorId(postDetails.getAuthorId());
        post.setQuantityLikes(postDetails.getQuantityLikes());
        post.setFiles(postDetails.getFiles());
        post.setDate(postDetails.getDate());
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пост не найден с id: " + id));
        postRepository.delete(post);
    }
}
