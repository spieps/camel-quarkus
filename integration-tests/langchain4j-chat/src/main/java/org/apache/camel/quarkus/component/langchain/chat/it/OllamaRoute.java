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
package org.apache.camel.quarkus.component.langchain.chat.it;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class OllamaRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:send-simple-message?timeout=30000")
                .to("langchain4j-chat:test1?chatOperation=CHAT_SINGLE_MESSAGE")
                .to("mock:simpleMessageResponse");

        from("direct:send-message-prompt?timeout=30000")
                .to("langchain4j-chat:test2?chatOperation=CHAT_SINGLE_MESSAGE_WITH_PROMPT")
                .to("mock:messagePromptResponse");

        from("direct:send-multiple?timeout=30000")
                .to("langchain4j-chat:test3?chatOperation=CHAT_MULTIPLE_MESSAGES")
                .to("mock:multipleMessageResponse");

        from("direct:send-simple-message-m1")
                .to("langchain4j-chat:test4?chatOperation=CHAT_SINGLE_MESSAGE&chatModel=#m1")
                .to("mock:simpleMessageResponseM1");

        from("direct:send-simple-message-m2")
                .to("langchain4j-chat:test5?chatOperation=CHAT_SINGLE_MESSAGE&chatModel=#m2")
                .to("mock:simpleMessageResponseM2");
    }
}
