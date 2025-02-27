package io.quarkus.datasource.runtime;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;

@ConfigGroup
public class DevServicesBuildTimeConfig {

    /**
     * If DevServices has been explicitly enabled or disabled. DevServices is generally enabled
     * by default, unless there is an existing configuration present.
     *
     * When DevServices is enabled Quarkus will attempt to automatically configure and start
     * a database when running in Dev or Test mode.
     */
    @ConfigItem
    public Optional<Boolean> enabled = Optional.empty();

    /**
     * The container image name to use, for container based DevServices providers.
     *
     * If the provider is not container based (e.g. a H2 Database) then this has no effect.
     */
    @ConfigItem
    public Optional<String> imageName;

    /**
     * Generic properties that are passed for additional container configuration.
     * <p>
     * Properties defined here are database specific and are interpreted specifically in each database dev service
     * implementation.
     */
    @ConfigItem
    public Map<String, String> containerProperties;

    /**
     * Generic properties that are added to the database connection URL.
     */
    @ConfigItem
    public Map<String, String> properties;

    /**
     * Optional fixed port the dev service will listen to.
     * <p>
     * If not defined, the port will be chosen randomly.
     */
    @ConfigItem
    public OptionalInt port;

    /**
     * The container start command to use, for container based DevServices providers.
     *
     * If the provider is not container based (e.g. a H2 Database) then this has no effect.
     */
    @ConfigItem
    public Optional<String> command;

    /**
     * The name of the database to use if this Dev Service supports overriding it.
     */
    @ConfigItem
    public Optional<String> dbName;

    /**
     * The username to use if this Dev Service supports overriding it.
     */
    @ConfigItem
    public Optional<String> username;

    /**
     * The password to use if this Dev Service supports overriding it.
     */
    @ConfigItem
    public Optional<String> password;

    /**
     * Path to a SQL script that will be loaded from the classpath and applied to the Dev Service database
     *
     * If the provider is not container based (e.g. a H2 or Derby Database) then this has no effect.
     */
    @ConfigItem
    public Optional<String> initScriptPath;
}
