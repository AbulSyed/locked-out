import './RoleAuthCard.scss'

import { useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from '../../../store/hooks'

interface RoleAuthCardInterface {
  userId: string;
  setShowRoleAuthForm: any;
}

interface Role {
  id: string;
  name: string;
}

const RoleAuthCard: React.FC<RoleAuthCardInterface> = ({ userId, setShowRoleAuthForm }) => {
  const roles = useAppSelector(state => state.role.roles)
  const users = useAppSelector(state => state.user.users)
  const currUserRoles = users.find(el => el.id == userId)?.roles
  const authorities = useAppSelector(state => state.authority.authorities)
  const [rolesToAdd, setRolesToAdd] = useState([])
  const dispatch = useAppDispatch()

  const addRoleToSet = (role: Role) => {
    const roleExists = isRolePresent(role)

    if (roleExists) {
      console.log('exists')
      const filteredArr = rolesToAdd.filter(existingRole => existingRole.id != role.id)
      setRolesToAdd(filteredArr)
    } else {
      console.log('doesn\'t exist')
      setRolesToAdd([...rolesToAdd, role])
    }
  }

  const isRolePresent = (role: Role) => {
    return rolesToAdd.some(existingRole => existingRole.name == role.name)
  }

  useEffect(() => {
    if (currUserRoles) {
      setRolesToAdd(currUserRoles)
    }
  }, [])

  useEffect(() => {
    console.log(rolesToAdd)
  }, [rolesToAdd])

  const addAuthorityToSet = (authority: any) => {
    console.log(authority)
  }

  const handleSubmit = (e: any) => {
    e.preventDefault()
  }

  return (
    <div className='role-auth-container'>
      <form className='role-auth' onSubmit={handleSubmit}>
        <div>
          {
            roles && roles.map(role => (
              <p 
                key={role.id}
                className={isRolePresent(role) ? 'category role active' : 'category role'}
                onClick={() => addRoleToSet(role)}
              >
                {role.name}
              </p>
            ))
          }
          <br />
          {
            authorities && authorities.map(authority => (
              <p
                key={authority.id}
                className='category authority'
                onClick={() => addAuthorityToSet(authority)}
              >
                {authority.name}
              </p>
            ))
          }
        </div>
        <div className='form-group-btn'>
          <button className='btn btn-primary'>Update</button>
          <button className='btn btn-cancel' onClick={() => setShowRoleAuthForm(false)}>Cancel</button>
        </div>
      </form>
    </div>
  )
}

export default RoleAuthCard