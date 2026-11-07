FROM amazoncorretto:17-alpine

RUN apk add --no-cache curl

WORKDIR /app

ARG OTEL_AGENT_VERSION=2.33.1
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar opentelemetry-javaagent.jar

COPY build/libs/*.jar identity-service.jar
COPY --chmod=755 docker-entrypoint.sh docker-entrypoint.sh

ENV JAVA_OPTIONS="\
    -XX:+ExitOnOutOfMemoryError"

ENV JAVA_TOOL_OPTIONS="\
    -javaagent:./opentelemetry-javaagent.jar \
    -Dotel.exporter.otlp.compression=gzip \
    -Dotel.exporter.otlp.metrics.default.histogram.aggregation=base2_exponential_bucket_histogram \
    -Dotel.exporter.otlp.metrics.temporality.preference=delta \
    -Dotel.instrumentation.common.logging.structured-attributes.included=* \
    -Dotel.instrumentation.logback-appender.experimental-log-attributes=true \
    -Dotel.instrumentation.logback-appender.experimental.mdc-attributes.included=* \
    -Dotel.instrumentation.micrometer.enabled=true \
    -Dotel.javaagent.logging=application \
    -Dotel.traces.sampler=parentbased_traceidratio"

EXPOSE 8081

ENTRYPOINT ["./docker-entrypoint.sh"]
