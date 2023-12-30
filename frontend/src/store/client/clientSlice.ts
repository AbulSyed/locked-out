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

interface AlterClientRoleDto {
  clientId: string;
  roleIds: string[];
  rolesToAdd: Role[];
}

interface AlterClientAuthorityDto {
  clientId: string;
  authorityIds: string[];
  authorityToAdd: Authority[];
}

interface AlterClientScopesDto {
  clientId: string;
  scopesToAdd: string[];
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

export const alterClientRoles = createAsyncThunk('client/alterClientRoles', async (data: AlterClientRoleDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.put(`/alter-roles?addRoleTo=CLIENT`, {
      clientId: data.clientId,
      roleIds: data.roleIds
    }, {
      headers: {
        'x-correlation-id': 'frontend/alterClientRoles'
      }
    })

    if (res.status == 201) {
      return {
        clientId: data.clientId,
        rolesToAdd: data.rolesToAdd
      }
    }
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const alterClientAuthority = createAsyncThunk('client/alterClientAuthority', async (data: AlterClientAuthorityDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.put(`/alter-authority?addAuthorityTo=CLIENT`, {
      clientId: data.clientId,
      authorityIds: data.authorityIds
    }, {
      headers: {
        'x-correlation-id': 'frontend/alterClientAuthority'
      }
    })

    if (res.status == 201) {
      return {
        clientId: data.clientId,
        authorityToAdd: data.authorityToAdd
      }
    }
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const alterClientScopes = createAsyncThunk('client/alterClientScopes', async (data: AlterClientScopesDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.put(`/alter-client-scopes/${data.clientId}`, {
      scopes: data.scopesToAdd
    }, {
      headers: {
        'x-correlation-id': 'frontend/alterClientScopes'
      }
    })

    if (res.status == 200) {
      return {
        clientId: data.clientId,
        scopesToAdd: data.scopesToAdd
      }
    }
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
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
    // alter client roles
    builder.addCase(alterClientRoles.pending, (state) => {
      state.loading = true
    })
    builder.addCase(alterClientRoles.fulfilled, (state, action) => {
      state.loading = false
      state.clients = state.clients.map(client => {
        if (client.id == action.payload.clientId) {
          client.roles = action.payload.rolesToAdd
          return client
        } else {
          return client
        }
      })
      state.error = ''
    })
    builder.addCase(alterClientRoles.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // alter client authority
    builder.addCase(alterClientAuthority.pending, (state) => {
      state.loading = true
    })
    builder.addCase(alterClientAuthority.fulfilled, (state, action) => {
      state.loading = false
      state.clients = state.clients.map(client => {
        if (client.id == action.payload.clientId) {
          client.authorities = action.payload.authorityToAdd
          return client
        } else {
          return client
        }
      })
      state.error = ''
    })
    builder.addCase(alterClientAuthority.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // alter client scopes
    builder.addCase(alterClientScopes.pending, (state) => {
      state.loading = true
    })
    builder.addCase(alterClientScopes.fulfilled, (state, action) => {
      state.loading = false
      state.clients = state.clients.map(client => {
        if (client.id == action.payload.clientId) {
          client.scopes = action.payload.scopesToAdd
          return client
        } else {
          return client
        }
      })
      state.error = ''
    })
    builder.addCase(alterClientScopes.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
  },
})

export default clientSlice.reducer