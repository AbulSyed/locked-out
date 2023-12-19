import './SimpleForm.scss'

import { useState } from 'react'
import { useAppDispatch } from '../../../store/hooks'
import { createRole } from '../../../store/role/roleSlice'
import { createAuthority } from '../../../store/authority/authoritySlice'

interface SimpleFormInterface {
  type: string
}

const SimpleForm: React.FC<SimpleFormInterface> = ({ type }) => {
  const [name, setName] = useState('')
  const dispatch = useAppDispatch()

  const handleSubmit = (e: any) => {
    e.preventDefault()

    if (type == 'Create role') {
      dispatch(createRole({
        name: name.toUpperCase()
      }))
    }

    if (type == 'Create authority') {
      dispatch(createAuthority({
        name: name.toLowerCase()
      }))
    }

    setName('')
  }

  return (
    <div className='simple-form-container'>
      <form className='simple-form' onSubmit={handleSubmit}>
        <div className='form-group'>
          <label className='label'>Name<span className='required'>*</span></label>
          <input type='text' required className='form-input' onChange={e => setName(e.target.value)} value={name} />
        </div>
        <div className='form-group-btn'>
          <button className='btn btn-primary'>{type}</button>
          <button className='btn btn-cancel' onClick={() => setName('')}>Clear</button>
        </div>
      </form>
    </div>
  )
}

export default SimpleForm