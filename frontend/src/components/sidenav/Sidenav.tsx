import './Sidenav.scss'
import { NavLink } from 'react-router-dom';

const Sidenav: React.FC = () => {
  return (
    <div className='sidenav'>
      <ul className='sidenav-list'>
        <NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/">
          Home
        </NavLink>
        <NavLink className={({ isActive, isPending }) => isPending ? "pending li" : isActive ? "active li" : "li"} to="/ss">
          Hello
        </NavLink>
        {/* <li className='sidenav-list-item'>Home</li>
        <li className='sidenav-list-item'>Apps</li>
        <li className='sidenav-list-item'>Clients</li>
        <li className='sidenav-list-item'>Users</li> */}
      </ul>
      <span className='version'>{import.meta.env.VITE_APP_VERSION}</span>
    </div>
  )
}

export default Sidenav;