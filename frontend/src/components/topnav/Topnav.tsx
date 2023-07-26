import './Topnav.scss';
import logo from '../../assets/Logo.png';

const Topnav: React.FC = () => {
  return (
    <nav className='topnav'>
      <div className='main-container'>
        <div>
          <img src={logo} alt='Locked out logo' className='topnav-logo' />
          <span>LockedOut</span>
        </div>
        <ul className='topnav-list'>
          <li className='topnav-list-item'>Home</li>
          {/* <li className='topnav-list-item'>Docs</li> */}
        </ul>
      </div>
    </nav>
  )
}

export default Topnav;