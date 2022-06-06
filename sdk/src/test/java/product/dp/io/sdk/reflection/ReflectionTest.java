package product.dp.io.sdk.reflection;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import product.dp.io.sdk.reflection.sample.ReflectionObject;

public class ReflectionTest {

    @Test
    public void sampleJavaPersonReflectionTest() {

        try {
            Reflection reflection = new ReflectionObject(
                    "product.dp.io.sdk.reflection.sample.JavaPerson");

            reflection.createObject(null, null, true);
            assertTrue(reflection.setField("name", "NewName"));
            assertEquals("NewName", reflection.getField("name"));

            assertEquals("staticMethod",
                    reflection.invokeStaticMethod("staticMethod", null, null));
        } catch (ReflectionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void sampleKotlinPersonReflectionTest() {
        try {
            Reflection reflection = new ReflectionObject(
                    "product.dp.io.sdk.reflection.sample.KotlinPerson");

            reflection.createObject(
                    new Class[]{String.class, int.class},
                    new Object[]{"Testing", 123},
                    true);

            assertEquals("printAllProps", reflection.invokeMethod("printAllProps", null, null));
            assertTrue(reflection.setField("name", "NewName"));
            assertEquals("NewName", reflection.getField("name"));

        } catch (ReflectionException e) {
            fail(e.getMessage());
        }
    }
}