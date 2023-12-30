import './UserForm.scss'

import { useState } from 'react'
import { useAppDispatch, useAppSelector } from '../../../store/hooks'
import { createUser, updateUser } from '../../../store/user/userSlice';

interface UserFormInterface {
  type: string;
  id?: string;
  initUserame?: string;
  initPassword?: string;
  initEmail?: string;
  initPhoneNumber?: string;
  showUserForm: boolean;
  setShowUserForm: any;
}

const UserForm: React.FC<UserFormInterface> = ({ type, id, initUserame, initPassword, initEmail, initPhoneNumber, showUserForm, setShowUserForm }) => {
  const [username, setUsername] = useState(initUserame)
  const [password, setPassword] = useState(initPassword)
  const [email, setEmail] = useState(initEmail)
  const [phoneNumber, setPhoneNumber] = useState(initPhoneNumber)
  
  const appId = useAppSelector(state => state.app.appDetails.id)

  const dispatch = useAppDispatch()

  const handleSubmit = (e: any) => {
    e.preventDefault()

    if (type == 'Create') {
      console.log('create user request')
      // made fields optional to error removed from DefaultUserCard
      // need to resolve in a better way
      dispatch(createUser({
        appId,
        username,
        password,
        email,
        phoneNumber
      }))
    }

    if (type == 'Update' && id != null) {
      console.log('update user request: ' + id)
      dispatch(updateUser({
        id,
        username,
        password,
        email,
        phoneNumber
      }))
    }

    setShowUserForm(!showUserForm)
  }

  return (
    <div className='user-form-container'>
      <form className='user-form' onSubmit={handleSubmit}>
        <div className="form-group">
          <label className='label'>Username<span className="required">*</span></label>
          <input type="text" required className="form-input" onChange={e => setUsername(e.target.value)} value={username} />
        </div>
        <div className="form-group">
          <label className='label'>Password<span className="required">*</span></label>
          <input type="text" required className="form-input" onChange={e => setPassword(e.target.value)} value={password} />
        </div>
        <div className="form-group">
          <label className='label'>Email<span className="required">*</span></label>
          <input type="email" required className="form-input" onChange={e => setEmail(e.target.value)} value={email} />
        </div>
        <div className="form-group">
          <label className='label'>Phone number<span className="required">*</span></label>
          <input type="number" required className="form-input" onChange={e => setPhoneNumber(e.target.value)} value={phoneNumber} />
        </div>
        <div className='form-group-btn'>
          <button className='btn btn-primary'>{type}</button>
          <button className='btn btn-cancel' onClick={() => setShowUserForm(!showUserForm)}>Cancel</button>
        </div>
      </form>
    </div>
  )
}

export default UserForm