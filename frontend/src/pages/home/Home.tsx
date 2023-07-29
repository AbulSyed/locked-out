import AppCard from '../../components/card/app-card/AppCard'
import DefaultCard from '../../components/card/default-card/DefaultCard';
import './Home.scss'

const Home: React.FC = () => {
  return (
    <div>
      <div className='home-container'>
        <p className='l-heading pt-1'>Welcome to LockedOut</p>
        <p className='m-heading py-1'>Your Apps</p>
        <div className='card-container'>
          <DefaultCard />
          <AppCard />
        </div>
      </div>
    </div>
  )
}

export default Home;