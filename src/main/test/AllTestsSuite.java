package main.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    ConstructorTests.class,
    AddStockTests.class,
    ReservationTests.class,
    ShippingTests.class,
    DamagedStockTests.class,
    ReorderAndUpdateTests.class
})
class AllTestsSuite { }
