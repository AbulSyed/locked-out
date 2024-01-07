import './Home.scss'

import AppCard from '../../components/card/app-card/AppCard'
import DefaultAppCard from '../../components/card/default-card/DefaultAppCard';

import { useAppSelector } from '../../store/hooks'
import { Spin } from 'antd'

interface HomeProps {
}

const Home: React.FC<HomeProps> = () => {
  const state = useAppSelector(state => state.app)
  const loading = useAppSelector(state => state.app.loading)

  return (
    <div>
      <div className='home-container'>
        <p className='m-heading py-2'>Welcome to LockedOut</p>
        <div className='card-container'>
          <DefaultAppCard />
          {
            loading ? <Spin /> : (
              state.apps.map((app) => (
                <AppCard
                  key={app.id}
                  id={app.id}
                  title={app.name}
                  description={app.description}
                  to={`/${app.name}`}
                />
              ))
            )
          }
        </div>
      </div>
    </div>
  )
}

export default Home