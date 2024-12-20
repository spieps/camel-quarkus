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
package org.apache.camel.catalog.quarkus;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.catalog.CamelCatalog;
import org.apache.camel.catalog.RuntimeProvider;
import org.apache.camel.catalog.impl.CatalogHelper;

/**
 * A Quarkus based {@link RuntimeProvider} which only includes the supported Camel components, data formats, and
 * languages
 * which can be installed in Quarkus using the Camel extensions.
 */
public class QuarkusRuntimeProvider implements RuntimeProvider {

    private static final String COMPONENT_DIR = "org/apache/camel/catalog/quarkus/components";
    private static final String DATAFORMAT_DIR = "org/apache/camel/catalog/quarkus/dataformats";
    private static final String DEV_CONSOLE_DIR = "org/apache/camel/catalog/quarkus/dev-consoles";
    private static final String LANGUAGE_DIR = "org/apache/camel/catalog/quarkus/languages";
    private static final String TRANSFORMER_DIR = "org/apache/camel/catalog/quarkus/transformers";
    private static final String OTHER_DIR = "org/apache/camel/catalog/quarkus/others";
    private static final String BEANS_DIR = "org/apache/camel/catalog/quarkus/beans";
    private static final String COMPONENTS_CATALOG = "org/apache/camel/catalog/quarkus/components.properties";
    private static final String DEV_CONSOLE_CATALOG = "org/apache/camel/catalog/quarkus/dev-consoles.properties";
    private static final String DATA_FORMATS_CATALOG = "org/apache/camel/catalog/quarkus/dataformats.properties";
    private static final String LANGUAGE_CATALOG = "org/apache/camel/catalog/quarkus/languages.properties";
    private static final String TRANSFORMER_CATALOG = "org/apache/camel/catalog/quarkus/transformers.properties";
    private static final String OTHER_CATALOG = "org/apache/camel/catalog/quarkus/others.properties";
    private static final String BEANS_CATALOG = "org/apache/camel/catalog/quarkus/beans.properties";

    private CamelCatalog camelCatalog;

    @Override
    public CamelCatalog getCamelCatalog() {
        return camelCatalog;
    }

    @Override
    public void setCamelCatalog(CamelCatalog camelCatalog) {
        this.camelCatalog = camelCatalog;
    }

    @Override
    public String getProviderName() {
        return "quarkus";
    }

    @Override
    public String getProviderGroupId() {
        return "org.apache.camel.quarkus";
    }

    @Override
    public String getProviderArtifactId() {
        return "camel-quarkus-catalog";
    }

    @Override
    public String getComponentJSonSchemaDirectory() {
        return COMPONENT_DIR;
    }

    @Override
    public String getDataFormatJSonSchemaDirectory() {
        return DATAFORMAT_DIR;
    }

    @Override
    public String getDevConsoleJSonSchemaDirectory() {
        return DEV_CONSOLE_DIR;
    }

    @Override
    public String getLanguageJSonSchemaDirectory() {
        return LANGUAGE_DIR;
    }

    @Override
    public String getTransformerJSonSchemaDirectory() {
        return TRANSFORMER_DIR;
    }

    @Override
    public String getOtherJSonSchemaDirectory() {
        return OTHER_DIR;
    }

    @Override
    public String getPojoBeanJSonSchemaDirectory() {
        return BEANS_DIR;
    }

    @Override
    public List<String> findComponentNames() {
        List<String> names = new ArrayList<>();
        InputStream is = camelCatalog.getVersionManager().getResourceAsStream(COMPONENTS_CATALOG);
        if (is != null) {
            try {
                CatalogHelper.loadLines(is, names);
            } catch (IOException e) {
                // ignore
            }
        }
        return names;
    }

    @Override
    public List<String> findDataFormatNames() {
        List<String> names = new ArrayList<>();
        InputStream is = camelCatalog.getVersionManager().getResourceAsStream(DATA_FORMATS_CATALOG);
        if (is != null) {
            try {
                CatalogHelper.loadLines(is, names);
            } catch (IOException e) {
                // ignore
            }
        }
        return names;
    }

    @Override
    public List<String> findDevConsoleNames() {
        List<String> names = new ArrayList<>();
        InputStream is = camelCatalog.getVersionManager().getResourceAsStream(DEV_CONSOLE_CATALOG);
        if (is != null) {
            try {
                CatalogHelper.loadLines(is, names);
            } catch (IOException e) {
                // ignore
            }
        }
        return names;
    }

    @Override
    public List<String> findLanguageNames() {
        List<String> names = new ArrayList<>();
        InputStream is = camelCatalog.getVersionManager().getResourceAsStream(LANGUAGE_CATALOG);
        if (is != null) {
            try {
                CatalogHelper.loadLines(is, names);
            } catch (IOException e) {
                // ignore
            }
        }
        return names;
    }

    @Override
    public List<String> findTransformerNames() {
        List<String> names = new ArrayList<>();
        InputStream is = camelCatalog.getVersionManager().getResourceAsStream(TRANSFORMER_CATALOG);
        if (is != null) {
            try {
                CatalogHelper.loadLines(is, names);
            } catch (IOException e) {
                // ignore
            }
        }
        return names;
    }

    @Override
    public List<String> findOtherNames() {
        List<String> names = new ArrayList<>();
        InputStream is = camelCatalog.getVersionManager().getResourceAsStream(OTHER_CATALOG);
        if (is != null) {
            try {
                CatalogHelper.loadLines(is, names);
            } catch (IOException e) {
                // ignore
            }
        }
        return names;
    }

    @Override
    public List<String> findBeansNames() {
        List<String> names = new ArrayList<>();
        InputStream is = camelCatalog.getVersionManager().getResourceAsStream(BEANS_CATALOG);
        if (is != null) {
            try {
                CatalogHelper.loadLines(is, names);
            } catch (IOException e) {
                // ignore
            }
        }
        return names;
    }
}
