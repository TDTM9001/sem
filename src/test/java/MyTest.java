import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import locations.*;

class MyTest
{
    @Test
    void unitTest10()
    {
        assertThrows(NullPointerException.class, this::throwsException);
    }

    void throwsException() throws NullPointerException
    {
        throw new NullPointerException();
    }
}