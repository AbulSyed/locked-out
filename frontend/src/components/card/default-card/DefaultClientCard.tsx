import './DefaultClientCard.scss'

import ClientForm from '../../form/client-form/ClientForm'

import { useState } from 'react'
import { PlusOutlined } from '@ant-design/icons'

interface DefaultClientCardProps {
}

const DefaultClientCard: React.FC<DefaultClientCardProps> = () => {
  const [showClientForm, setShowClientForm] = useState(true)

  return (
    <div>
      {
        showClientForm ?
        <div className="default-client-card">
            <div className="default-client-card-top-and-bottom p-1">
                <div>
                  <div className='default-client-card-top'>
                    <p>Create client</p>
                    <PlusOutlined
                      className='default-client-card-icon'
                      onClick={() => setShowClientForm(!showClientForm)}
                    />
                  </div>
                  <br />
                  <hr />
                  <p className='desc'>Clients make resource requests</p>
                  {/* <p>obtains tokens using the client_credentials grant flow.</p> */}
                </div>
            </div>
        </div> :
        <ClientForm
          type='Create'
          initClientId=""
          initClientSecret=""
          initAuthMethod={[]}
          initAuthGrantType={[]}
          initRedirectUri=""
          showClientForm={showClientForm}
          setShowClientForm={setShowClientForm}
        />
      }
    </div>
  )
}

export default DefaultClientCard