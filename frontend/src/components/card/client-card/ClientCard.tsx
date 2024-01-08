import './ClientCard.scss'

import ClientForm from '../../form/client-form/ClientForm'
import RoleAuthCard from '../role-auth-card/RoleAuthCard'
import ScopesCard from '../scopes-card/ScopesCard'

import { IdcardOutlined, AimOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons'
import { useState } from 'react'
import { useAppDispatch } from '../../../store/hooks'
import { deleteClient } from '../../../store/client/clientSlice'
import { message, Modal, Tooltip } from 'antd'

interface ClientCardProps {
  id: string;
  clientId: string;
  clientSecret: string;
  roles: Role[];
  authorities: Authority[];
  scopes: string[];
  authMethod: string[];
  authGrantType: string[];
  redirectUri: string;
  createdAt: string;
}

interface Role {
  id: string;
  name: string;
}

interface Authority {
  id: string;
  name: string;
}

const ClientCard: React.FC<ClientCardProps> = ({ id, clientId, clientSecret, roles, authorities, scopes, authMethod, authGrantType, redirectUri, createdAt }) => {
  const [showClientForm, setShowClientForm] = useState(false)
  const [showRoleAuthForm, setShowRoleAuthForm] = useState(false)
  const [showScopesCard, setShowScopesCard] = useState(false)
  const [isModalOpen, setIsModalOpen] = useState(false)
  
  const [messageApi, contextHolder] = message.useMessage()

  const activeApp = location.pathname.split('/')[2]

  const dispatch = useAppDispatch()

  // endpoint should be used when redirecting a user to login page
  const generateAuthorizeEndpoint = () => {
    if (authGrantType.includes('AUTHORIZATION_CODE')) {
      const authendpoint =  `http://localhost:8080/oauth2/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}&appname=${activeApp}${scopes.length >= 1 ? '&scope=' : ''}${scopes.join('%20').toLowerCase()}`
      navigator.clipboard.writeText(authendpoint)
      showSuccessPopup('Authorize endpoint copied - GET')
    } else {
      errorPopup('The oauth2/authorize endpoint requires client to have AUTHORIZATION_CODE grant type')
    }
  }

  // endpoint should be used to obtain an access token
  const generateTokenEndpoint = () => {
    if (authGrantType.includes('AUTHORIZATION_CODE')) {
      const tokenEndpoint = `http://localhost:8080/oauth2/token?client_id=${clientId}&redirect_uri=${redirectUri}&grant_type=authorization_code&code=YOUR_AUTH_CODE&appname=${activeApp}`
      navigator.clipboard.writeText(tokenEndpoint)
      showSuccessPopup('Token endpoint copied - POST')
    } else {
      errorPopup('The oauth2/token endpoint requires client to have AUTHORIZATION_CODE grant type')
    }
  }

  // endpoint should be used to obtain an access token via client_credentials grant flow
  const generateClientCredentialsEndpoint = () => {
    if (authGrantType.includes('CLIENT_CREDENTIALS')) {
      const ccEndpoint = `http://localhost:8080/oauth2/token?client_id=${clientId}&grant_type=client_credentials&appname=${activeApp}`
      navigator.clipboard.writeText(ccEndpoint)
      showSuccessPopup('Client credentials endpoint copied - POST')
    } else {
      errorPopup('This endpoint requires client to have CLIENT_CREDENTIALS grant type')
    }
  }

  const showSuccessPopup = (message: string) => {
    messageApi.open({
      type: 'success',
      content: message,
    })
  }

  const errorPopup = (message: string) => {
    messageApi.open({
      type: 'error',
      content: message,
    })
  }

  const showModal = () => {
    setIsModalOpen(true)
  }

  const handleOk = () => {
    setIsModalOpen(false)
    dispatch(deleteClient(id))
  }

  const handleCancel = () => {
    setIsModalOpen(false)
  }

  return ( 
    <div>
      {contextHolder}
      {
        // condition 1
        // show card with values
        !showClientForm && !showRoleAuthForm && !showScopesCard ? (
          <div className="client-card">
            <div className="client-card-top-and-bottom p-1">
              <div>
                <div className='client-card-top'>
                  <Tooltip title="Client ID">
                    <p>{clientId}</p>
                  </Tooltip>
                  <div>
                    <IdcardOutlined
                      className='client-card-icon'
                      onClick={() => setShowRoleAuthForm(true)}
                    />
                    <AimOutlined
                      className='client-card-icon'
                      onClick={() => setShowScopesCard(true)}
                    />
                    <EditOutlined
                      className='client-card-icon'
                      onClick={() => setShowClientForm(true)}
                    />
                    <DeleteOutlined
                      className='client-card-icon'
                      onClick={showModal}
                    />
                    <Modal
                      title="Deletion"
                      open={isModalOpen}
                      onOk={handleOk}
                      onCancel={handleCancel}
                    >
                      <p>Are you sure, you want to delete client with id: {id}?</p>
                    </Modal>
                  </div>
                </div>
                <div style={{ 
                  'display': 'flex',
                  'justifyContent': 'space-between',
                  'marginBottom': '.5rem'
                }}>
                  <Tooltip title="Client secret">
                    <span className='parag'>{clientSecret} </span>
                  </Tooltip>
                  <Tooltip title="Redirect uri">
                    <span className='parag'> {redirectUri}</span>
                  </Tooltip>
                </div>
                {
                  authMethod && authMethod.map(am => (
                    <Tooltip title="Auth method" color={'#5f6991'}>
                      <p key={am} className='category am'>{am}</p>
                    </Tooltip>
                  ))
                }
                {
                  authGrantType && authGrantType.map(agt => (
                    <Tooltip title="Grant type" color={'#5100ff'}>
                      <p key={agt} className='category agt'>{agt}</p>
                    </Tooltip>
                  ))
                }
                {
                  roles && roles.map(role => (
                    <Tooltip title="Role" color={'#0033ff'}>
                      <p key={role.id} className='category role'>{role.name}</p>
                    </Tooltip>
                  ))
                }
                {
                  authorities && authorities.map(authority => (
                    <Tooltip title="Authority" color={'#28089b'}>
                      <p key={authority.id} className='category authority'>{authority.name}</p>
                    </Tooltip>
                  ))
                }
                {
                  scopes && scopes.map(scope => (
                    <Tooltip title="Authority" color={'#9b8308'}>
                      <p key={scope} className='category scope'>{scope}</p>
                    </Tooltip>
                  ))
                }
              </div>
            </div>
            <div style={{ 
              'display': 'flex',
              'justifyContent': 'space-around',
              'marginBottom': '1rem'
              }}>
                <button 
                  className='btn btn-secondary' 
                  onClick={() => generateAuthorizeEndpoint()}
                >
                  Authorize endpoint
                </button>
                <button 
                  className='btn btn-secondary'
                  onClick={() => generateTokenEndpoint()}
                >
                  Token endpoint
                </button>
            </div>
            <div style={{ 
              'display': 'flex',
              'justifyContent': 'space-around',
              'marginBottom': '1rem'
              }}>
                <button 
                  className='btn btn-secondary'
                  onClick={() => generateClientCredentialsEndpoint()}
                >
                  Client credentials endpoint
                </button>
            </div>
          </div>
        ) : 

        // condition 2
        // show card to allow user to add roles/authorities
        !showClientForm && showRoleAuthForm ? (
          <RoleAuthCard
            type='CLIENT'
            clientId={id}
            setShowRoleAuthForm={setShowRoleAuthForm}
          />
        ) :

        // condition 3
        // show card to allow user to add scopes
        !showClientForm && showScopesCard ? 
        (
          <ScopesCard
            clientId={id}
            setShowScopesCard={setShowScopesCard}
          />
        ) :

        // condition 4
        // show edit form
        (
          <ClientForm
            type='Update'
            id={id}
            initClientId={clientId}
            initClientSecret={clientSecret}
            initAuthMethod={authMethod}
            initAuthGrantType={authGrantType}
            initRedirectUri={redirectUri}
            showClientForm={showClientForm}
            setShowClientForm={setShowClientForm}
            
            initClient_secret_basicChecked={authMethod.includes('CLIENT_SECRET_BASIC')}
            initClient_secret_postChecked={authMethod.includes('CLIENT_SECRET_POST')}
            initAuthorization_codeChecked={authGrantType.includes('AUTHORIZATION_CODE')}
            initClient_credentialsChecked={authGrantType.includes('CLIENT_CREDENTIALS')}
          />
        )
      }
    </div>
  );
}
 
export default ClientCard