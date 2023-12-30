import './DefaultUserCard.scss'

import UserForm from '../../form/user-form/UserForm'

import { useState } from 'react'
import { PlusOutlined } from '@ant-design/icons'

interface DefaultUserCardProps {
}

const DefaultUserCard: React.FC<DefaultUserCardProps> = () => {
  const [showUserForm, setShowUserForm] = useState(true)

  return (
    <div>
      {
        showUserForm ?
        <div className="default-user-card">
            <div className="default-user-card-top-and-bottom p-1">
                <div>
                  <div className='default-user-card-top'>
                    <p>Create user</p>
                    <PlusOutlined
                      className='default-user-card-icon'
                      onClick={() => setShowUserForm(!showUserForm)}
                    />
                  </div>
                  <br />
                  <hr />
                  <p className='desc'>Users will obtain an access tokens</p>
                  <p>using the authorization_code grant flow.</p>
                </div>
            </div>
        </div> :
        <UserForm
          type='Create'
          showUserForm={showUserForm}
          setShowUserForm={setShowUserForm}
        />
      }
    </div>
  )
}

export default DefaultUserCard