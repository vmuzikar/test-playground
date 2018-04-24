package org.test;

import org.junit.Test;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.GenericContainer;

import static org.junit.Assert.assertEquals;

/**
 * @author Vaclav Muzikar <vmuzikar@redhat.com>
 */
public class DockerTest {
    @Test
    public void cmdOutputTest() throws Exception {
        final String testStr = "Hello World!";
        final String dockerIoPrefix = System.getProperty("docker.io") != null ? "docker.io/" : "";

        GenericContainer container = new GenericContainer(dockerIoPrefix + "centos:latest")
                .withCommand("sleep infinity");
        container.start();

        Container.ExecResult result = container.execInContainer("echo", testStr);

        System.out.printf("STDOUT: %s\n---\nSTDERR: %s", result.getStdout(), result.getStderr());

        assertEquals(testStr + "\n", result.getStdout());
    }
}
