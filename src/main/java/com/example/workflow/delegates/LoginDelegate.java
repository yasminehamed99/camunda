package com.example.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;
@Component
public class LoginDelegate implements JavaDelegate {
    private final Logger logger = Logger.getLogger(LoginDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("loggedIn",true);

    }
}
