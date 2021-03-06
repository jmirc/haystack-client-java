package com.expedia.haystack.dropwizard.configuration;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.expedia.www.haystack.client.Tracer;
import com.expedia.www.haystack.client.dispatchers.ChainedDispatcher;
import com.expedia.www.haystack.client.dispatchers.Dispatcher;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import io.opentracing.NoopTracerFactory;

public class TracerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(TracerFactory.class);

    @Valid
    private boolean enabled = true;

    @NotEmpty
    private String serviceName;

    @Valid
    @NotEmpty
    private List<DispatcherFactory> dispatchers = ImmutableList.of(new RemoteDispatcherFactory());

    public io.opentracing.Tracer build() {
        if (!enabled) {
            return NoopTracerFactory.create();
        }
        Dispatcher dispatcher;
        if (dispatchers.size() > 1) {
            ChainedDispatcher.Builder builder = new ChainedDispatcher.Builder();
            for (DispatcherFactory factory : dispatchers) {
                builder.withDispatcher(factory.build());
            }
            dispatcher = builder.build();
        } else {
            dispatcher = dispatchers.get(0).build();
        }

        final Tracer.Builder builder = new Tracer.Builder(serviceName, dispatcher);
        return builder.build();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("serviceName", serviceName)
            .add("dispatchers", dispatchers)
            .toString();
    }

    @JsonProperty
    public boolean isEnabled() {
        return enabled;
    }

    @JsonProperty
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty
    public String getServiceName() {
        return serviceName;
    }

    @JsonProperty
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @JsonProperty
    public List<DispatcherFactory> getDispatchers() {
        return dispatchers;
    }

    @JsonProperty
    public void setDispatchers(List<DispatcherFactory> dispatchers) {
        this.dispatchers = dispatchers;
    }
}
