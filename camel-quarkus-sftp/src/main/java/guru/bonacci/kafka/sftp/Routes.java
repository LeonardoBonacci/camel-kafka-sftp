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
package guru.bonacci.kafka.sftp;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import lombok.AllArgsConstructor;

/**
 * Camel route definitions.
 */
@AllArgsConstructor
@ApplicationScoped
public class Routes extends EndpointRouteBuilder {

	private final KafkaProcessor kafka;
    
    @Override
    public void configure() throws Exception {
        from("platform-http:/fruits?httpMethodRestrict=POST")
        		.setBody().constant("blablaaaaaa")
                .process(kafka);
    }
}