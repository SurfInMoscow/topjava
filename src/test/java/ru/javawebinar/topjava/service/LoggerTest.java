package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest implements TestRule {
    protected Logger log;

    public Logger getLog() {
        return this.log;
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                log = LoggerFactory.getLogger(description.getTestClass().getName() + '.' + description.getDisplayName());
                statement.evaluate();
            }
        };
    }
}
