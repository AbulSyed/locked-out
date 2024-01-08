import './UserCard.scss'

import UserForm from '../../form/user-form/UserForm'
import RoleAuthCard from '../role-auth-card/RoleAuthCard'

import { IdcardOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons'
import { useState } from 'react'
import { useAppDispatch } from '../../../store/hooks'
import { deleteUser } from '../../../store/user/userSlice'
import { Modal, Tooltip } from 'antd'

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
  const [isModalOpen, setIsModalOpen] = useState(false)

  const dispatch = useAppDispatch()

  const showModal = () => {
    setIsModalOpen(true)
  }

  const handleOk = () => {
    setIsModalOpen(false)
    dispatch(deleteUser(id))
  }

  const handleCancel = () => {
    setIsModalOpen(false)
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
                    <IdcardOutlined
                      className='user-card-icon'
                      onClick={() => setShowRoleAuthForm(true)}
                    />
                    <EditOutlined
                      className='user-card-icon'
                      onClick={() => setShowUserForm(true)}
                    />
                    <DeleteOutlined
                      className='user-card-icon'
                      onClick={showModal}
                    />
                    <Modal
                      title="Deletion"
                      open={isModalOpen}
                      onOk={handleOk}
                      onCancel={handleCancel}
                    >
                      <p>Are you sure, you want to delete user with id: {id}?</p>
                    </Modal>
                  </div>
                </div>
                <p className='parag'>Email: {email}</p>
                <p className='parag'>Password: {password}</p>
                <p className='parag'>Phone: {phoneNumber}</p>
                {
                  roles && roles.map(role => (
                    <Tooltip title="Role" color={'#0033ff'}>
                      <span key={role.id} className='category role'>{role.name}</span>
                    </Tooltip>
                  ))
                }
                {
                  authorities && authorities.map(authority => (
                    <Tooltip title="Authority" color={'#28089b'}>
                      <span key={authority.id} className='category authority'>{authority.name}</span>
                    </Tooltip>
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
            type='USER'
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