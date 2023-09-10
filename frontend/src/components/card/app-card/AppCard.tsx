import './AppCard.scss'
import { ArrowRightOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import AppForm from '../../form/app-form/AppForm';

interface CardProps {
  id: string,
  title: string,
  description: string,
  to: string
}

const Card: React.FC<CardProps> = ({ title, description, to }) => {
  const [showAppForm, setShowAppForm] = useState(true);

  const handleDelete = () => {
    alert('Are you sure, you want to delete?')
  }

  return ( 
    <div>
      {
        showAppForm ? (
          <div className="app-card">
            <div className="app-card-top-and-bottom p-1">
              <div>
                <div className='app-card-top'>
                  <h2>{title}</h2>
                  <div>
                    <EditOutlined className='app-card-icon' onClick={() => setShowAppForm(false)} />
                    <DeleteOutlined className='app-card-icon' onClick={() => handleDelete()} />
                  </div>
                </div>
                <hr />
                <p className='parag'>{description}</p>
              </div>
              <div>
                <hr className='hr' />
                <Link className='bottom-card' to={'/apps' + to} >
                  <p className='bottom-card-p'>Go to</p>
                  <ArrowRightOutlined />
                </Link>
              </div>
            </div>
          </div>
        ) : <AppForm type='Update' initName={title} initDesc={description} showAppForm={showAppForm} setShowAppForm={setShowAppForm} />
      }
    </div>
  );
}
 
export default Card;