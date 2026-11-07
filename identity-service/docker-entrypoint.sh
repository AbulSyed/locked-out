#!/bin/sh

export OTEL_EXPORTER_OTLP_ENDPOINT="https://otlp.eu01.nr-data.net"
export OTEL_EXPORTER_OTLP_HEADERS="api-key=$NEW_RELIC_API_KEY"
export OTEL_SERVICE_NAME="locked-out-identity-service"
export OTEL_TRACES_SAMPLER_ARG="1"

exec java ${JAVA_OPTIONS} -jar identity-service.jar
