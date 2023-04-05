package cz.vmuzikar;

import io.fabric8.kubernetes.client.KubernetesClientBuilder;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Vaclav Muzikar <vmuzikar@redhat.com>
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final String namespace = "keycloak-test-db2cd0cb-c666-47ef-afd8-a1db3cf7c16e";

        var client = new KubernetesClientBuilder().build();
        var pods = client.pods().inNamespace(namespace).list().getItems().stream()
                        .map(p -> p.getMetadata().getName())
                        .collect(Collectors.toList());

        System.out.println(pods);

        var pf = client.pods().inNamespace(namespace).withName(pods.get(0)).portForward(8443, 8443);

        while (!new Scanner(System.in).hasNextLine()) {}

        pf.close();
        client.close();
    }
}
