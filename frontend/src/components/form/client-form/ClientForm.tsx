import './ClientForm.scss'

import { useState } from 'react'
import { useAppDispatch } from '../../../store/hooks'

interface ClientFormInterface {
  type: string;
  id?: string;
  initClientId?: string;
  initClientSecret?: string;
  initAuthMethod: string[];
  initAuthGrantType: string[];
  initRedirectUri?: string;
  showClientForm: boolean;
  setShowClientForm: any;
}

const ClientForm: React.FC<ClientFormInterface> = ({ type, id, initClientId, initClientSecret, initAuthMethod, initAuthGrantType, initRedirectUri, showClientForm, setShowClientForm }) => {
  const [clientId, setClientId] = useState(initClientId)
  const [clientSecret, setClientSecret] = useState(initClientSecret)
  const [authMethod, setAuthMethod] = useState(initAuthMethod)
  const [authGrantType, setAuthGrantType] = useState(initAuthGrantType)
  const [redirectUri, setRedirectUri] = useState(initRedirectUri)
  
  const [client_secret_basicChecked, setClient_secret_basicChecked] = useState(false)
  const [client_secret_postChecked, setclient_secret_postChecked] = useState(false)
  const [authorization_codeChecked, setAuthorization_codeChecked] = useState(false)
  const [client_credentialsChecked, setClient_credentialsChecked] = useState(false)
  
  const dispatch = useAppDispatch()

  const handleSubmit = (e: any) => {
    e.preventDefault()

    if (type == 'Create') {
      console.log('create client request')

      console.log({
        clientId,
        clientSecret,
        authMethod,
        authGrantType,
        redirectUri
      })
    }

    if (type == 'Update' && id != null) {
      console.log('update client request: ' + id)

      console.log({
        clientId,
        clientSecret,
        authMethod,
        authGrantType,
        redirectUri
      })
    }

    setShowClientForm(!showClientForm)
  }

  const updateArr = (predicate: boolean, value: string, state: string[], setter: any) => {
    if (!predicate) {
      setter([...state, value])
    } else {
      setter([...state.filter(el => el != value)])
    }
  }

  const handleAuthMethodCheckbox = (val: string) => {
    if (val == 'client_secret_basic') {
      setClient_secret_basicChecked(!client_secret_basicChecked)

      updateArr(client_secret_basicChecked, 'client_secret_basic', authMethod, setAuthMethod)
    }
    if (val == 'client_secret_post') {
      setclient_secret_postChecked(!client_secret_postChecked)

      updateArr(client_secret_postChecked, 'client_secret_post', authMethod, setAuthMethod)
    }
  }

  const handleAuthGrantTypeCheckbox = (val: string) => {
    if (val == 'authorization_code') {
      setAuthorization_codeChecked(!authorization_codeChecked)

      updateArr(authorization_codeChecked, 'authorization_code', authGrantType, setAuthGrantType)
    }
    if (val == 'client_credentials') {
      setClient_credentialsChecked(!client_credentialsChecked)

      updateArr(client_credentialsChecked, 'client_credentials', authGrantType, setAuthGrantType)
    }
  }

  return (
    <div className='client-form-container'>
      <form className='client-form' onSubmit={handleSubmit}>
        <div className="form-group">
          <label className='label'>Client ID<span className="required">*</span></label>
          <input type="text" required className="form-input" onChange={e => setClientId(e.target.value)} value={clientId} />
        </div>
        <div className="form-group">
          <label className='label'>Client secret<span className="required">*</span></label>
          <input type="text" required className="form-input" onChange={e => setClientSecret(e.target.value)} value={clientSecret} />
        </div>
        <div className="form-group">
          <label className='label'>Auth method<span className="required">*</span></label>
          <input type="checkbox" name="client_secret_basic" value="client_secret_basic" onChange={() => handleAuthMethodCheckbox('client_secret_basic')}/>
          <label> client_secret_basic</label><br/>
          <input type="checkbox" name="client_secret_post" value="client_secret_post" onChange={() => handleAuthMethodCheckbox('client_secret_post')}/>
          <label> client_secret_post</label><br/>
        </div>
        <div className="form-group">
          <label className='label'>Auth grant type<span className="required">*</span></label>
          <input type="checkbox" name="authorization_code" value="authorization_code" onChange={() => handleAuthGrantTypeCheckbox('authorization_code')}/>
          <label> authorization_code</label><br/>
          <input type="checkbox" name="client_credentials" value="client_credentials" onChange={() => handleAuthGrantTypeCheckbox('client_credentials')}/>
          <label> client_credentials</label><br/>
        </div>
        <div className="form-group">
          <label className='label'>Redirect URI<span className="required">*</span></label>
          <input type="text" required className="form-input" onChange={e => setRedirectUri(e.target.value)} value={redirectUri} />
        </div>
        <div className='form-group-btn'>
          <button className='btn btn-primary'>{type}</button>
          <button className='btn btn-cancel' onClick={() => setShowClientForm(!showClientForm)}>Cancel</button>
        </div>
      </form>
    </div>
  )
}

export default ClientForm