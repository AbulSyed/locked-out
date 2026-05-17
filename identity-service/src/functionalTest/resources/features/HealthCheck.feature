Feature: Health Check Endpoint

  Scenario: I want to verify the Health Check endpoint returns a 200 response with the message 'Identity service v202605171213 running'
    When a user calls the Health Check endpoint "/identity/health"
    Then the response status code should be "200 OK"
    And the response should contain the message "Identity service v202605171213 running"
