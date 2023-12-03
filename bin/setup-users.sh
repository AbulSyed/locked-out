# chmod +x ./setup-users.sh

set -eu

dir=$(dirname ${0})

echo "Setting up users"

${dir}/utils/create-user.sh "1" "user1" "user1" "user1@m.com" "111"
${dir}/utils/create-user.sh "2" "user2" "user2" "user2@m.com" "222"