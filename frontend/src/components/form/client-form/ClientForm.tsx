import './ClientForm.scss'

import { useState } from 'react'
import { useAppDispatch, useAppSelector } from '../../../store/hooks'
import { createClient, updateClient } from '../../../store/client/clientSlice';

interface ClientFormInterface {
  type: string;
  id?: string;
  initClientId: string;
  initClientSecret: string;
  initAuthMethod: string[];
  initAuthGrantType: string[];
  initRedirectUri: string;
  showClientForm: boolean;
  setShowClientForm: any;

  initClient_secret_basicChecked: boolean;
  initClient_secret_postChecked: boolean;
  initAuthorization_codeChecked: boolean;
  initClient_credentialsChecked: boolean;
}

const ClientForm: React.FC<ClientFormInterface> = ({ type, id, initClientId, initClientSecret, initAuthMethod, initAuthGrantType, initRedirectUri, showClientForm, setShowClientForm, initClient_secret_basicChecked, initClient_secret_postChecked, initAuthorization_codeChecked, initClient_credentialsChecked }) => {
  const [clientId, setClientId] = useState(initClientId)
  const [clientSecret, setClientSecret] = useState(initClientSecret)
  const [authMethod, setAuthMethod] = useState(initAuthMethod)
  const [authGrantType, setAuthGrantType] = useState(initAuthGrantType)
  const [redirectUri, setRedirectUri] = useState(initRedirectUri)

  const [client_secret_basicChecked, setClient_secret_basicChecked] = useState(initClient_secret_basicChecked)
  const [client_secret_postChecked, setclient_secret_postChecked] = useState(initClient_secret_postChecked)
  const [authorization_codeChecked, setAuthorization_codeChecked] = useState(initAuthorization_codeChecked)
  const [client_credentialsChecked, setClient_credentialsChecked] = useState(initClient_credentialsChecked)

  const appId = useAppSelector(state => state.app.appDetails.id)
  
  const dispatch = useAppDispatch()

  const handleSubmit = (e: any) => {
    e.preventDefault()

    if (type == 'Create') {
      console.log('create client request')

      const data = {
        appId,
        clientId,
        clientSecret,
        authMethod,
        authGrantType,
        redirectUri
      }

      dispatch(createClient(data))
    }

    if (type == 'Update' && id != null) {
      console.log('update client request: ' + id)

      const data = {
        id,
        clientId,
        clientSecret,
        authMethod,
        authGrantType,
        redirectUri
      }

      dispatch(updateClient(data))
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
    if (val == 'CLIENT_SECRET_BASIC') {
      setClient_secret_basicChecked(!client_secret_basicChecked)

      updateArr(client_secret_basicChecked, 'CLIENT_SECRET_BASIC', authMethod, setAuthMethod)
    }
    if (val == 'CLIENT_SECRET_POST') {
      setclient_secret_postChecked(!client_secret_postChecked)

      updateArr(client_secret_postChecked, 'CLIENT_SECRET_POST', authMethod, setAuthMethod)
    }
  }

  const handleAuthGrantTypeCheckbox = (val: string) => {
    if (val == 'AUTHORIZATION_CODE') {
      setAuthorization_codeChecked(!authorization_codeChecked)

      updateArr(authorization_codeChecked, 'AUTHORIZATION_CODE', authGrantType, setAuthGrantType)
    }
    if (val == 'CLIENT_CREDENTIALS') {
      setClient_credentialsChecked(!client_credentialsChecked)

      updateArr(client_credentialsChecked, 'CLIENT_CREDENTIALS', authGrantType, setAuthGrantType)
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
          <input type="checkbox" name="client_secret_basic" value="client_secret_basic" onChange={() => handleAuthMethodCheckbox('CLIENT_SECRET_BASIC')} checked={client_secret_basicChecked} />
          <label> client_secret_basic</label><br/>
          <input type="checkbox" name="client_secret_post" value="client_secret_post" onChange={() => handleAuthMethodCheckbox('CLIENT_SECRET_POST')} checked={client_secret_postChecked} />
          <label> client_secret_post</label><br/>
        </div>
        <div className="form-group">
          <label className='label'>Auth grant type<span className="required">*</span></label>
          <input type="checkbox" name="authorization_code" value="authorization_code" onChange={() => handleAuthGrantTypeCheckbox('AUTHORIZATION_CODE')} checked={authorization_codeChecked} />
          <label> authorization_code</label><br/>
          <input type="checkbox" name="client_credentials" value="client_credentials" onChange={() => handleAuthGrantTypeCheckbox('CLIENT_CREDENTIALS')} checked={client_credentialsChecked} />
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