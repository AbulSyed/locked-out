import './MainLayout.scss'
import Topnav from '../components/topnav/Topnav'
import Sidenav from '../components/sidenav/Sidenav'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Home from '../pages/home/Home'

const MainLayout: React.FC = () => {
  return (
    <BrowserRouter>
      <Topnav />
      <div style={{display: 'flex'}}>
        <Sidenav />
        <div style={{padding: '2rem'}}>
          <Routes>
            <Route path="/" element={<Home />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  )
}

export default MainLayout;