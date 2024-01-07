import './ScopesCard.scss'

import { useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from '../../../store/hooks'
import { alterClientScopes } from '../../../store/client/clientSlice';

interface ScopesCardInterface {
  clientId: string;
  setShowScopesCard: any;
}

const ScopesCard: React.FC<ScopesCardInterface> = ({ clientId, setShowScopesCard }) => {
  const clients = useAppSelector(state => state.client.clients)
  const scopes = ['OPENID', 'EMAIL', 'PHONE']

  const currClientScopes = clients.find(el => el.id == clientId)?.scopes
  
  const [scopesToAdd, setScopesToAdd] = useState([])
  
  const dispatch = useAppDispatch()

  const addScopeToSet = (scope: string) => {
    const scopeExists = isScopePresent(scope)

    if (scopeExists) {
      const filteredArr = scopesToAdd.filter(existingScope => existingScope != scope)
      setScopesToAdd(filteredArr)
    } else {
      setScopesToAdd([...scopesToAdd, scope])
    }
  }

  const isScopePresent = (scope: string) => {
    return scopesToAdd.some(existingScope => existingScope == scope)
  }

  useEffect(() => {
    if (currClientScopes) {
      setScopesToAdd(currClientScopes)
    }
  }, [])

  const handleSubmit = (e: any) => {
    e.preventDefault()

    const data = {
      clientId,
      scopesToAdd
    }

    dispatch(alterClientScopes(data))

    setShowScopesCard(false)
  }

  return (
    <div className='scope-container'>
      <form className='scope' onSubmit={handleSubmit}>
        <div>
          {
            scopes && scopes.map(scope => (
              <p 
                key={scope}
                className={isScopePresent(scope) ? 'category scope active-scope' : 'category scope'}
                onClick={() => addScopeToSet(scope)}
              >
                {scope}
              </p>
            ))
          }
        </div>
        <div className='form-group-btn'>
          <button className='btn btn-primary'>Update</button>
          <button className='btn btn-cancel' onClick={() => setShowScopesCard(false)}>Cancel</button>
        </div>
      </form>
    </div>
  )
}

export default ScopesCard