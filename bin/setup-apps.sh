# chmod +x ./setup-apps.sh

set -eu

dir=$(dirname ${0})

echo "Setting up apps"

${dir}/utils/create-app.sh "app1" "my first app"
${dir}/utils/create-app.sh "app2" "my second app"