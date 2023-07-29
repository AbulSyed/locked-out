import './AppCard.scss'
import { ArrowRightOutlined, DeleteOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';

interface CardProps {
  title: string,
  description: string,
  to: string
}

const Card: React.FC<CardProps> = ({ title, description, to }) => {
  return ( 
    <div className="app-card">
      <div className="app-card-top-and-bottom p-1">
        <div>
          <div className='app-card-top'>
            <h2>{title}title</h2>
            <DeleteOutlined />
          </div>
          <hr />
          <p className='parag'></p>
        </div>
        <div>
          <hr className='hr' />
          <Link className='bottom-card' to={to} >
            <p className='bottom-card-p'>Go to</p>
            <ArrowRightOutlined />
          </Link>
        </div>
      </div>
    </div>
  );
}
 
export default Card;