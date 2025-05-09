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
package org.apache.camel.quarkus.main;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.apache.camel.CamelContext;
import org.apache.camel.model.Model;

@Path("/test")
@ApplicationScoped
public class CamelSupportResource {
    @Inject
    CamelContext context;

    @Path("/describe")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject describeMain() {
        JsonArrayBuilder routes = Json.createArrayBuilder();
        JsonArrayBuilder rests = Json.createArrayBuilder();
        JsonArrayBuilder templates = Json.createArrayBuilder();
        context.getRoutes().forEach(route -> routes.add(route.getId()));

        Model model = context.getCamelContextExtension().getContextPlugin(Model.class);
        model.getRestDefinitions().forEach(rest -> rests.add(rest.getId()));
        model.getRouteTemplateDefinitions().forEach(template -> templates.add(template.getId()));

        return Json.createObjectBuilder()
                .add("routes", routes)
                .add("rests", rests)
                .add("templates", templates)
                .build();
    }

    @Path("/getShutdownStrategy")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getShutdownStrategy() {
        return context.getShutdownStrategy().getClass().getSimpleName();
    }
}
