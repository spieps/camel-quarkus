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
package org.apache.camel.quarkus.component.opentelemetry;

import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "camel.opentelemetry", phase = ConfigPhase.RUN_TIME)
public final class CamelOpenTelemetryConfig {

    /**
     * Sets whether header names need to be encoded. Can be useful in situations where OpenTelemetry propagators potentially
     * set header name values in formats that are not compatible with the target system. E.g for JMS where the specification
     * mandates header names are valid Java identifiers.
     *
     * @asciidoclet
     */
    @ConfigItem(defaultValue = "false")
    public boolean encoding;

    /**
     * Sets whether to disable tracing for endpoint URIs or Processor ids that match the given comma separated patterns. The
     * pattern can take the following forms:
     *
     * 1. An exact match on the endpoint URI. E.g platform-http:/some/path
     *
     * 2. A wildcard match. E.g platform-http:++*++
     *
     * 3. A regular expression matching the endpoint URI. E.g platform-http:/prefix/.++*++
     *
     * @asciidoclet
     */
    @ConfigItem
    public Optional<String> excludePatterns;

    /**
     * Sets whether to create new OpenTelemetry spans for each Camel Processor. Use the excludePatterns property to filter
     * out Processors.
     *
     * @asciidoclet
     */
    @ConfigItem(defaultValue = "false")
    public boolean traceProcessors;
}
