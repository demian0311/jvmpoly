package javafunctional.java8.fpij;

import org.junit.Test;

import java.util.function.Consumer;

class ResourceController{
    private String resourceName;

    /**
     * Consumers of this capability will never instantiate it on their own.
     * They use the *use* method to send in a block of code to run against
     * a resource.
     */
    private ResourceController(String resourceNameIn) {
        resourceName = resourceNameIn;
        System.out.println(String.format("%s created", resourceName));
    }

    public void doStuff(String information) {
        System.out.println(String.format("%s << %s", resourceName, information));
    }

    private void close() {
        System.out.println(String.format("%s clean up", resourceName));
    }

    public static void use(
            String resourceName,
            final Consumer<ResourceController> block) {
        final ResourceController resourceController = new ResourceController(resourceName);
        try{
            block.accept(resourceController);
        } finally{
            resourceController.close();
        }
    }
}

/**
 * Less elaborate than what Venkhat was doing.
 */
public class Chapter5 {
    @Test public void testResourceController() {
        ResourceController.use(
                "printer02",
                resourceController -> resourceController.doStuff("foo")
        );
    }
}
