import './Overview.scss'

import { useParams } from 'react-router-dom';

interface OverviewProps {
}

const Overview: React.FC<OverviewProps> = () => {
  const { appname } = useParams()

  return ( 
    <div className='overview'>
      {appname}
    </div>
  );
}
 
export default Overview