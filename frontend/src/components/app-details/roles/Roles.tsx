import './Roles.scss'

import SimpleForm from '../../form/simple-form/SimpleForm'
import Block from '../../block/Block'

import { useAppSelector } from '../../../store/hooks'
import { deleteRoleCascade } from '../../../store/role/roleSlice'

interface RolesProps {
}

const Roles: React.FC<RolesProps> = () => {
  const role = useAppSelector(state => state.role)

  return ( 
    <div className='roles'>
      <p>Roles can be added to both <i>Users</i> & <i>Clients</i>. Roles added to either a <i>User</i> or <i>Client</i> will be present as a claim on the JWT.</p>

      <br />
      <hr />
      <br />

      <SimpleForm type='Create role' />

      {
        role.roles.length > 0 ? (
          <>
            <p style={{ 'margin': '1rem 0', 'textAlign': 'center' }}>All roles:</p>
            {
              <Block items={role.roles} showDelete={true} action={deleteRoleCascade} />
            }
          </>
        ) : <p style={{ 'margin': '1rem 0', 'textAlign': 'center' }}>No roles created</p>
      }
    </div>
  );
}
 
export default Roles