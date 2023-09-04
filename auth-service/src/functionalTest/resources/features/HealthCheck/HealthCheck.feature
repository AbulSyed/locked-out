Feature: Health Check Endpoint

  Scenario: Verify the Health Check endpoint returns a 200 response with the message 'Auth service v202308201929 running'
    When a user calls the Health Check endpoint "/health"
    Then the response status code should be "200 OK"
    And the response should contain the message "Auth service v202308201929 running"
