package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimplePostService implements PostService {
    private final PostRepository postRepository;

    @Override
    public Optional<Post> save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public boolean deleteById(int id) {
        return postRepository.deleteById(id);
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public Collection<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Collection<Post> getLastDayPosts() {
        return postRepository.getLastDayPosts();
    }

    @Override
    public Collection<Post> getPostsWithPhoto() {
        return postRepository.getPostsWithPhoto();
    }

    @Override
    public Collection<Post> getPostsByBrand(String brand) {
        return postRepository.getPostsByBrand(brand);
    }
}
