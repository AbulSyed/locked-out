import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

import identityServiceApi from '../../api/identityServiceApi'

const initialState: InitialState = {
  loading: false,
  clients: [],
  error: '',
}

interface InitialState {
  loading: boolean;
  clients: Client[];
  error: string;
}

interface Client {
  id: string;
  clientId: string;
  clientSecret: string;
  roles: Role[];
  authorities: Authority[];
  scopes: string[];
  authMethod: string[];
  authGrantType: string[];
  redirectUri: string;
  createdAt: string;
}

interface Role {
  id: string;
  name: string;
}

interface Authority {
  id: string;
  name: string;
}

interface CreateClientDto {
  appId?: string;
  clientId: string;
  clientSecret: string;
  authMethod: string[];
  authGrantType: string[];
  redirectUri: string;
}

interface UpdateClientDto extends CreateClientDto {
  id: string
}

export const createClient = createAsyncThunk('client/createClient', async (data: CreateClientDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.post(`/create-client/${data.appId}`, data, {
      headers: {
        'x-correlation-id': 'frontend/createClient'
      }
    })

    return res.data
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const getClientsByAppName = createAsyncThunk('client/getClientsByAppName', async (appName: string) => {
  try {
    const res = await identityServiceApi.get(`/get-client-list-by-app?appName=${appName}`, {
      headers: {
        'x-correlation-id': 'frontend/getClientByAppName'
      }
    })

    return res.data
  } catch (err: any) {
    return err.message
  }
})

export const updateClient = createAsyncThunk('client/updateClient', async (data: UpdateClientDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.put(`/update-client/${data.id}`, {
      clientId: data.clientId,
      clientSecret: data.clientSecret,
      authMethod: data.authMethod,
      authGrantType: data.authGrantType,
      redirectUri: data.redirectUri
    }, {
      headers: {
        'x-correlation-id': 'frontend/updateClient'
      }
    })

    return res.data
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const deleteClient = createAsyncThunk('client/deleteClient', async (id: string) => {
  try {
    const res = await identityServiceApi.delete(`/delete-client/${id}`, {
      headers: {
        'x-correlation-id': 'frontend/deleteClient'
      }
    })

    if (res.status == 200) {
      return id
    }
  } catch (err: any) {
    return err.message
  }
})

const clientSlice = createSlice({
  name: 'client',
  initialState,
  reducers: { },
  extraReducers: (builder) => {
    // create a client
    builder.addCase(createClient.pending, (state) => {
      state.loading = true
    })
    builder.addCase(createClient.fulfilled, (state, action) => {
      state.loading = false
      state.clients.push(action.payload)
      state.error = ''
    })
    builder.addCase(createClient.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong...'
    })
    // get clients by app name
    builder.addCase(getClientsByAppName.pending, (state) => {
      state.loading = true
    })
    builder.addCase(getClientsByAppName.fulfilled, (state, action) => {
      state.loading = false
      state.clients = action.payload
      state.error = ''
    })
    builder.addCase(getClientsByAppName.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // update client
    builder.addCase(updateClient.fulfilled, (state, action) => {
      state.loading = false
      state.clients = state.clients.map(client => {
        if (client.id == action.payload.id) {
          return action.payload
        } else {
          return client
        }
      })
      state.error = ''
    })
    builder.addCase(updateClient.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // delete client
    builder.addCase(deleteClient.pending, (state) => {
      state.loading = true
    })
    builder.addCase(deleteClient.fulfilled, (state, action) => {
      state.loading = false
      state.clients = state.clients.filter(client => client.id != action.payload)
      state.error = ''
    })
    builder.addCase(deleteClient.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
  },
})

export default clientSlice.reducer