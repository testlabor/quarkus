package org.jboss.resteasy.reactive.server.vertx.test.response;

import static io.restassured.RestAssured.when;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.reactive.server.vertx.test.framework.ResteasyReactiveUnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

//https://github.com/quarkusio/quarkus/issues/17168
class NullHeaderTestCase {

    @RegisterExtension
    static ResteasyReactiveUnitTest runner = new ResteasyReactiveUnitTest()
            .withApplicationRoot((jar) -> jar
                    .addClasses(NullFilter.class, NullResource.class));

    @Test
    void nullHeaderTest() {
        when()
                .get("/null")
                .then().statusCode(200)
                .header("nullHeader", "");
    }

    @Provider
    public static class NullFilter implements ContainerResponseFilter {

        @Override
        public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
                throws IOException {
            responseContext.getHeaders().add("nullHeader", null);
        }
    }

    @Path("/null")
    public static class NullResource {
        @GET
        public Response ok() {
            return Response.ok().build();
        }
    }
}
