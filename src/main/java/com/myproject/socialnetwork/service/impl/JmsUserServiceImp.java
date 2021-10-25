package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.entity.User;
import com.myproject.socialnetwork.service.JmsUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JmsUserServiceImp implements JmsUserService {

    private static final String SIMPLE_QUEUE = "simple.queue";

    private final JmsTemplate jmsTemplate;

    @Override
    public void addOrder(User user) {
        jmsTemplate.convertAndSend(SIMPLE_QUEUE, user);
    }

}
