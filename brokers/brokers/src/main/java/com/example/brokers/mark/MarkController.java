package com.example.brokers.mark;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marks")
public class MarkController {

    @Autowired
    private MarkService markService;

    @GetMapping
    public List<Mark> getAllMarks () {
        return markService.getAllMarks();
    }

    @GetMapping(path = "/forBroker/{brokerId}")
    public List<Mark> getAllMarksForBroker (@PathVariable Long brokerId) {
        return markService.getAllMarksForBroker(brokerId);
    }

    @DeleteMapping(path="/{id}")
    public void deleteMark (@PathVariable Long id) throws NotFoundException {
        markService.deleteMark(id);
    }

    @PutMapping
    public void updateComment(@RequestBody Mark mark) throws NotFoundException {
        markService.updateMark(mark);
    }

    @PostMapping
    public void createMark(@RequestBody Mark mark) throws NotFoundException{
        markService.createMark(mark);
    }
}
