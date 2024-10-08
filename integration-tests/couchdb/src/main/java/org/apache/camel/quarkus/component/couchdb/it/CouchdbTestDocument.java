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
package org.apache.camel.quarkus.component.couchdb.it;

import java.util.Optional;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CouchdbTestDocument {

    @JsonProperty("_rev")
    private String revision;
    @JsonProperty("_id")
    private String id;
    private String value;

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        if (this.id != null) {
            jsonObject.addProperty("_id", this.id);
        }
        if (this.revision != null) {
            jsonObject.addProperty("_rev", this.revision);
        }
        if (this.value != null) {
            jsonObject.addProperty("value", this.value);
        }
        return jsonObject;
    }

    public static CouchdbTestDocument fromJsonObject(JsonObject jsonObject) {
        CouchdbTestDocument doc = new CouchdbTestDocument();
        if (jsonObject.has("id")) {
            doc.setId(jsonObject.get("id").getAsString());
        }
        if (jsonObject.has("changes") && jsonObject.get("changes").isJsonArray()) {
            JsonArray ja = jsonObject.get("changes").getAsJsonArray();
            Optional<String> rev = StreamSupport.stream(ja.spliterator(), true)
                    .filter(jo -> ((JsonObject) jo).has("rev"))
                    .map(jo2 -> ((JsonObject) jo2).get("rev").getAsString())
                    .findAny();
            doc.setRevision(rev.orElse(null));
        }
        if (jsonObject.has("value")) {
            doc.setValue(jsonObject.get("value").getAsString());
        } else if (jsonObject.has("_deleted")) {
            doc.setValue("delete");
        }
        return doc;
    }
}
