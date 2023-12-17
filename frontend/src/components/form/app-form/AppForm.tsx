import './AppForm.scss'

import { useState } from 'react'
import { useAppDispatch } from '../../../store/hooks'
import { createApp, updateApp } from '../../../store/app/appSlice'

interface CreateAppFormInterface {
  type: string,
  id?: string,
  initName: string,
  initDesc: string,
  showAppForm: boolean,
  setShowAppForm: any
}

const CreateAppForm: React.FC<CreateAppFormInterface> = ({ type, id, initName, initDesc, showAppForm, setShowAppForm }) => {
  const [name, setName] = useState(initName)
  const [desc, setDesc] = useState(initDesc)
  const dispatch = useAppDispatch()

  const handleSubmit = (e: any) => {
    e.preventDefault()

    if (type == 'Create') {
      console.log('create request')
      dispatch(createApp({
        name: name,
        description: desc
      }))
    }

    if (type == 'Update' && id != null) {
      console.log('update request')
      dispatch(updateApp({
        id,
        name: name,
        description: desc
      }))
    }

    setShowAppForm(!showAppForm)
  }

  return (
    <div className='create-app-form-container'>
      <form className='create-app-form' onSubmit={handleSubmit}>
        <div className="form-group">
          <label className='label'>Name<span className="required">*</span></label>
          <input type="text" required className="form-input" onChange={e => setName(e.target.value)} value={name} />
        </div>
        <div className="form-group">
          <label className='label'>Description<span className="required">*</span></label>
          <input type="text" required className="form-input" onChange={e => setDesc(e.target.value)} value={desc} />
        </div>
        <div className='form-group-btn'>
          <button className='btn btn-primary'>{type}</button>
          <button className='btn btn-cancel' onClick={() => setShowAppForm(!showAppForm)}>Cancel</button>
        </div>
      </form>
    </div>
  )
}

export default CreateAppForm