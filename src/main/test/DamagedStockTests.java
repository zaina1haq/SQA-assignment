package main.test;

import main.java.ProductStock;
import org.junit.jupiter.api.*;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DamagedStockTests {

    private ProductStock stock;

    @BeforeAll
    static void beforeAll() { System.out.println("=== Starting DamagedStockTests ==="); }

    @AfterAll
    static void afterAll() { System.out.println("=== Finished DamagedStockTests ==="); }

    @BeforeEach
    void setUp() { stock = new ProductStock("P1", "L1", 20, 5, 50); }

    @AfterEach
    void tearDown() { System.out.println("Test finished."); }

    @Test @Tag("sanity")
    @DisplayName("Remove damaged stock reduces onHand")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void removeDamaged() {
        stock.removeDamaged(5);
        assertEquals(15, stock.getOnHand());
    }

    @Test @Tag("regression")
    @DisplayName("Remove more damaged than onHand should fail")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void removeTooMuchDamaged() {
        assertThrows(IllegalStateException.class, () -> stock.removeDamaged(30));
    }

    // branch coverage for amount <=0
    @Test void removeDamagedZeroShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> stock.removeDamaged(0));
    }
    @Test void removeDamagedNegativeShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> stock.removeDamaged(-3));
    }

}
