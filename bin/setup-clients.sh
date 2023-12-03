# chmod +x ./setup-clients.sh

set -eu

dir=$(dirname ${0})

echo "Setting up clients"

${dir}/utils/create-client.sh "1" "client1" "secret" "CLIENT_SECRET_BASIC" "AUTHORIZATION_CODE" "http://127.0.0.1:3000/authorized"
${dir}/utils/create-client.sh "1" "client2" "secret" "CLIENT_SECRET_BASIC CLIENT_SECRET_POST" "AUTHORIZATION_CODE CLIENT_CREDENTIALS" "http://127.0.0.1:3000/authorized"