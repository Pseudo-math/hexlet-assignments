package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> getPosts() {
        return posts;
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPostById(@PathVariable String id) {
        var post = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findAny();
        return post;
    }

    @GetMapping("/page")
    public List<Post> getPage(@RequestParam(defaultValue = "1") Integer pageId, @RequestParam Integer postsCount) {
        return posts.subList((pageId - 1) * postsCount, pageId * postsCount);
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@RequestBody Post data, @PathVariable String id) {
        var maybePost = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findAny();
        if (maybePost.isPresent()) {
            Post updatedPost = maybePost.get();
            updatedPost.setId(data.getId());
            updatedPost.setTitle(data.getTitle());
            updatedPost.setBody(data.getBody());
        }
        return data;
    }

    @DeleteMapping("posts/{id}")
    public void deletePost(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END
}
