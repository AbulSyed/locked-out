import './Faq.scss'

interface FaqProps {
}

const Faq: React.FC<FaqProps> = () => {
  return (
    <div>
      <div className='faq-container'>
        <p className='s-heading py-1'>What is OAuth 2.0?</p>
        <div>OAuth 2.0 is protocol that can be used to obtain authorization tokens on behalf of a resource owner. Tokens can be obtained using several grant flows. The most common is <i>Authorization Code</i> & <i>Client Credentials</i>.</div>
        <p className='s-heading py-1'>What is Authorization Code?</p>
        <div><i>Authorization Code</i> is used to obtain a token where the user doesn't share their credentials to the client, but instead the client redirects the user to a authorization server where the user can directly authenticate itself. After this, the client recieves a authorization_code from the authorization server. This code and along with client credentials will be sent to the authorization server to recieve a access token. Now the client can use the access token to get data from resource server.</div>
        <p className='s-heading py-1'>What is Client Credentials?</p>
        <div>In the <i>Client Credentials</i> grant flow, an end user is not involved. A client will send its client credentials to a authorization server in exchange for a access token. This access token can then be used to retrieved data from a resource server.</div>
        <p className='s-heading py-1'>What is Refresh Token?</p>
        <div>When an access token expires, client won't ask user to log in again but will use refresh token and will initiate another grant type flow with auth server. Auth server will then return a new access token with new expiration time and new refresh token</div>
        <p className='s-heading py-1'>What is PKCE?</p>
        <div>Proof Key for Code Exchange is a security extension to the OAuth 2.0 authorization code flow used to prevent a malicious attacker from obtaining the authorization code - which in turn can be used to obtain a access token.</div>
        <p className='s-heading py-1'>client_secret_basic vs client_secret_post?</p>
        <div>These both dictate how client credentials should be sent to the authorization server. client_secret_basic allows you to send client credentials as basic auth where with client_secret_post, you can send client credentials as x-www-form-urlencoded.</div>
      </div>
    </div>
  )
}

export default Faq