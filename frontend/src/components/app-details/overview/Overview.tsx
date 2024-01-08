import './Overview.scss'

import { useParams } from 'react-router-dom'
import { useAppSelector } from '../../../store/hooks'
import { Spin, Statistic } from 'antd'

interface OverviewProps {
}

const Overview: React.FC<OverviewProps> = () => {
  const { appname } = useParams()

  const appDetails = useAppSelector(state => state.app.appDetails)
  const roles = useAppSelector(state => state.role.roles)
  const authorities = useAppSelector(state => state.authority.authorities)
  const loading = useAppSelector(state => state.app.loading)

  return ( 
    <div className='overview'>
      {
        loading ? <Spin /> : (
          <>
            <p className='m-heading py-2'>App: {appname}</p>
            <div className='overview-elements'>
              <Statistic
                title='Users registered with app'
                value={appDetails.users.length}
              />
              <Statistic
                title='Clients registered with app'
                value={appDetails.clients.length}
              />
              <Statistic
                title='Roles available'
                value={roles.length}
              />
              <Statistic
                title='Authorities availables'
                value={authorities.length}
              />
              <Statistic
                title='Scopes availables'
                value={3}
              />
            </div>
          </>
        )
      }
    </div>
  );
}
 
export default Overview
