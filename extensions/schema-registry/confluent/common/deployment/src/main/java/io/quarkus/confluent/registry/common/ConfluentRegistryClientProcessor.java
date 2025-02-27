package io.quarkus.confluent.registry.common;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.ExtensionSslNativeSupportBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ServiceProviderBuildItem;
import io.quarkus.deployment.pkg.builditem.CurateOutcomeBuildItem;

public class ConfluentRegistryClientProcessor {

    @BuildStep
    public void confluentRegistryClient(
            CurateOutcomeBuildItem curateOutcomeBuildItem,
            BuildProducer<ReflectiveClassBuildItem> reflectiveClass,
            BuildProducer<ServiceProviderBuildItem> serviceProviders,
            BuildProducer<ExtensionSslNativeSupportBuildItem> sslNativeSupport) {

        if (curateOutcomeBuildItem.getApplicationModel().getDependencies().stream().anyMatch(
                x -> x.getGroupId().equals("io.confluent")
                        && x.getArtifactId().equals("kafka-schema-serializer"))) {
            reflectiveClass
                    .produce(new ReflectiveClassBuildItem(true, false, false,
                            "io.confluent.kafka.serializers.context.NullContextNameStrategy"));

            reflectiveClass
                    .produce(new ReflectiveClassBuildItem(true, true, false,
                            "io.confluent.kafka.serializers.subject.TopicNameStrategy",
                            "io.confluent.kafka.serializers.subject.TopicRecordNameStrategy",
                            "io.confluent.kafka.serializers.subject.RecordNameStrategy"));
        }

        if (curateOutcomeBuildItem.getApplicationModel().getDependencies().stream().anyMatch(
                x -> x.getGroupId().equals("io.confluent")
                        && x.getArtifactId().equals("kafka-schema-registry-client"))) {
            reflectiveClass
                    .produce(new ReflectiveClassBuildItem(true, true, false,
                            "io.confluent.kafka.schemaregistry.client.rest.entities.ErrorMessage",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.Schema",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.Config",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.SchemaReference",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.SchemaString",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.SchemaTypeConverter",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.ServerClusterId",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.SubjectVersion"));

            reflectiveClass
                    .produce(new ReflectiveClassBuildItem(true, true, false,
                            "io.confluent.kafka.schemaregistry.client.rest.entities.requests.CompatibilityCheckResponse",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.requests.ConfigUpdateRequest",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.requests.ModeUpdateRequest",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.requests.RegisterSchemaRequest",
                            "io.confluent.kafka.schemaregistry.client.rest.entities.requests.RegisterSchemaResponse"));

            // Make this a weak registration since the class is only reachable when kafka-schema-registry-client v [5.2,7) is in the classpath
            reflectiveClass
                    .produce(ReflectiveClassBuildItem.weakClass(true, true, false,
                            "io.confluent.kafka.schemaregistry.client.rest.entities.requests.ModeGetResponse"));

            serviceProviders
                    .produce(new ServiceProviderBuildItem(
                            "io.confluent.kafka.schemaregistry.client.security.basicauth.BasicAuthCredentialProvider",
                            "io.confluent.kafka.schemaregistry.client.security.basicauth.SaslBasicAuthCredentialProvider",
                            "io.confluent.kafka.schemaregistry.client.security.basicauth.UrlBasicAuthCredentialProvider",
                            "io.confluent.kafka.schemaregistry.client.security.basicauth.UserInfoCredentialProvider"));
        }
    }
}
