/*
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
package org.apache.camel.quarkus.core.it.annotations;

import io.quarkus.arc.ClientProxy;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.EndpointInject;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.direct.DirectEndpoint;

@ApplicationScoped
public class BeanB {
    @EndpointInject("direct:endpointInjectDirect1")
    DirectEndpoint endpointInjectDirect1;

    @EndpointInject("direct:endpointInjectDirect2")
    DirectEndpoint endpointInjectDirect2;

    @Produce("direct:produceProducer")
    ProducerTemplate produceProducer;

    @Produce("direct:produceProducerFluent")
    FluentProducerTemplate produceProducerFluent;

    public DirectEndpoint getEndpointInjectDirect1() {
        return ClientProxy.unwrap(endpointInjectDirect1);
    }

    public DirectEndpoint getEndpointInjectDirect2() {
        return ClientProxy.unwrap(endpointInjectDirect2);
    }

    public ProducerTemplate getProduceProducer() {
        return ClientProxy.unwrap(produceProducer);
    }

    public FluentProducerTemplate getProduceProducerFluent() {
        return ClientProxy.unwrap(produceProducerFluent);
    }
}
