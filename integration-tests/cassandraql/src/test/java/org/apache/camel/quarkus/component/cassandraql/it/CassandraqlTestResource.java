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

package org.apache.camel.quarkus.component.cassandraql.it;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.Map;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.apache.camel.util.CollectionHelper;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class CassandraqlTestResource implements QuarkusTestResourceLifecycleManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraqlTestResource.class);
    private static final int PORT = 9042;
    private static final String DOCKER_IMAGE_NAME = ConfigProvider.getConfig().getValue("cassandra.container.image",
            String.class);

    protected CassandraContainer container;

    @Override
    public Map<String, String> start() {
        try {
            DockerImageName imageName = DockerImageName.parse(DOCKER_IMAGE_NAME).asCompatibleSubstituteFor("cassandra");
            container = new CassandraContainer(imageName)
                    .withExposedPorts(PORT)
                    .waitingFor(Wait.forLogMessage(".*Created default superuser role.*", 1))
                    .withEnv("JVM_EXTRA_OPTS", "-Xss448k")
                    .withConfigurationOverride("/cassandra");

            container.start();

            initDB(container);

            String cassandraUrl = container.getHost() + ":" + container.getMappedPort(PORT);
            return CollectionHelper.mapOf(
                    // Note: The cassandra component does not depend on any of these being set.
                    // They're added to test the component with the (optional) QuarkusCqlSession
                    // produced by the Quarkus extension
                    "quarkus.cassandra.contact-points", cassandraUrl,
                    "quarkus.cassandra.local-datacenter", "datacenter1",
                    "quarkus.cassandra.auth.username", container.getUsername(),
                    "quarkus.cassandra.auth.password", container.getPassword(),
                    "quarkus.cassandra.keyspace", CassandraqlRoutes.KEYSPACE);

        } catch (Exception e) {
            LOGGER.error("Container does not start", e);
            throw new RuntimeException(e);
        }
    }

    private void initDB(CassandraContainer container) {
        InetSocketAddress endpoint = new InetSocketAddress(container.getHost(), container.getMappedPort(PORT));
        CqlSessionBuilder builder = CqlSession.builder()
                .withLocalDatacenter(container.getLocalDatacenter())
                .withAuthCredentials(container.getUsername(), container.getPassword())
                .withConfigLoader(DriverConfigLoader.programmaticBuilder()
                        .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(5)).build())
                .addContactPoint(endpoint);

        try (CqlSession session = builder.build()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + CassandraqlRoutes.KEYSPACE + " WITH replication = \n" +
                    "{'class':'SimpleStrategy','replication_factor':'1'};");

            session.execute("CREATE TABLE " + CassandraqlRoutes.KEYSPACE + ".employee(\n" +
                    "   id int PRIMARY KEY,\n" +
                    "   name text,\n" +
                    "   address text\n" +
                    "   );");

            session.execute("CREATE TABLE " + CassandraqlRoutes.KEYSPACE + ".CAMEL_IDEMPOTENT (\n" +
                    "  NAME varchar,\n" +
                    "  KEY varchar,\n" +
                    "  PRIMARY KEY (NAME, KEY)\n" +
                    ");");

            session.execute("CREATE TABLE " + CassandraqlRoutes.KEYSPACE + ".CAMEL_AGGREGATION (\n" +
                    "  NAME varchar,\n" +
                    "  KEY varchar,\n" +
                    "  EXCHANGE_ID varchar,\n" +
                    "  EXCHANGE blob,\n" +
                    "  PRIMARY KEY (NAME, KEY)\n" +
                    ");");
        }
    }

    @Override
    public void stop() {
        try {
            if (container != null) {
                container.stop();
            }
        } catch (Exception e) {
            // ignored
        }
    }
}
