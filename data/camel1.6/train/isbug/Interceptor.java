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
package org.apache.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Intercept;
import org.apache.camel.Processor;
import org.apache.camel.util.ServiceHelper;

/**
 * An interceptor which provides the processing logic as a pluggable processor
 * which allows the {@link #proceed(Exchange)} method to be called at some point
 *
 * @version $Revision: 662664 $
 */
public class Interceptor extends DelegateProcessor implements Intercept {
    private Processor interceptorLogic;

    public Interceptor() {
    }

    public Interceptor(Processor interceptorLogic) {
        this.interceptorLogic = interceptorLogic;
    }

    public void process(Exchange exchange) throws Exception {
        interceptorLogic.process(exchange);
    }

    public Processor getInterceptorLogic() {
        return interceptorLogic;
    }

    public void setInterceptorLogic(Processor interceptorLogic) {
        this.interceptorLogic = interceptorLogic;
    }

    @Override
    protected void doStart() throws Exception {
        ServiceHelper.startService(interceptorLogic);
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        ServiceHelper.stopService(interceptorLogic);
        super.doStop();
    }
}
