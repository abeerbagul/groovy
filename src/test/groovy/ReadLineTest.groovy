// Do not remove this line: it is used in test below
/*
 * Copyright 2003-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovy

/**
 * Test to ensure that readLine() method works on Reader/InputStream
 * 
 * @author <a href="mailto:jeremy.rayner@bigfoot.com">Jeremy Rayner</a>
 * @author Joachim Baumann
 * @version $Revision$
 */

class ReadLineTest extends GroovyTestCase {
    def file
    void setUp() {
        file = new File("src/test/groovy/ReadLineTest.groovy")
    }
    void testReadOneLineFromReader() {
        def line
        file.withReader() {line = it.readLine()}
        assert line == "// Do not remove this line: it is used in test below"
    }
    
    static testString = " �\n �\n\n �\r\n 5\r\r 7\n\r 9"
    static expectedLines = [ " �", " �", "", " �", " 5", "", " 7", "", " 9" ]
    static String[] expectedLinesSlow = [ " �", " �", " �", " 5", " 7" ]
    static int[] expectedChars = [' ', '9', -1];

    void readFromReader(Reader reader) throws IOException {
        expectedLines.each { expected ->
            def line = reader.readLine()
            assertEquals("Readline should return correct line", expected, line)
        }
        assertNull("Readline should return null", reader.readLine())
    }

    public void testBufferedReader() throws IOException {        
        Reader reader = new BufferedReader(new StringReader(testString))
        readFromReader(reader)
    }

    public void testReaderSupportingMark() throws IOException {        
        Reader reader = new StringReader(testString)
        readFromReader(reader)
    }

    /*
     * In this case we cannot read more than one line separator
     * Thus empty lines can be returned if line separation is \r\n.
     */
    public void testReaderSlow() throws IOException {        
        Reader reader = new SlowStringReader(testString);
        expectedLinesSlow.each { expected ->
            String line = reader.readLine()
            while(line != null && line.length() == 0) {
                line = reader.readLine()
            } 
            assertEquals("Readline should return correct line", expected, line);    
        }
        assertEquals("Readline should return empty string", "", reader.readLine());

        expectedChars.each { expected ->
            assertEquals("Remaining characters incorrect", expected, reader.read())        
        }
        assertNull(reader.readLine());
    }
}
class SlowStringReader extends StringReader {
    public SlowStringReader(String s) { super(s); }
    public boolean markSupported() { return false }
}
