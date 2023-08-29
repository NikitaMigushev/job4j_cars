package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    private static final Logger logger = LoggerFactory.getLogger(HibernateBrandRepository.class);
    private final CrudRepository crudRepository;

    @Override
    public Optional<Post> save(Post post) {
        try {
            crudRepository.run(session -> session.save(post));
            return Optional.of(post);
        } catch (Exception e) {
            logError("Failed to save Post: " + post, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Post post) {
        try {
            crudRepository.run(session -> session.merge(post));
            return true;
        } catch (Exception e) {
            logError("Failed to save update: " + post, e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from Post where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Post> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT p FROM Post p WHERE p.id = :fId",
                    Post.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<Post> findAll() {
        try {
            return crudRepository.query(
                    "SELECT p FROM Post p",
                    Post.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Post> getLastDayPosts() {
        try {
            LocalDateTime yesterday = LocalDate.now().minusDays(1).atStartOfDay();
            LocalDateTime today = LocalDate.now().atStartOfDay();
            String query = "SELECT p FROM Post p WHERE p.created > :yesterday AND p.created < :today";
            Map<String, Object> params = new HashMap<>();
            params.put("yesterday", yesterday);
            params.put("today", today);
            return crudRepository.query(query, Post.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Post> getPostsWithPhoto() {
        try {
            String query = "SELECT p FROM Post p LEFT JOIN FETCH p.photo WHERE p.photo.id IS NOT NULL";
            return crudRepository.query(query, Post.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Post> getPostsByBrand(String brand) {
        try {
            String query = "SELECT p FROM Post p LEFT JOIN FETCH p.car c WHERE c.brand.name = :brand";
            Map<String, Object> params = new HashMap<>();
            params.put("brand", brand);
            return crudRepository.query(query, Post.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean markSold(Post post) {
        try {
            post.setSold(true);
            crudRepository.run(session -> session.update(post));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void logError(String message, Throwable e) {
        logger.error(message, e);
    }
}
