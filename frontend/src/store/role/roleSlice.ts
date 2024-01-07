import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

import identityServiceApi from '../../api/identityServiceApi'

const initialState: InitialState = {
  loading: false,
  roles: [],
  error: '',
}

interface InitialState {
  loading: boolean;
  roles: Role[];
  error: string;
}

interface Role {
  id: string;
  name: string;
}

interface CreateRoleDto {
  name: string
}

export const getRoles = createAsyncThunk('role/getRoles', async () => {
  try {
    const res = await identityServiceApi.get(`/get-role-list`, {
      headers: {
        'x-correlation-id': 'frontend/getRoles'
      }
    })

    return res.data
  } catch (err: any) {
    return err.message
  }
})

export const createRole = createAsyncThunk('role/createRole', async (data: CreateRoleDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.post(`/create-role`, data, {
      headers: {
        'x-correlation-id': 'frontend/createRole'
      }
    })

    return res.data
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const deleteRoleCascade = createAsyncThunk('role/deleteRole', async (roleId: string, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.delete(`/delete-role/${roleId}`, {
      headers: {
        'x-correlation-id': 'frontend/deleteRole'
      }
    })

    if (res.status == 200) {
      return roleId
    }
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

const roleSlice = createSlice({
  name: 'role',
  initialState,
  reducers: { },
  extraReducers: (builder) => {
    // get roles
    builder.addCase(getRoles.pending, (state) => {
      state.loading = true
    })
    builder.addCase(getRoles.fulfilled, (state, action) => {
      state.loading = false
      state.roles = action.payload
      state.error = ''
    })
    builder.addCase(getRoles.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // create a role
    builder.addCase(createRole.pending, (state) => {
      state.loading = true
    })
    builder.addCase(createRole.fulfilled, (state, action) => {
      state.loading = false
      state.roles.push(action.payload)
      state.error = ''
    })
    builder.addCase(createRole.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong...'
    })
    // delete role entirely
    builder.addCase(deleteRoleCascade.pending, (state) => {
      state.loading = true
    })
    builder.addCase(deleteRoleCascade.fulfilled, (state, action) => {
      state.loading = false
      state.roles = state.roles.filter(role => role.id != action.payload)
      state.error = ''
    })
    builder.addCase(deleteRoleCascade.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong...'
    })
  },
})

export default roleSlice.reducer