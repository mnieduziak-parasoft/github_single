package examples.mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.MockedStatic;

/**
 * FileExampleTest is a test class for FileExample
 *
 * @see FileExample
 * @author Parasoft Jtest 10.0
 */
public class FileExampleTest
{
	@Test
    public void testAnalyze()
    {
        File file = mock(File.class);

        when(file.getAbsolutePath()).thenReturn("Z:\\tmp\\X001.txt");
        when(file.setLastModified(anyLong())).thenReturn(true);
        when(file.compareTo((File)argThat(new IsFileNameCorrect("X001.txt")))).thenReturn(10);
        when(file.compareTo((File)argThat(new IsFileNameCorrect("XXX")))).thenReturn(0);

        String result = FileExample.analyze(file);
        assertEquals("Z:\\tmp\\X001.txt:true:10:0:", result);
    }

	class IsFileNameCorrect implements ArgumentMatcher<File> 
	{
		private final String nameToCompare;

		public IsFileNameCorrect(String fileName) 
		{
			nameToCompare = fileName;
		}
		@Override
		public boolean matches(File file) {
            return (file == null) ? false : nameToCompare.equals(((File)file).getName());

		}
    }
	
	@Test
	public void testIsOversize()
	{
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class);
        	 MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)) 
        {
        	mockedPaths.when(() -> Paths.get(null)).thenReturn(null);
        	mockedFiles.when(() -> Files.size(null)).thenReturn(99L);
            assertTrue(FileExample.isOversize(null,  100));
        }
	}
}
