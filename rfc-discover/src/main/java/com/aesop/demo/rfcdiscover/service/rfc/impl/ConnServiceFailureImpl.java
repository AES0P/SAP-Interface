package com.aesop.demo.rfcdiscover.service.rfc.impl;

import com.aesop.demo.rfcdiscover.service.rfc.ConnService;
import org.springframework.stereotype.Service;

@Service
public class ConnServiceFailureImpl implements ConnService {

    @Override
    public String sayHi(String name) {
        return "hey " +
                name + ", there is some problem with hi page";
    }

    @Override
    public String showJCoDestinationState() {
        return "There may be something wrong with JCO destination. ";
    }

    @Override
    public String switchJCoServerState(int switchCode) {
        return "There may be something wrong with JCo server.";
    }

    @Override
    public String switchJCoIDocServerState(int switchCode) {
        return "There may be something wrong with JCo IDoc server.";
    }


}
