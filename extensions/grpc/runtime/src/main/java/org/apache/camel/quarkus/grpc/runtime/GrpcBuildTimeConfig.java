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
package org.apache.camel.quarkus.grpc.runtime;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigRoot(phase = ConfigPhase.BUILD_TIME)
@ConfigMapping(prefix = "quarkus.camel.grpc")
public interface GrpcBuildTimeConfig {
    /**
     * Excludes classes from the build time scanning of gRPC service classes.
     * This can be useful if there are gRPC services that you want to exclude from participating in Camel gRPC route
     * operations. The value is a comma separated list of class name patterns.
     * You can specify the fully qualified class name of individual classes or use path patterns to match multiple classes.
     * For example to exclude all classes starting with `MyService` use: `++**++MyService++*++`.
     * To exclude all services from a specific package use: `com.services.++*++`.
     * To exclude all services from a specific package and its sub-packages, use
     * double wildcards: `com.services.++**++`.
     * And to exclude all services from two specific packages use:
     * `com.services.++*++,com.other.services.++*++`.
     *
     * @asciidoclet
     */
    Optional<Set<String>> serviceExcludes();

    /**
     * Build time configuration options for Camel Quarkus gRPC code generator.
     *
     * @asciidoclet
     */
    CodeGenConfig codegen();

    interface CodeGenConfig {
        /**
         * If `true`, Camel Quarkus gRPC code generation is run for .proto files discovered from the `proto` directory, or from
         * dependencies specified in the `scan-for-proto` or `scan-for-imports` options. When `false`, code generation for
         * .proto files is disabled.
         *
         * @asciidoclet
         */
        @WithDefault("true")
        boolean enabled();

        /**
         * Camel Quarkus gRPC code generation can scan application dependencies for .proto files to generate Java stubs from
         * them. This property sets the scope of the dependencies to scan. Applicable values:
         *
         * - _none_ - default - don't scan dependencies
         * - a comma separated list of _groupId:artifactId_ coordinates to scan
         * - _all_ - scan all dependencies
         *
         * @asciidoclet
         */
        @WithDefault("none")
        String scanForProto();

        /**
         * Camel Quarkus gRPC code generation can scan dependencies for .proto files that can be imported by protos in this
         * applications. Applicable values:
         *
         * - _none_ - default - don't scan dependencies
         * - a comma separated list of _groupId:artifactId_ coordinates to scan
         * - _all_ - scan all dependencies The default is _com.google.protobuf:protobuf-java_.
         *
         * @asciidoclet
         */
        @WithDefault("com.google.protobuf:protobuf-java")
        String scanForImports();

        /**
         * Package path or file glob pattern includes per dependency containing .proto files to be considered for inclusion.
         *
         * @asciidoclet
         */
        Map<String, List<String>> scanForProtoIncludes();

        /**
         * Package path or file glob pattern includes per dependency containing .proto files to be considered for exclusion.
         *
         * @asciidoclet
         */
        Map<String, List<String>> scanForProtoExcludes();
    }
}
