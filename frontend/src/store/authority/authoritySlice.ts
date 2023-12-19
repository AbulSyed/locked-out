import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

import identityServiceApi from '../../api/identityServiceApi'

const initialState: InitialState = {
  loading: false,
  authorities: [],
  error: '',
}

interface InitialState {
  loading: boolean;
  authorities: Authority[];
  error: string;
}

interface Authority {
  id: string;
  name: string;
}

interface CreateAuthorityDto {
  name: string
}

export const getAuthorities = createAsyncThunk('authority/getAuthorities', async () => {
  try {
    const res = await identityServiceApi.get(`/get-authority-list`, {
      headers: {
        'x-correlation-id': 'frontend/getAuthorities'
      }
    })

    return res.data
  } catch (err: any) {
    return err.message
  }
})

export const createAuthority = createAsyncThunk('authority/createAuthority', async (data: CreateAuthorityDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.post(`/create-authority`, data, {
      headers: {
        'x-correlation-id': 'frontend/createAuthority'
      }
    })

    return res.data
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const deleteAuthorityCascade = createAsyncThunk('authority/deleteAuthority', async (authorityId: string, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.delete(`/delete-authority/${authorityId}`, {
      headers: {
        'x-correlation-id': 'frontend/deleteAuthority'
      }
    })

    if (res.status == 200) {
      return authorityId
    }
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

const authoritySlice = createSlice({
  name: 'authority',
  initialState,
  reducers: { },
  extraReducers: (builder) => {
    // get authorities
    builder.addCase(getAuthorities.pending, (state) => {
      state.loading = true
    })
    builder.addCase(getAuthorities.fulfilled, (state, action) => {
      state.loading = false
      state.authorities = action.payload
      state.error = ''
    })
    builder.addCase(getAuthorities.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // create a authority
    builder.addCase(createAuthority.pending, (state) => {
      state.loading = true
    })
    builder.addCase(createAuthority.fulfilled, (state, action) => {
      state.loading = false
      state.authorities.push(action.payload)
      state.error = ''
    })
    builder.addCase(createAuthority.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong...'
    })
    // delete authority entirely
    builder.addCase(deleteAuthorityCascade.pending, (state) => {
      state.loading = true
    })
    builder.addCase(deleteAuthorityCascade.fulfilled, (state, action) => {
      state.loading = false
      state.authorities = state.authorities.filter(authority => authority.id != action.payload)
      state.error = ''
    })
    builder.addCase(deleteAuthorityCascade.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong...'
    })
  },
})

export default authoritySlice.reducer