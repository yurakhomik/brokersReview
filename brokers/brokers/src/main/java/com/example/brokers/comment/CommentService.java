package com.example.brokers.comment;

import com.example.brokers.user.User;
import com.example.brokers.user.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getAllCommentsForBroker(Long brokerId) {
        return commentRepository.findByBrokerId(brokerId);
    }

    public void createComment(Comment comment) throws NotFoundException {
        Optional<User> user = userService.findByEmail(UserService.getCurrentUser());
        comment.setUserFullName(user.get().getFirstName() + " " + user.get().getSecondName());
        comment.setUserId(user.get().getId());

        if (comment.getBrokerId() == null || comment.getFeedback() == null || comment.getFeedback().length() == 0) {
            throw new InvalidParameterException("Incorrect comment");
        }
        commentRepository.save(comment);

    }

    public void updateComment(Comment comment) throws NotFoundException {
        if (comment.getId() == null || comment.getBrokerId() == null || comment.getFeedback() == null ||
                comment.getFeedback().length() == 0) {
            throw new InvalidParameterException("Incorrect comment for update");
        }

        Optional<User> user = userService.findByEmail(UserService.getCurrentUser());
        Optional<Comment> existComment = commentRepository.findById(comment.getId());

        if (!existComment.isPresent()) {
            throw new InvalidParameterException("Incorrect id" + comment.getId());
        }

        Comment exist = existComment.get();
        if (!user.get().getId().equals(exist.getUserId()) && !user.get().getRole().equals("ADMIN")) {
            throw new AccessDeniedException("Denied access!!!");
        }

        comment.setUserId(comment.getUserId());
        comment.setUserFullName(existComment.get().getUserFullName());

        commentRepository.save(comment);
    }

    public void deleteComment(Long id) throws NotFoundException {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (!commentOptional.isPresent()) {
            throw new NotFoundException("Comment id:" + id + " doesn't found");
        }

        Optional<User> user = userService.findByEmail(UserService.getCurrentUser());
        if (!user.get().getId().equals(commentOptional.get().getUserId()) && !user.get().getRole().equals("ADMIN")) {
            throw new AccessDeniedException("Denied access!");
        }
        commentRepository.deleteById(id);
    }

}
