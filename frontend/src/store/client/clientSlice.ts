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
  secret: string;
  roles: Role[];
  authorities: Authority[];
  scopes: Scope[];
  authMethods: AuthMethod[];
  authGrantTypes: AuthGrantType[];
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

interface Scope {
  name: string;
}

interface AuthMethod {
  name: string;
}

interface AuthGrantType {
  name: string;
}

interface CreateClientDto {
  appId: string;
  clientId: string;
  clientSecret: string;
  authMethod: string[];
  authGrantType: string[];
  redirectUri: string;
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
  },
})

export default clientSlice.reducer