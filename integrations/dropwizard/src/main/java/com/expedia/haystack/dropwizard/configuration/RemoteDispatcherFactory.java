package com.expedia.haystack.dropwizard.configuration;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.expedia.www.haystack.client.dispatchers.Dispatcher;
import com.expedia.www.haystack.client.dispatchers.RemoteDispatcher;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("remote")
public class RemoteDispatcherFactory implements DispatcherFactory {

    @Valid
    @NotNull
    private ClientFactory client;

    @Nullable
    @Min(1)
    private Integer maxQueueSize;

    @Nullable
    @Min(1)
    private Integer threadCount;

    @Nullable
    @Min(1)
    private Long flushIntervalMs;

    @Nullable
    @Min(1)
    private Long shutdownTimoutMs;

    @Override
    public Dispatcher build() {
        RemoteDispatcher.Builder builder = new RemoteDispatcher.Builder(client.build());
        if (maxQueueSize != null) {
            builder.withBlockingQueueLimit(maxQueueSize);
        }
        if (threadCount != null) {
            builder.withExecutorThreadCount(threadCount);
        }
        if (flushIntervalMs != null) {
            builder.withFlushIntervalMillis(flushIntervalMs);
        }
        if (shutdownTimoutMs != null) {
            builder.withShutdownTimeoutMillis(shutdownTimoutMs);
        }
        return builder.build();
    }

    /**
     * @return the client
     */
    @JsonProperty
    public ClientFactory getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    @JsonProperty
    public void setClient(ClientFactory client) {
        this.client = client;
    }

    /**
     * @return the maxQueueSize
     */
    @JsonProperty
    public Integer getMaxQueueSize() {
        return maxQueueSize;
    }

    /**
     * @param maxQueueSize the maxQueueSize to set
     */
    @JsonProperty
    public void setMaxQueueSize(Integer maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    /**
     * @return the threadCount
     */
    @JsonProperty
    public Integer getThreadCount() {
        return threadCount;
    }

    /**
     * @param threadCount the threadCount to set
     */
    @JsonProperty
    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * @return the flushIntervalMs
     */
    @JsonProperty
    public Long getFlushIntervalMs() {
        return flushIntervalMs;
    }

    /**
     * @param flushIntervalMs the flushIntervalMs to set
     */
    @JsonProperty
    public void setFlushIntervalMs(Long flushIntervalMs) {
        this.flushIntervalMs = flushIntervalMs;
    }

    /**
     * @return the shutdownTimoutMs
     */
    @JsonProperty
    public Long getShutdownTimoutMs() {
        return shutdownTimoutMs;
    }

    /**
     * @param shutdownTimoutMs the shutdownTimoutMs to set
     */
    @JsonProperty
    public void setShutdownTimoutMs(Long shutdownTimoutMs) {
        this.shutdownTimoutMs = shutdownTimoutMs;
    }

}
