import AppCard from '../../components/card/app-card/AppCard'
import DefaultCard from '../../components/card/default-card/DefaultCard';
import './Home.scss'

const Home: React.FC = () => {
  return (
    <div>
      <div className='home-container'>
        <p className='m-heading py-2'>Welcome to LockedOut</p>
        <div className='card-container'>
          <DefaultCard />
          <AppCard id="1" title="My App" description='my first cool app' to="/app-1/overview" />
        </div>
      </div>
    </div>
  )
}

export default Home;