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
package org.apache.camel.quarkus.test.junit5.patterns;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.quarkus.test.CamelQuarkusTestSupport;
import org.apache.camel.test.junit5.DebugBreakpoint;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestProfile(DebugJUnit5Test.class)
public class DebugJUnit5Test extends CamelQuarkusTestSupport {

    private static final Logger LOG = Logger.getLogger(DebugJUnit5Test.class);

    private TestDebugBreakpoint testDebugBreakpoint;

    @Override
    public void doPreSetup() throws Exception {
        super.doPreSetup();

        camelContextConfiguration()
                .withBreakpoint(createBreakpoint());
    }

    protected DebugBreakpoint createBreakpoint() {
        testDebugBreakpoint = new TestDebugBreakpoint();
        return testDebugBreakpoint;
    }

    // START SNIPPET: e1
    @Override
    public boolean isUseDebugger() {
        // must enable debugger
        return true;
    }

    @Override
    protected void debugBefore(
            Exchange exchange, Processor processor, ProcessorDefinition<?> definition, String id, String shortName) {
        // this method is invoked before we are about to enter the given
        // processor
        // from your Java editor you can just add a breakpoint in the code line
        // below
        LOG.info("Before " + definition + " with body " + exchange.getIn().getBody());
    }
    // END SNIPPET: e1

    @Test
    public void testDebugger() throws Exception {
        // set mock expectations
        getMockEndpoint("mock:a").expectedMessageCount(1);
        getMockEndpoint("mock:b").expectedMessageCount(1);

        // send a message
        template.sendBody("direct:start", "World");

        // assert mocks
        MockEndpoint.assertIsSatisfied(context);
    }

    @Test
    public void testTwo() throws Exception {
        // set mock expectations
        getMockEndpoint("mock:a").expectedMessageCount(2);
        getMockEndpoint("mock:b").expectedMessageCount(2);

        // send a message
        template.sendBody("direct:start", "World");
        template.sendBody("direct:start", "Camel");

        // assert mocks
        MockEndpoint.assertIsSatisfied(context);
    }

    // START SNIPPET: e2
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                // this is the route we want to debug
                from("direct:start").to("mock:a").transform(body().prepend("Hello ")).to("mock:b");
            }
        };
    }
    // END SNIPPET: e2

}
