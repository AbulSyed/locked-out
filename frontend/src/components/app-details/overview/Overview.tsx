import './Overview.scss'

import { Link, useParams } from 'react-router-dom'
import { useAppDispatch, useAppSelector } from '../../../store/hooks'
import { Spin } from 'antd'
import { setActiveNavReducer } from '../../../store/active-nav/activeNavSlice'

interface OverviewProps {
}

const Overview: React.FC<OverviewProps> = () => {
  const { appname } = useParams()

  const appDetails = useAppSelector(state => state.app.appDetails)
  const roles = useAppSelector(state => state.role.roles)
  const authorities = useAppSelector(state => state.authority.authorities)
  const appName = useAppSelector(state => state.app.appDetails.name)
  const loading = useAppSelector(state => state.app.loading)

  return ( 
    <div className='overview'>
      {
        loading ? <Spin /> : (
          <>
            <p className='m-heading py-2'>App: {appname}</p>
            <div className='overview-elements'>
              {
                appDetails.users.length == 0 ? (
                  <OverviewCard
                    message='No users registered'
                    appname={appName}
                    to='/users'
                    updateActiveNavTo='Users'
                  />
                ) : appDetails.users.length == 1 ? (
                  <OverviewCard
                    message={`${appDetails.users.length} user registered with app`}
                    appname={appName}
                    to='/users'
                    updateActiveNavTo='Users'
                  />
                ) : <OverviewCard
                      message={`${appDetails.users.length} users registered with app`}
                      appname={appName}
                      to='/users'
                      updateActiveNavTo='Users'
                    />
              }
              {
                appDetails.clients.length == 0 ? (
                  <OverviewCard
                    message='No clients registered'
                    appname={appName}
                    to='/clients'
                    updateActiveNavTo='Clients'
                  />
                ) : appDetails.clients.length == 1 ? (
                  <OverviewCard
                    message={`${appDetails.clients.length} client registered with app`}
                    appname={appName}
                    to='/clients'
                    updateActiveNavTo='Clients'
                  />
                ) : <OverviewCard
                      message={`${appDetails.clients.length} clients registered with app`}
                      appname={appName}
                      to='/clients'
                      updateActiveNavTo='Clients'
                    />
              }
              {
                roles.length == 0 ? (
                  <OverviewCard
                    message='No roles created'
                    appname={appName}
                    to='/roles'
                    updateActiveNavTo='Roles'
                  />
                ) : roles.length == 1 ? (
                  <OverviewCard
                    message={`${roles.length} role created`}
                    appname={appName}
                    to='/roles'
                    updateActiveNavTo='Roles'
                  />
                ) : <OverviewCard
                      message={`${roles.length} roles created`}
                      appname={appName}
                      to='/roles'
                      updateActiveNavTo='Roles'
                    />
              }
              {
                authorities.length == 0 ? (
                  <OverviewCard
                    message='No authorities created'
                    appname={appName}
                    to='/authorities'
                    updateActiveNavTo='Authorities'
                  />
                ) : authorities.length == 1 ? (
                  <OverviewCard
                    message={`${authorities.length} authority created`}
                    appname={appName}
                    to='/authorities'
                    updateActiveNavTo='Authorities'
                  />
                ) : <OverviewCard
                      message={`${authorities.length} authorities created`}
                      appname={appName}
                      to='/authorities'
                      updateActiveNavTo='Authorities'
                    />
              }
              {
                <OverviewCard
                  message='3 scopes available'
                  appname={appName}
                  to='/scopes'
                  updateActiveNavTo='Scopes'
                />
              }
            </div>
          </>
        )
      }
    </div>
  );
}
 
export default Overview

interface OverviewCardProps {
  message: string;
  appname: string;
  to: string;
  updateActiveNavTo: string;
}

const OverviewCard: React.FC<OverviewCardProps> = ({ message, appname, to, updateActiveNavTo }) => {
  const dispatch = useAppDispatch()

  return (
    <Link
      className='overview-card' to={'/apps/' + appname + to}
      onClick={() => dispatch(setActiveNavReducer(updateActiveNavTo))}
    >
      {message}
    </Link>
  )
}