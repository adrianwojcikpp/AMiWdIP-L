package iot.examples.datagrabber;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestableClassUnitTest {

    private TestableClass responseHandling = new TestableClass();
    private String correctResponse = "{\"data\":3.14}";
    private String incorrectResponse = "{\"data\":3.14";
    private String integerResponse = "{\"data\":3}";

    @Test
    public void JsonStringToDouble_isCorrect() {
        assertEquals( responseHandling.getRawDataFromResponse(correctResponse), 3.14, 0.0);
    }

    @Test
    public void JsonStringToDouble_isNanIfIncorrect() {
        assertEquals( responseHandling.getRawDataFromResponse(incorrectResponse), Double.NaN, 0.0);
    }

    @Test
    public void JsonStringToDouble_isCorrectIfInteger() {
        assertEquals( responseHandling.getRawDataFromResponse(integerResponse), 3, 0);
    }
}