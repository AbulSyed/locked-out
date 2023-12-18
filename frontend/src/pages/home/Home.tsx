import './Home.scss'

import AppCard from '../../components/card/app-card/AppCard'
import DefaultCard from '../../components/card/default-card/DefaultCard';

import { useAppSelector } from '../../store/hooks'

const Home: React.FC = () => {
  const state = useAppSelector(state => state.app)

  return (
    <div>
      <div className='home-container'>
        <p className='m-heading py-2'>Welcome to LockedOut</p>
        <div className='card-container'>
          <DefaultCard />
          {
            state.apps.map((app) => (
              <AppCard key={app.id} id={app.id} title={app.name} description={app.description} to={`/${app.name}/overview`} />
            ))
          }
        </div>
      </div>
    </div>
  )
}

export default Home