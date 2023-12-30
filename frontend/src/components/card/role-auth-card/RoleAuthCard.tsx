import './RoleAuthCard.scss'

import { useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from '../../../store/hooks'
import { alterUserAuthority, alterUserRoles } from '../../../store/user/userSlice';
import { alterClientAuthority, alterClientRoles } from '../../../store/client/clientSlice';

interface RoleAuthCardInterface {
  type: string;
  userId?: string;
  clientId?: string;
  setShowRoleAuthForm: any;
}

interface Role {
  id: string;
  name: string;
}

interface Authority {
  id: string;
  name: string;
}

const RoleAuthCard: React.FC<RoleAuthCardInterface> = ({ type, userId, clientId, setShowRoleAuthForm }) => {
  const users = useAppSelector(state => state.user.users)
  const clients = useAppSelector(state => state.client.clients)
  const roles = useAppSelector(state => state.role.roles)
  const authorities = useAppSelector(state => state.authority.authorities)

  const currUserRoles = users.find(el => el.id == userId)?.roles
  const currClientRoles = clients.find(el => el.id == clientId)?.roles
  
  const currUserAuthority = users.find(el => el.id == userId)?.authorities
  const currClientAuthority = clients.find(el => el.id == clientId)?.authorities
  
  const [rolesToAdd, setRolesToAdd] = useState([])
  const [authorityToAdd, setAuthorityToAdd] = useState([])
  
  const dispatch = useAppDispatch()

  const addRoleToSet = (role: Role) => {
    const roleExists = isRolePresent(role)

    if (roleExists) {
      const filteredArr = rolesToAdd.filter(existingRole => existingRole.id != role.id)
      setRolesToAdd(filteredArr)
    } else {
      setRolesToAdd([...rolesToAdd, role])
    }
  }

  const isRolePresent = (role: Role) => {
    return rolesToAdd.some(existingRole => existingRole.name == role.name)
  }

  useEffect(() => {
    if (type == 'USER' && currUserRoles) {
      setRolesToAdd(currUserRoles)
    }

    if (type == 'CLIENT' && currClientRoles) {
      setRolesToAdd(currClientRoles)
    }
  }, [])

  const addAuthorityToSet = (authority: any) => {
    const authorityExists = isAuthorityPresent(authority)

    if (authorityExists) {
      const filteredArr = authorityToAdd.filter(existingAuthority => existingAuthority.id != authority.id)
      setAuthorityToAdd(filteredArr)
    } else {
      setAuthorityToAdd([...authorityToAdd, authority])
    }
  }

  const isAuthorityPresent = (authority: Authority) => {
    return authorityToAdd.some(existingAuthority => existingAuthority.name == authority.name)
  }

  useEffect(() => {
    if (type == 'USER' && currUserAuthority) {
      setAuthorityToAdd(currUserAuthority)
    }

    if (type == 'CLIENT' && currClientAuthority) {
      setAuthorityToAdd(currClientAuthority)
    }
  }, [])

  const handleSubmit = (e: any) => {
    e.preventDefault()
    
    const roleIds = rolesToAdd.map(el => el.id)
    const authorityIds = authorityToAdd.map(el => el.id)

    if (type == 'USER') {
      dispatch(alterUserRoles({
        userId,
        roleIds,
        rolesToAdd
      }))

      dispatch(alterUserAuthority({
        userId,
        authorityIds,
        authorityToAdd
      }))

      setShowRoleAuthForm(false)
    }

    if (type == 'CLIENT') {
      dispatch(alterClientRoles({
        clientId,
        roleIds,
        rolesToAdd
      }))

      dispatch(alterClientAuthority({
        clientId,
        authorityIds,
        authorityToAdd
      }))

      setShowRoleAuthForm(false)
    }
  }

  return (
    <div className='role-auth-container'>
      <form className='role-auth' onSubmit={handleSubmit}>
        <div>
          {
            roles && roles.map(role => (
              <p 
                key={role.id}
                className={isRolePresent(role) ? 'category role active-role' : 'category role'}
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
                className={isAuthorityPresent(authority) ? 'category authority active-authority' : 'category authority'}
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