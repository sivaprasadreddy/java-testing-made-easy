package com.sivalabs.jtme;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

class ConditionalTestExecutionDemoTest {

    @Test
    @EnabledOnOs(OS.MAC)
    void shouldRunOnlyOnMacOS() {
        System.out.println("This is a test running on MacOS");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void shouldRunOnlyOnWindows() {
        System.out.println("This is a test running on Windows");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void shouldRunOnlyOnJre17() {
        System.out.println("This is a test running on Java 17");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_21)
    void shouldRunOnlyOnJre21() {
        System.out.println("This is a test running on Java 21");
    }

}
