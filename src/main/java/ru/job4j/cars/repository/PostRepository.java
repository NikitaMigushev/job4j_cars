package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> save(Post post);
    boolean update(Post post);
    boolean deleteById(int id);
    Optional<Post> findById(int id);
    Collection<Post> findAll();
    Collection<Post> getLastDayPosts();
    Collection<Post> getPostsWithPhoto();
    Collection<Post> getPostsByBrand(String brand);

}
