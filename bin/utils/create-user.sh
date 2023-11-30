# chmod +x ./create-user.sh

set -eu

dir=$(dirname ${0})

APP=${1}
USERNAME=${2}
PASSWORD=${3}
EMAIL=${4}
PHONENUMBER=${5}

echo "\nCreating user: ${USERNAME}, password: ${PASSWORD}, email: ${EMAIL}, phonenumber: ${PHONENUMBER} for app: ${APP}\n"

curl --location "http://localhost:8081/create-user/${APP}" \
--header 'x-correlation-id: 1' \
--header 'Content-Type: application/json' \
--data-raw '{
  "username": "'${USERNAME}'",
  "password": "'${PASSWORD}'",
  "email": "'${EMAIL}'",
  "phoneNumber": "'${PHONENUMBER}'"
}'