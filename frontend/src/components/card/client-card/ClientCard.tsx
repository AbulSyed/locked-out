import './ClientCard.scss'

import ClientForm from '../../form/client-form/ClientForm'
import RoleAuthCard from '../role-auth-card/RoleAuthCard'
import ScopesCard from '../scopes-card/ScopesCard'

import { IdcardOutlined, AimOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons'
import { useState } from 'react'
import { useAppDispatch } from '../../../store/hooks'
import { deleteClient } from '../../../store/client/clientSlice'

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

  const dispatch = useAppDispatch()

  const handleDelete = (id: string) => {
    alert('Are you sure, you want to delete client with id: ' + id + '?')

    dispatch(deleteClient(id))
  }

  return ( 
    <div>
      {
        // condition 1
        // show card with values
        !showClientForm && !showRoleAuthForm && !showScopesCard ? (
          <div className="client-card">
            <div className="client-card-top-and-bottom p-1">
              <div>
                <div className='client-card-top'>
                  <p>{clientId}</p>
                  <div>
                    <IdcardOutlined className='client-card-icon' onClick={() => setShowRoleAuthForm(true)} />
                    <AimOutlined className='client-card-icon' onClick={() => setShowScopesCard(true)} />
                    <EditOutlined className='client-card-icon' onClick={() => setShowClientForm(true)} />
                    <DeleteOutlined className='client-card-icon' onClick={() => handleDelete(id)} />
                  </div>
                </div>
                <p className='parag'>Secret: {clientSecret}</p>
                <p className='parag'>Redirect URI: {redirectUri}</p>
                <p className='parag'>Auth Method: {authMethod && authMethod.map(am => (
                  <>
                    <span key={am}>{am}</span>
                    <br />
                  </>
                ))}</p>
                <p className='parag'>Auth Grant Type: {authGrantType && authGrantType.map(agt => (
                  <>
                    <span key={agt}>{agt}</span>
                    <br />
                  </>
                ))}</p>
                {
                  roles && roles.map(role => (
                    <p key={role.id} className='category role'>{role.name}</p>
                  ))
                }
                <br />
                {
                  authorities && authorities.map(authority => (
                    <p key={authority.id} className='category authority'>{authority.name}</p>
                  ))
                }
                <br />
                {
                  scopes && scopes.map(scope => (
                    <p key={scope} className='category scope'>{scope}</p>
                  ))
                }
              </div>
              <div>
              </div>
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