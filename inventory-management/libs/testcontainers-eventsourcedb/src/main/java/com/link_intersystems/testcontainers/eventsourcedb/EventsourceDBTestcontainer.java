package com.link_intersystems.testcontainers.eventsourcedb;

import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import com.github.dockerjava.api.model.HealthCheck;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class EventsourceDBTestcontainer extends GenericContainer<EventsourceDBTestcontainer> {

    private static final HealthCheck HEALTH_CHECK;

    static {
        HEALTH_CHECK = new HealthCheck()
                .withInterval(1000000000L)
                .withTimeout(1000000000L)
                .withRetries(10);
    }

    public EventsourceDBTestcontainer() {
        this("latest");
    }

    public EventsourceDBTestcontainer(String version) {
        super(String.format("eventstore/eventstore:%s", version));

        addExposedPorts(1113, 1113);
        addExposedPorts(2113, 2113);

        withEnv("EVENTSTORE_RUN_PROJECTIONS", "ALL");
        withEnv("EVENTSTORE_START_STANDARD_PROJECTIONS", "true");
        withEnv("EVENTSTORE_CLUSTER_SIZE", "1");
        withEnv("EVENTSTORE_ENABLE_ATOM_PUB_OVER_HTTP", "true");
        withEnv("EVENTSTORE_ENABLE_EXTERNAL_TCP", "true");
        withEnv("EVENTSTORE_INSECURE", "true");
        withEnv("EVENTSTORE_EXT_TCP_PORT", "1113");
        withEnv("EVENTSTORE_HTTP_PORT", "2113");

        withCreateContainerCmdModifier(cmd -> cmd.withHealthcheck(HEALTH_CHECK));
        waitingFor(Wait.forHealthcheck());
    }

    public EventStoreDBClient getClient() {
        Integer mappedPort = getMappedPort(2113);
        EventStoreDBClientSettings setts = EventStoreDBConnectionString.parseOrThrow("esdb://localhost:" + mappedPort + "?tls=false");
        return EventStoreDBClient.create(setts);
    }
}