import './Overview.scss'

import { useParams } from 'react-router-dom';
import { useAppSelector } from '../../../store/hooks';

interface OverviewProps {
}

const Overview: React.FC<OverviewProps> = () => {
  const { appname } = useParams()

  const appDetails = useAppSelector(state => state.app.appDetails)

  return ( 
    <div className='overview'>
      <p className='m-heading py-2'>App: {appname}</p>
      {
        appDetails.users.length == 0 ? (
          <p>No users registered</p>
        ) : appDetails.users.length == 1 ? (
          <p>{appDetails.users.length} user registered with app</p>
        ) : <p>{appDetails.users.length} users registered with app</p>
      }
      {
        appDetails.clients.length == 0 ? (
          <p>No clients registered</p>
        ) : appDetails.clients.length == 1 ? (
          <p>{appDetails.clients.length} client registered with app</p>
        ) : <p>{appDetails.clients.length} clients registered with app</p>
      }
    </div>
  );
}
 
export default Overview