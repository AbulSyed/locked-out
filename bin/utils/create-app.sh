# chmod +x ./create-app.sh

set -eu

dir=$(dirname ${0})

NAME=${1}
DESCRIPTION=${2}

echo "\nCreating app with name: ${NAME}, description: ${DESCRIPTION}\n"

curl --location 'http://localhost:8081/create-app' \
--header 'x-correlation-id: 1' \
--header 'Content-Type: application/json' \
--data '{
  "name": "'${NAME}'",
  "description": "'"${DESCRIPTION}"'"
}'