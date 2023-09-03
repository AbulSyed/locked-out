Feature: Health Check Endpoint

  Scenario: Verify the Health Check endpoint returns a 200 response with the message 'Identity service v2023082139 running'
    Given the API is running locally
    When a user calls the Health Check endpoint
    Then the response status code should be 200 OK
    And the response body should contain the message 'Identity service v2023082139 running'
