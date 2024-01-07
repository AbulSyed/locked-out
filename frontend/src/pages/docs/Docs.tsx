import './Docs.scss'

interface DocsProps {
}

const dependencies = `
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.security:spring-security-oauth2-jose:6.0.3'
`

const javacode = `
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());

        http
            .csrf().disable()
            .authorizeHttpRequests().requestMatchers("/demo").authenticated() // <-- endpoint
            .and()
            .oauth2ResourceServer().jwt()
            .jwtAuthenticationConverter(jwtAuthenticationConverter);

        return http.build();
      }
    }
`

const JwtRoleConverter = `
    public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

      @Override
      public Collection<GrantedAuthority> convert(Jwt source) {
          List<String> roles = (List<String>) source.getClaims().get("roles");
          List<String> authorities = (List<String>) source.getClaims().get("authorities");

          if (roles == null || roles.isEmpty()) {
              return new ArrayList<>();
          }

          if (authorities == null || authorities.isEmpty()) {
              return new ArrayList<>();
          }

          return Stream.concat(authorities.stream(), roles.stream())
                      .map(SimpleGrantedAuthority::new)
                      .collect(Collectors.toList());
      }
    }
`

const appYaml = `
    spring:
      security:
        oauth2:
          resourceserver:
            jwt:
              jwk-set-uri: http://localhost:8080/oauth2/jwks
`

const protection = `
    .requestMatchers("/demo").authenticated()
    .requestMatchers("/demo").hasRole(ROLE_NAME)
    .requestMatchers("/demo").hasAnyRole(.., ..)
    .requestMatchers("/demo").hasAuthority(AUTHORITY_NAME)
    .requestMatchers("/demo").hasAnyAuthority(.., ..)

or

    @PreAuthorize(hasRole('ROLE_NAME'))
    @PostAutorize(hasRole('ROLE_NAME'))
    @PreAuthorize(hasAuthority('AUTHORITY_NAME'))
    @PostAutorize(hasAuthority('AUTHORITY_NAME'))
`

const authorizeEndpoint = `
http://localhost:8080/oauth2/authorize?response_type=code&client_id=\${clientId}&redirect_uri=\${redirect_uri}&appname=\${appname}&scope=\${scopes}
`

const tokenEndpoint = `
http://localhost:8080/oauth2/token?client_id=\${clientId}&redirect_uri=\${redirect_uri}&grant_type=authorization_code&code=\${code}&appname=\${appname}
`

const Docs: React.FC<DocsProps> = () => {

  return (
    <div>
      <div className='docs-container'>
        <p className='m-heading py-1'>To connect a Spring Boot resource server to LockedOut:</p>
        <u>1. Add dependencies</u>
        <pre className='code-block'>
          {dependencies}
        </pre>
        <br />
        <u>2. Add SecurityConfig</u>
        <pre className="code-block">
          {javacode}
        </pre>
        <br />
        <u>3. Add JwtRoleConverter</u>
        <pre className="code-block">
          {JwtRoleConverter}
        </pre>
        <br />
        <u>4. Update application.yaml</u>
        <pre className="code-block">
          {appYaml}
        </pre>
        <br />
        <u>5. Protecting endpoints</u>
        <pre className="code-block">
          {protection}
        </pre>
        <br />
        <p className='m-heading py-1'>To connect React.js to support Authorization Code grant flow:</p>
        <u>1: When user clicks `Login` - route them to oauth2/authorize endpoint</u>
        <pre className="code-block">
          {authorizeEndpoint}
        </pre>
        <br />
        <u>2: After a user logs in, auth server will redirect the request to redirect uri with authorization code in request params. Retrieve the code</u>
        <br />
        <br />
        <u>3: Using the code, send to oauth2/token endpoint to get access token</u>
        <pre className="code-block">
          {tokenEndpoint}
        </pre>
        <br />
        <u>4: Use token to access protected endpoint</u>
        <br />
        <br />
        <u>5: Refresh token when necessary</u>
        <br />
        <br />
      </div>
    </div>
  )
}

export default Docs