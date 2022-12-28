package com.medium.driver;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.ExecutionContext;

public class BaseTest extends DriverManager {
    private final DriverManager driverManager;

    public BaseTest() {
        driverManager = new DriverManager();
    }

    @BeforeScenario
    public void setup(ExecutionContext context) {
        driverManager.initializeDriver(context);
    }

    @AfterScenario
    public void tearDown() {
        driverManager.closeDriver();
    }
}
