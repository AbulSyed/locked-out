import './Card.scss'
import { ArrowRightOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';

const Card = ({ title, description, to }) => {
return ( 
    <div className="card">
        <div className="p-1">
          <h2>{title}title</h2>
          <p className='parag'>s</p>
          <hr className='hr' />
            <Link className='bottom-card' to={to} >
              <p className='bottom-card-p'>Go to</p>
              <ArrowRightOutlined />
            </Link>
        </div>
    </div>
  );
}
 
export default Card;