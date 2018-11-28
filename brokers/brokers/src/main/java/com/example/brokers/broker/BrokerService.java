package com.example.brokers.broker;

import javassist.NotFoundException;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class BrokerService {

    @Autowired
    private BrokerRepository brokerRepository;

    public List<Broker> getAllBrokers() {
        return brokerRepository.findAll();
    }

    public Broker retrieveBroker(Long id) throws NotFoundException {
        Optional<Broker> broker = brokerRepository.findById(id);
        if (!broker.isPresent()) {
            throw new NotFoundException("Broker id:" + id + " doesn't found");
        }
        return broker.get();
    }

    public void deleteBroker(Long id) throws NotFoundException {
        Optional<Broker> broker = brokerRepository.findById(id);
        if (!broker.isPresent()) {
            throw new NotFoundException("Broker id:" + id + " doesn't found");
        }
        brokerRepository.deleteById(id);
    }

    public void updateBroker(Broker broker) throws NotFoundException {
        Long brokerId = broker.getId();
        if (brokerId == null) {
            throw new InvalidParameterException("Broker id can't be null");
        }
        Optional<Broker> brokerOptional = brokerRepository.findById(brokerId);
        if (!brokerOptional.isPresent()) {
            throw new NotFoundException("Broker id:" + brokerId + " doesn't found");
        }
        brokerRepository.save(broker);
    }

    public void createBroker(Broker broker) {
        brokerRepository.save(broker);
    }
}
