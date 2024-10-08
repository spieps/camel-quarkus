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
package org.apache.camel.quarkus.component.bean.bind;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.camel.BindToRegistry;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class BindToRegistryRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:invokeBindToRegistryBean")
                .toD("bean:${header.beanName}");

        from("direct:getAndInvokeBindToRegistryBean")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        CamelContext context = exchange.getContext();
                        BindToRegistrySimpleBean bean = (BindToRegistrySimpleBean) context.getRegistry()
                                .lookupByName("BindToRegistrySimpleBean");
                        exchange.getMessage().setBody(bean.hello("BindToRegistrySimpleBean"));
                    }
                });
    }

    @RegisterForReflection(fields = false)
    @BindToRegistry
    public static class RouteNestedBindToRegistryBean {
        public String hello(String name) {
            return "Hello " + name;
        }
    }
}
