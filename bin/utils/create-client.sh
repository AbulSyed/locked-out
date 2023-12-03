# chmod +x ./create-client.sh

set -eu

dir=$(dirname ${0})

APP=${1}
CLIENT_ID=${2}
CLIENT_SECRET=${3}
AUTH_METHOD=${4}
AUTH_GRANT_TYPE=${5}
REDIRECT_URI=${6}

# convert bash array to json array
authMethodJson=$(printf '"%s",' ${AUTH_METHOD})
authGrantTypeJson=$(printf '"%s",' ${AUTH_GRANT_TYPE})

# remove trailing commas and wrap in square brackets
authMethodJsonArray="[${authMethodJson%,}]"
authGrantTypeJsonArray="[${authGrantTypeJson%,}]"

echo "\nCreating client: ${CLIENT_ID}, secret: ${CLIENT_SECRET}, auth method: ${authMethodJsonArray}, auth grant type: ${authGrantTypeJsonArray}, redirect uri: ${REDIRECT_URI} for app: ${APP}\n"

curl --location "http://localhost:8081/create-client/${APP}" \
--header 'x-correlation-id: 1' \
--header 'Content-Type: application/json' \
--data '{
  "clientId": "'${CLIENT_ID}'",
  "clientSecret": "'${CLIENT_SECRET}'",
  "authMethod": '"${authMethodJsonArray}"',
  "authGrantType": '"${authGrantTypeJsonArray}"',
  "redirectUri": "'${REDIRECT_URI}'"
}'