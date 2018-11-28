package com.example.brokers.comment;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping(path = "/brokerComments/{brokerId}")
    public List<Comment> getAllCommentsForBroker(@PathVariable Long brokerId) {
        return commentService.getAllCommentsForBroker(brokerId);
    }

    @PutMapping
    public void updateComment(@RequestBody Comment comment) throws NotFoundException {
        commentService.updateComment(comment);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteComment(@PathVariable Long id) throws NotFoundException {
        commentService.deleteComment(id);
    }

    @PostMapping
    public void createComment(@RequestBody Comment comment) throws NotFoundException {
        commentService.createComment(comment);
    }
}
