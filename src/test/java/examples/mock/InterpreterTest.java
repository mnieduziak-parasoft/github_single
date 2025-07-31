package examples.mock;

/*
 * InterpreterTest.java Created by Jtest on 7/3/14 2:47:55 PM.
 */
import org.junit.Test;
import org.mockito.MockedConstruction;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockConstruction;


import java.io.DataInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * InterpreterTest is a test class for Interpreter
 *
 * @see Interpreter
 * @author Parasoft Jtest 10.0
 */
public class InterpreterTest
{
    @Test(timeout = 10000)
    public void testAdd34() throws Throwable
    {
        try (MockedConstruction<DataInputStream> mocked = mockConstruction(DataInputStream.class,
                (mock, context) -> {
                    when(mock.readUTF()).thenReturn("ADD");
                    when(mock.readInt()).thenReturn(3, 4);
                }
        )){
            Interpreter itp = new Interpreter(null);
            try {
                int value = itp.getNext().intValue();
                assertTrue(value == 7, "value is:" + value); // failing due to BUG inside getNext method logic
            } catch (IOException e) {
                fail(e.toString());
            }
        }
    }
}
