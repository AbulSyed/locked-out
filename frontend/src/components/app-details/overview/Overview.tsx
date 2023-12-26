import './Overview.scss'

import { useParams } from 'react-router-dom';

interface OverviewProps {
}

const Overview: React.FC<OverviewProps> = () => {
  const { appname } = useParams()

  return ( 
    <div className='overview'>
      <p className='m-heading py-2'>App: {appname}</p>
    </div>
  );
}
 
export default Overview