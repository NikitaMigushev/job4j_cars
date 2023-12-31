package ru.job4j.cars.service;

import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostService {
    Optional<Post> save(Post post, PhotoDto photo);
    Optional<Post> save(PostDto postDto, PhotoDto photo);
    boolean update(Post post);
    boolean update(PostDto postDto);
    boolean deleteById(int id);
    Optional<Post> findById(int id);
    Collection<Post> findAll();
    Collection<Post> getLastDayPosts();
    Collection<Post> getPostsWithPhoto();
    Collection<Post> getPostsByBrand(String brand);
    boolean update(Post post, PhotoDto photo);
    boolean markSold(Post post);
}
