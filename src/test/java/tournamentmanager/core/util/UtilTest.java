package tournamentmanager.core.util;

import org.junit.jupiter.api.Test;
import tournamentmanager.util.Util;
import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {
    Util u = new Util();

    @Test
    void checkFalseValue() {
        assertFalse(u.isPowerOfTwo(3));
    }

    @Test
    void checkTrueValue() {
        assertTrue(u.isPowerOfTwo(8));
    }

    @Test
    void checkNegativeValueIsFalse() {
        assertFalse(u.isPowerOfTwo(-8));
    }

    @Test
    void testNullValue() {
        assertTrue(u.isPowerOfTwo(0));
    }

}
