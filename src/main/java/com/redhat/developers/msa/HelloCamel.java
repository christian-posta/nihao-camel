/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.developers.msa;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by ceposta 
 * <a href="http://christianposta.com/blog>http://christianposta.com/blog</a>.
 */
@Component
public class HelloCamel extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("netty4-http").host("0.0.0.0").port("9090");
        rest("/")
                .get("/nihao")
                    .produces("text/plain").route()
                .setBody().method(SayHello.class).endRest()
                .get("/nihao-chaining")
                    .produces("application/json").route()
                        .loadBalance().circuitBreaker(5, 5000).to("netty4-http://localhost:8080/api/backend");
    }
}
