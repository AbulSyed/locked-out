import './CreateAppForm.scss'

interface CreateAppFormInterface {
  showAppForm: boolean,
  setShowAppForm: any
}

const CreateAppForm: React.FC<CreateAppFormInterface> = ({ showAppForm, setShowAppForm }) => {
  const handleSubmit = (e: any) => {
    e.preventDefault()
    console.log('form submitted')
    setShowAppForm(!showAppForm)
  }

  return (
    <div className='create-app-form-container'>
      <form className='create-app-form' onSubmit={handleSubmit}>
        <div className="form-group">
          <label className='label'>Name<span className="required">*</span></label>
          <input type="text" required className="form-input" />
        </div>
        <div className="form-group">
          <label className='label'>Description<span className="required">*</span></label>
          <input type="text" required className="form-input" />
        </div>
        <div className='form-group-btn'>
          <button className='btn btn-primary'>Submit</button>
          <button className='btn btn-cancel' onClick={() => setShowAppForm(!showAppForm)}>Cancel</button>
        </div>
      </form>
    </div>
  )
}

export default CreateAppForm;