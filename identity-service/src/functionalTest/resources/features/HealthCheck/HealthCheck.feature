Feature: Health Check Endpoint

  Scenario: Verify the Health Check endpoint returns a 200 response with the message 'Identity service v2023082139 running'
    When a user calls the Health Check endpoint "/health"
    Then the response status code should be "200 OK"
    And the response should contain the message "Identity service v202309040050 running"
