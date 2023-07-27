import Card from '../../components/card/Card'
import './Home.scss'

const Home: React.FC = () => {
  return (
    <div>
      <div className='home-container'>
        <p className='l-heading pt-1'>Welcome to LockedOut</p>
        <p className='m-heading py-1'>Create an App</p>
        <div className='card-container'>
          <Card />
          <Card />
          <Card />
          <Card />
        </div>
      </div>
    </div>
  )
}

export default Home;