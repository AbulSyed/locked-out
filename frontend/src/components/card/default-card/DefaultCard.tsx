import './DefaultCard.scss'
import { useState } from 'react'
import { PlusOutlined } from '@ant-design/icons';
import CreateAppForm from '../../form/create-app/CreateAppForm';

const DefaultCard: React.FC = () => {
  const [showAppForm, setShowAppForm] = useState(true);

  return (
    <div>
      {
        showAppForm ?
        <div className="default-card">
            <div className="default-card-top-and-bottom p-1">
                <div>
                  <div className='default-card-top'>
                    <h2>Create an App</h2>
                    <PlusOutlined className='icon' onClick={() => setShowAppForm(!showAppForm)} />
                  </div>
                  <hr />
                  <p className='desc'>An App represents a resource that requires authentication & authorization.</p>
                </div>
                <div>
                  <hr className='hr' />
                  <div className='botton-icon'>
                    
                  </div>
                </div>
            </div>
        </div> :
        <CreateAppForm showAppForm={showAppForm} setShowAppForm={setShowAppForm} />
      }
    </div>
  )
}

export default DefaultCard;