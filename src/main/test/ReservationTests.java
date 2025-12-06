package main.test;

import main.java.ProductStock;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationTests {

    private ProductStock stock;

    @BeforeAll static void beforeAll() { System.out.println("=== Starting ReservationTests ==="); }
    @AfterAll static void afterAll() { System.out.println("=== Finished ReservationTests ==="); }
    @BeforeEach void setUp() { stock = new ProductStock("P1", "L1", 20, 5, 50); }
    @AfterEach void tearDown() { System.out.println("Test finished."); }

    @Test @Tag("sanity") void reserveStock() {
        stock.reserve(5);
        assertEquals(5, stock.getReserved());
        assertEquals(15, stock.getAvailable());
    }

    @Test @Tag("regression") void reserveTooMuch() {
        assertThrows(IllegalStateException.class, () -> stock.reserve(25));
    }

    @Test @Tag("sanity") void releaseReservation() {
        stock.reserve(10);
        stock.releaseReservation(5);
        assertEquals(5, stock.getReserved());
        assertEquals(15, stock.getAvailable());
    }

    @Test @Tag("regression") void releaseTooMuch() {
        stock.reserve(5);
        assertThrows(IllegalStateException.class, () -> stock.releaseReservation(10));
    }

    // branch coverage for amount <=0
    @Test void reserveZeroShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> stock.reserve(0));
    }
    @Test void reserveNegativeShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> stock.reserve(-5));
    }
    @Test void releaseZeroShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> stock.releaseReservation(0));
    }
    @Test void releaseNegativeShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> stock.releaseReservation(-2));
    }
    @Test void reserveAllAvailable() {
        stock.reserve(20); // available=20, reserve all
        assertEquals(20, stock.getReserved());
        assertEquals(0, stock.getAvailable());
    }

    @Test void releaseAllReserved() {
        stock.reserve(15);
        stock.releaseReservation(15);
        assertEquals(0, stock.getReserved());
        assertEquals(20, stock.getAvailable());
    }

    @Test void reserveUpToMaxCapacity() {
        ProductStock s = new ProductStock("P2", "L2", 50, 5, 50);
        s.reserve(50);
        assertEquals(50, s.getReserved());
        assertEquals(0, s.getAvailable());
    }

}
