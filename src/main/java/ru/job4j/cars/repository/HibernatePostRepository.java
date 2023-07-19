package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Post> save(Post post) {
        try {
            crudRepository.run(session -> session.save(post));
            return Optional.of(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Post post) {
        try {
            crudRepository.run(session -> session.merge(post));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
                    "SELECT p FROM Post p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.priceHistory LEFT JOIN FETCH p.participates WHERE p.id = :fId",
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
                    "SELECT p FROM Post p  LEFT JOIN FETCH p.user LEFT JOIN FETCH p.priceHistory LEFT JOIN FETCH p.participates",
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
            LocalDateTime yesterday = LocalDateTime.now().minusDays(1)
                    .withHour(0)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
            LocalDateTime today = LocalDateTime.now()
                    .withHour(0)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
            String query = "SELECT p FROM Post p  LEFT JOIN FETCH p.user LEFT JOIN FETCH p.priceHistory LEFT JOIN FETCH p.participates WHERE p.created > :yesterday AND p.created < :today";
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
            String query = "SELECT p FROM Post p  LEFT JOIN FETCH p.user LEFT JOIN FETCH p.priceHistory LEFT JOIN FETCH p.participates WHERE p.hasPhoto = true";
            return crudRepository.query(query, Post.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Post> getPostsByBrand(String brand) {
        try {
            String query = "SELECT p FROM Post p WHERE p.car.brand = :brand";
            Map<String, Object> params = new HashMap<>();
            params.put("brand", brand);
            return crudRepository.query(query, Post.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
