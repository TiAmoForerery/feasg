/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.converter.IOConverter;
import org.apache.camel.spi.DataFormat;

/**
 * The <a href="http://activemq.apache.org/camel/data-format.html">data format</a>
 * using Java Serialiation.
 *
 * @version $Revision: 705356 $
 */
public class SerializationDataFormat implements DataFormat {

    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws IOException {
        ObjectOutput out = IOConverter.toObjectOutput(stream);
        try {
            out.writeObject(graph);
        } finally {
            out.flush();
            try {
                out.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public Object unmarshal(Exchange exchange, InputStream stream) throws IOException, ClassNotFoundException {
        ObjectInput in = IOConverter.toObjectInput(stream);
        try {
            return in.readObject();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
