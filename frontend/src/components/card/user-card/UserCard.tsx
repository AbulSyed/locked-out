import './UserCard.scss'

import UserForm from '../../form/user-form/UserForm'

import { DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { useState } from 'react'
import { useAppDispatch } from '../../../store/hooks'
import { deleteUser } from '../../../store/user/userSlice'

interface UserCardProps {
  id: string;
  username: string;
  password: string;
  email: string;
  phoneNumber: string;
  roles: Role[];
  authorities: Authority[];
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

const UserCard: React.FC<UserCardProps> = ({ id, username, password, email, phoneNumber, roles, authorities }) => {
  const [showUserForm, setShowUserForm] = useState(false)
  const dispatch = useAppDispatch()

  const handleDelete = (id: string) => {
    alert('Are you sure, you want to delete user with id: ' + id + '?')

    dispatch(deleteUser(id))
  }

  return ( 
    <div>
      {
        !showUserForm ? (
          <div className="user-card">
            <div className="user-card-top-and-bottom p-1">
              <div>
                <div className='user-card-top'>
                  <p>{username}</p>
                  <div>
                    <EditOutlined className='user-card-icon' onClick={() => setShowUserForm(true)} />
                    <DeleteOutlined className='user-card-icon' onClick={() => handleDelete(id)} />
                  </div>
                </div>
                <p className='parag'>Email: {email}</p>
                <p className='parag'>Password: {password}</p>
                <p className='parag'>Phone: {phoneNumber}</p>
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
              </div>
              <div>
              </div>
            </div>
          </div>
        ) : 
          <UserForm
            type='Update'
            id={id}
            initUserame={username}
            initPassword={password}
            initEmail={email}
            initPhoneNumber={phoneNumber}
            showUserForm={showUserForm}
            setShowUserForm={setShowUserForm}
          />
      }
    </div>
  );
}
 
export default UserCard