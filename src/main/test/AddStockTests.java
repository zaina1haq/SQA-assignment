package main.test;

import main.java.ProductStock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddStockTests {

    private ProductStock stock;

    @BeforeAll
    static void beforeAll() {
        System.out.println("=== Starting AddStockTests ===");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("=== Finished AddStockTests ===");
    }

    @BeforeEach
    void setUp() {
        stock = new ProductStock("P1", "L1", 20, 5, 50);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished.");
    }

    @Test
    @Tag("sanity")
    @DisplayName("Adding normal stock increases onHand")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void addStockNormal() {
        stock.addStock(10);
        assertEquals(30, stock.getOnHand());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    @Tag("regression")
    @DisplayName("Adding stock within capacity succeeds")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void addStockParameterized(int amount) {
        stock.addStock(amount);
        assertTrue(stock.getOnHand() <= stock.getMaxCapacity());
    }

    @Test
    @Tag("regression")
    @DisplayName("Adding stock exceeding maxCapacity should fail")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void addStockExceedCapacity() {
        assertThrows(IllegalStateException.class, () -> stock.addStock(40));
    }

    @Test
    @Tag("regression")
    @DisplayName("Adding zero or negative stock should fail")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void addStockNegative() {
        assertThrows(IllegalArgumentException.class, () -> stock.addStock(0));
        assertThrows(IllegalArgumentException.class, () -> stock.addStock(-5));
    }

    // branch coverage: future feature placeholder
    @Disabled("Future feature: adding stock automatically")
    @Test
    void futureFeatureTest() { }

}
