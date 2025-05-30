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
package org.apache.camel.quarkus.component.azure.key.vault.it;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.quarkus.test.junit.QuarkusTestProfile;

import static org.apache.camel.quarkus.component.azure.key.vault.it.AzureKeyVaultUtil.setPropertyIfEnvVarPresent;

public class AzureKeyVaultContextReloadWithIdentityTestProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        //properties have to be set via profile to not be used by different azure-* test in grouped module
        Map<String, String> props = new HashMap<>();
        setPropertyIfEnvVarPresent(props, "camel.vault.azure.eventhubConnectionString",
                "AZURE_VAULT_EVENT_HUBS_CONNECTION_STRING");
        setPropertyIfEnvVarPresent(props, "camel.vault.azure.blobAccountName", "AZURE_STORAGE_ACCOUNT_NAME");
        setPropertyIfEnvVarPresent(props, "camel.vault.azure.blobContainerName", "AZURE_VAULT_EVENT_HUBS_BLOB_CONTAINER_NAME");
        setPropertyIfEnvVarPresent(props, "camel.vault.azure.blobAccessKey", "AZURE_STORAGE_ACCOUNT_KEY");
        props.put("camel.vault.azure.refreshEnabled", "true");
        props.put("camel.vault.azure.refreshPeriod", "1000");
        props.put("camel.vault.azure.secrets", String.format("cq-secret-context-refresh-identity-%s.*", UUID.randomUUID()));
        props.put("camel.vault.azure.azureIdentityEnabled", "true");
        return props;
    }
}
