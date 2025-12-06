package main.test;

import main.java.ProductStock;
import org.junit.jupiter.api.*;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReorderAndUpdateTests {

    private ProductStock stock;

    @BeforeAll static void beforeAll() { System.out.println("=== Starting ReorderAndUpdateTests ==="); }
    @AfterAll static void afterAll() { System.out.println("=== Finished ReorderAndUpdateTests ==="); }
    @BeforeEach void setUp() { stock = new ProductStock("P1", "L1", 20, 5, 50); }
    @AfterEach void tearDown() { System.out.println("Test finished."); }

    @Test @Tag("sanity") @DisplayName("isReorderNeeded returns true if available < threshold")
    void reorderNeeded() {
        stock.reserve(16);
        assertTrue(stock.isReorderNeeded());
    }

    @Test @Tag("sanity") @DisplayName("isReorderNeeded returns false if available >= threshold")
    void reorderNotNeeded() {
        assertFalse(stock.isReorderNeeded());
    }

    @Test @Tag("regression") @DisplayName("Update reorder threshold")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void updateReorderThreshold() {
        stock.updateReorderThreshold(10);
        assertEquals(10, stock.getReorderThreshold());
        assertThrows(IllegalArgumentException.class, () -> stock.updateReorderThreshold(-1));
        assertThrows(IllegalArgumentException.class, () -> stock.updateReorderThreshold(1000));
    }

    @Test @Tag("regression") @DisplayName("Update max capacity")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void updateMaxCapacity() {
        stock.updateMaxCapacity(100);
        assertEquals(100, stock.getMaxCapacity());
        assertThrows(IllegalStateException.class, () -> stock.updateMaxCapacity(10));
        assertThrows(IllegalArgumentException.class, () -> stock.updateMaxCapacity(0));
    }

}
