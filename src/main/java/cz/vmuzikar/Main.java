package cz.vmuzikar;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import jakarta.inject.Inject;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Vaclav Muzikar <vmuzikar@redhat.com>
 */
public class Main {
    public static void main(String[] args) {
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {
        @Inject
        KubernetesClient client;

        @Override
        public int run(String... args) throws Exception {
            final String namespace = "keycloak-test-2b4c6f1a-ceb1-426e-b3c7-293a769d84fb";

//            var client = new KubernetesClientBuilder().build();
            var pods = client.pods().inNamespace(namespace).list().getItems().stream()
                    .map(p -> p.getMetadata().getName())
                    .collect(Collectors.toList());

            System.out.println(pods);

            var pf = client.pods().inNamespace(namespace).withName(pods.get(0)).portForward(8443, 8443);

            while (!new Scanner(System.in).hasNextLine()) {}

            pf.close();
            client.close();
            Quarkus.blockingExit();
            return 0;
        }
    }
}
