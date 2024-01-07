import './AppCard.scss'

import AppForm from '../../form/app-form/AppForm'

import { ArrowRightOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { useAppDispatch } from '../../../store/hooks'
import { deleteApp } from '../../../store/app/appSlice'
import { Modal } from 'antd'

interface AppCardProps {
  id: string;
  title: string;
  description: string;
  to: string;
}

const AppCard: React.FC<AppCardProps> = ({ id, title, description, to }) => {
  const [showAppForm, setShowAppForm] = useState(false)
  const [isModalOpen, setIsModalOpen] = useState(false)
  
  const dispatch = useAppDispatch()

  const showModal = () => {
    setIsModalOpen(true)
  }

  const handleOk = () => {
    setIsModalOpen(false)
    dispatch(deleteApp(id))
  }

  const handleCancel = () => {
    setIsModalOpen(false)
  }

  return ( 
    <div>
      {
        !showAppForm ? (
          <div className="app-card">
            <div className="app-card-top-and-bottom p-1">
              <div>
                <div className='app-card-top'>
                  <h2>{title}</h2>
                  <div>
                    <EditOutlined
                      className='app-card-icon'
                      onClick={() => setShowAppForm(true)}
                    />
                    <DeleteOutlined
                      className='app-card-icon'
                      onClick={showModal}
                    />
                    <Modal
                      title="Deletion"
                      open={isModalOpen}
                      onOk={handleOk}
                      onCancel={handleCancel}
                    >
                      <p>Are you sure, you want to delete app with id: {id}?</p>
                    </Modal>
                  </div>
                </div>
                <hr />
                <p className='parag'>{description}</p>
              </div>
              <div>
                <hr className='hr' />
                <Link className='bottom-card' to={'/apps' + to + '/overview'} >
                  <p className='bottom-card-p'>Go to</p>
                  <ArrowRightOutlined />
                </Link>
              </div>
            </div>
          </div>
        ) : <AppForm 
              type='Update'
              id={id}
              initName={title}
              initDesc={description}
              showAppForm={showAppForm}
              setShowAppForm={setShowAppForm}
            />
      }
    </div>
  );
}
 
export default AppCard