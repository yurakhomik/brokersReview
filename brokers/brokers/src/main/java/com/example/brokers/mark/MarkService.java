package com.example.brokers.mark;

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
public class MarkService {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private UserService userService;

    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }

    public List<Mark> getAllMarksForBroker(Long brokerId) {
        return markRepository.findByBrokerId(brokerId);
    }

    public void createMark(Mark mark) throws NotFoundException {
        if (mark.getBrokerId() == null) {
            throw new InvalidParameterException("Broker Id can't be null");
        }

        Optional<User> user = userService.findByEmail(UserService.getCurrentUser());
        mark.setUserFullName(user.get().getFirstName() + " " + user.get().getSecondName());
        mark.setUserId(user.get().getId());

        Optional<Mark> optionalComment = markRepository.findByUserIdAndBrokerId(mark.getUserId(), mark.getBrokerId());
        if (optionalComment.isPresent()) {
            throw new InvalidParameterException("You already create mark");
        }
        markRepository.save(mark);
    }

    public void updateMark(Mark mark) throws NotFoundException {
        Long markId = mark.getId();
        if(markId == null) {
            throw new InvalidParameterException("mark Id can't be null");
        }

        Optional<Mark> optionalMark = markRepository.findById(markId);
        if (!optionalMark.isPresent()) {
            throw new NotFoundException("Mark id:" + markId + " doesn't found");
        }

        Optional<User> user = userService.findByEmail(UserService.getCurrentUser());
        mark.setUserId(optionalMark.get().getUserId());
        mark.setUserFullName(optionalMark.get().getUserFullName());

        if (user.get().getId() == optionalMark.get().getUserId() || user.get().getRole().equals("ADMIN")) {
            markRepository.save(mark);
        } else {
            throw new AccessDeniedException("Denied access!");
        }
    }

    public void deleteMark(Long id) throws NotFoundException {
        Optional<Mark> optionalMark = markRepository.findById(id);
        if (!optionalMark.isPresent()) {
            throw new NotFoundException("Mark id:" + id + " doesn't found");
        }

        Optional<User> user = userService.findByEmail(userService.getCurrentUser());
        if (user.get().getId() == optionalMark.get().getUserId() || user.get().getRole().equals("ADMIN")) {
            markRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("Denied access!");
        }
    }


}

