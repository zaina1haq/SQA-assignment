package main.test;

import main.java.ProductStock;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Constructor Test Suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConstructorTests {

    @BeforeAll
    static void setupAll() {
        System.out.println(">>> Starting ConstructorTests suite");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println(">>> ConstructorTests suite finished");
    }

    @Nested
    @DisplayName("Valid Construction Scenarios")
    class ValidConstruction {

        @Test
        @DisplayName("Valid construction")
        @Tag("sanity")
        @Timeout(1)
        void testValidConstructor() {
            ProductStock ps = new ProductStock("X1", "L1", 5, 2, 20);
            assertAll("Constructor assertions",
                    () -> assertEquals("X1", ps.getProductId()),
                    () -> assertEquals("L1", ps.getLocation()),
                    () -> assertEquals(5, ps.getOnHand()),
                    () -> assertEquals(2, ps.getReorderThreshold()),
                    () -> assertEquals(20, ps.getMaxCapacity())
            );
        }
    }

    @Nested
    @DisplayName("Invalid Construction Scenarios")
    class InvalidConstruction {

        @Test
        @DisplayName("Constructor throws exception on invalid inputs")
        @Tag("regression")
        @Timeout(1)
        void testInvalidConstructor() {
            assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new ProductStock("", "L1", 10, 5, 20)),
                () -> assertThrows(IllegalArgumentException.class, () -> new ProductStock("P1", "", 10, 5, 20)),
                () -> assertThrows(IllegalArgumentException.class, () -> new ProductStock("P1", "L1", -1, 5, 20)),
                () -> assertThrows(IllegalArgumentException.class, () -> new ProductStock("P1", "L1", 10, -5, 20))
            );
        }
    }
}