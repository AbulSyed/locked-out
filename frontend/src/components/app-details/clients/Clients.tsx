import './Clients.scss'

import DefaultClientCard from '../../card/default-card/DefaultClientCard';
import ClientCard from '../../card/client-card/ClientCard';

import { useAppSelector } from '../../../store/hooks';

interface ClientsProps {
}

const Clients: React.FC<ClientsProps> = () => {
  const clients = useAppSelector(state => state.client.clients)

  return ( 
    <div className='clients'>
      <p className='m-heading py-2'>Clients</p>
      <div className='clients-container'>
        <DefaultClientCard />
        {
          clients && clients.length > 0 ? (
            clients.map(client => (
              <ClientCard
                key={client.id}
                id={client.id}
                clientId={client.clientId}
                clientSecret={client.clientSecret}
                roles={client.roles}
                authorities={client.authorities}
                scopes={client.scopes}
                authMethod={client.authMethod}
                authGrantType={client.authGrantType}
                redirectUri={client.redirectUri}
                createdAt={client.createdAt} />
            ))
          ) : <p>No clients</p>
        }
      </div>
    </div>
  );
}
 
export default Clients