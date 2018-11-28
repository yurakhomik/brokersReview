package com.example.brokers.broker;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brokers")
public class BrokerController {

    @Autowired
    private BrokerService brokerService;

    @GetMapping
    public List<Broker> getAllBrokers() {
        return brokerService.getAllBrokers();
    }

    @GetMapping(path="/{id}")
    public Broker retrieveBroker (@PathVariable Long id) throws NotFoundException{
        return brokerService.retrieveBroker(id);
    }

    @DeleteMapping(path="/{id}")
    public void deleteBroker (@PathVariable Long id)throws NotFoundException {
        brokerService.deleteBroker(id);
    }

    @PutMapping
    public void updateBroker (@RequestBody Broker broker) throws NotFoundException{
        brokerService.updateBroker(broker);
    }

    @PostMapping
    public void createBroker(@RequestBody Broker broker) {
        brokerService.createBroker(broker);
    }

}

