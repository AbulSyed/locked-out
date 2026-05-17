Feature: Health Check Endpoint

  Scenario: I want to verify the Health Check endpoint returns a 200 response with the message 'Auth service v202605171213 running'
    When a user calls the Health Check endpoint "/auth/health"
    Then the response status code should be "200 OK"
    And the response should contain the message "Auth service v202605171213 running"
