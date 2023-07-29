import './CreateAppForm.scss'

interface CreateAppFormInterface {
  showAppForm: boolean,
  setShowAppForm: any
}

const CreateAppForm: React.FC<CreateAppFormInterface> = ({ showAppForm, setShowAppForm }) => {
  return (
    <div>Form</div>
  )
}

export default CreateAppForm;