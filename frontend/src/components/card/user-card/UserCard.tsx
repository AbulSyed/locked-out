import './UserCard.scss'

import UserForm from '../../form/user-form/UserForm'

import { AimOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons'
import { useState } from 'react'
import { useAppDispatch } from '../../../store/hooks'
import { deleteUser } from '../../../store/user/userSlice'
import RoleAuthCard from '../role-auth-card/RoleAuthCard'

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
  const [showRoleAuthForm, setShowRoleAuthForm] = useState(false)
  const dispatch = useAppDispatch()

  const handleAddRoleAuth = () => {
    // show users all roles & authorities
    // allow user to pick which roles/authorities to add
    console.log('hi')
  }

  const handleDelete = (id: string) => {
    alert('Are you sure, you want to delete user with id: ' + id + '?')

    dispatch(deleteUser(id))
  }

  return ( 
    <div>
      {
        // condition 1
        // show card with values
        !showUserForm && !showRoleAuthForm ? (
          <div className="user-card">
            <div className="user-card-top-and-bottom p-1">
              <div>
                <div className='user-card-top'>
                  <p>{username}</p>
                  <div>
                    <AimOutlined className='user-card-icon' onClick={() => setShowRoleAuthForm(true)} />
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
        // condition 2
        // show card to allow user to add roles/authorities
        !showUserForm && showRoleAuthForm ? (
          <RoleAuthCard
            userId={id}
            setShowRoleAuthForm={setShowRoleAuthForm}
          />
        ) :
        // condition 3
        // show edit form
        (
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
        )
      }
    </div>
  );
}
 
export default UserCard