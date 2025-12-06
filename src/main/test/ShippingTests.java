package main.test;

import main.java.ProductStock;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShippingTests {

    private ProductStock stock;

    @BeforeAll static void beforeAll() { System.out.println("=== Starting ShippingTests ==="); }
    @AfterAll static void afterAll() { System.out.println("=== Finished ShippingTests ==="); }
    @BeforeEach void setUp() { stock = new ProductStock("P1", "L1", 20, 5, 50); }
    @AfterEach void tearDown() { System.out.println("Test finished."); }

    @Test @Tag("regression") void shipReserved() {
        stock.reserve(10);
        stock.shipReserved(5);
        assertEquals(5, stock.getReserved());
        assertEquals(15, stock.getOnHand());
    }

    @Test @Tag("regression") void shipTooMuch() {
        stock.reserve(5);
        assertThrows(IllegalStateException.class, () -> stock.shipReserved(10));
    }

    // branch coverage for amount <=0
    @Test void shipZeroShouldThrow() {
        stock.reserve(5);
        assertThrows(IllegalArgumentException.class, () -> stock.shipReserved(0));
    }
    @Test void shipNegativeShouldThrow() {
        stock.reserve(5);
        assertThrows(IllegalArgumentException.class, () -> stock.shipReserved(-4));
    }
    @Test
    @DisplayName("Change location with null or blank should throw")
    void changeLocationInvalid() {
        assertThrows(IllegalArgumentException.class, () -> stock.changeLocation(null));
        assertThrows(IllegalArgumentException.class, () -> stock.changeLocation(""));
    }

}
