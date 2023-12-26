import './Users.scss'

import UserCard from '../../card/user-card/UserCard';
import DefaultUserCard from '../../card/default-card/DefaultUserCard';

import { useAppSelector } from '../../../store/hooks';

interface UsersProps {
}

const Users: React.FC<UsersProps> = () => {
  const users = useAppSelector(state => state.user.users)

  return ( 
    <div className='users'>
      <p className='m-heading py-2'>Users</p>
      <div className='users-container'>
        <DefaultUserCard />
        {
          users && users.length > 0 ? (
            users.map(user => (
              <UserCard
                key={user.id}
                id={user.id}
                username={user.username}
                password={user.password}
                email={user.email}
                phoneNumber={user.phoneNumber}
                roles={user.roles}
                authorities={user.authorities}
                createdAt={user.createdAt} />
            ))
          ) : <p>No users</p>
        }
      </div>
    </div>
  );
}
 
export default Users