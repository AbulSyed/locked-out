import './MainLayout.scss'
import Topnav from '../components/topnav/Topnav'
import Sidenav from '../components/sidenav/Sidenav'

const MainLayout: React.FC = () => {
  return (
    <div>
      <Topnav />
      <div style={{display: 'flex'}}>
        <Sidenav />
        <div style={{padding: '2rem'}}>
          hi
        </div>
      </div>
    </div>
  )
}

export default MainLayout;