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
package gls.syntax

import gls.CompilableTestSupport

/**
 * 
 * @author Guillaume Laforge
 */
class BinaryLiteralTest extends CompilableTestSupport {

    void testSomeBinaryLiteralNumbers() {
        assertScript '''
            assert  0b0   ==  0
            assert  0b000 ==  0
            assert  0b1   ==  1
            assert  0b11  ==  3
            assert -0b11  == -3

            // uppercase B letter works too
            assert 0B101  == 0b101

            int x = 0b10101111
            assert x == 175

            byte aByte = (byte)0b00100001
            assert aByte == 33

            int anInt = (int)0b1010000101000101
            assert anInt == 41285

        '''
    }

}
