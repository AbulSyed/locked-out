import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

import identityServiceApi from '../../api/identityServiceApi'

const initialState: InitialState = {
  loading: false,
  users: [],
  error: '',
}

interface InitialState {
  loading: boolean;
  users: User[];
  error: string;
}

interface CreateUserDto {
  appId: string;
  username: string;
  password: string;
  email: string;
  phoneNumber: string;
}

interface User {
  id: string;
  username: string;
  password: string;
  email: string;
  phoneNumber: string;
  roles: Role[];
  authorities: Authority[];
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

interface UpdateUserDto extends CreateUserDto {
  id: string;
}

export const getUsersByAppName = createAsyncThunk('user/getUsersByAppName', async (appName: string) => {
  try {
    const res = await identityServiceApi.get(`/get-user-list-by-app?appName=${appName}`, {
      headers: {
        'x-correlation-id': 'frontend/getUsersByAppName'
      }
    })

    return res.data
  } catch (err: any) {
    return err.message
  }
})

export const createUser = createAsyncThunk('user/createUser', async (data: CreateUserDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.post(`/create-user/${data.appId}`, data, {
      headers: {
        'x-correlation-id': 'frontend/createUser'
      }
    })

    console.log(res.data)

    return res.data
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const updateUser = createAsyncThunk('user/updateUser', async (data: UpdateUserDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.put(`/update-user/${data.id}`, {
      username: data.username,
      password: data.password,
      email: data.email,
      phoneNumber: data.phoneNumber
    }, {
      headers: {
        'x-correlation-id': 'frontend/updateUser'
      }
    })

    return res.data
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const deleteUser = createAsyncThunk('user/deleteUser', async (userId: string) => {
  try {
    const res = await identityServiceApi.delete(`/delete-user/${userId}`, {
      headers: {
        'x-correlation-id': 'frontend/deleteUser'
      }
    })

    if (res.status == 200) {
      return userId
    }
  } catch (err: any) {
    return err.message
  }
})

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: { },
  extraReducers: (builder) => {
    // get user by app name
    builder.addCase(getUsersByAppName.pending, (state) => {
      state.loading = true
    })
    builder.addCase(getUsersByAppName.fulfilled, (state, action) => {
      state.loading = false
      state.users = action.payload
      state.error = ''
    })
    builder.addCase(getUsersByAppName.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // create a user
    builder.addCase(createUser.pending, (state) => {
      state.loading = true
    })
    builder.addCase(createUser.fulfilled, (state, action) => {
      state.loading = false
      state.users.push(action.payload)
      state.error = ''
    })
    builder.addCase(createUser.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong...'
    })
    // update user
    builder.addCase(updateUser.fulfilled, (state, action) => {
      state.loading = false
      state.users = state.users.map(user => {
        if (user.id == action.payload.id) {
          return action.payload
        } else {
          return user
        }
      })
      state.error = ''
    })
    builder.addCase(updateUser.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // delete user
    builder.addCase(deleteUser.pending, (state) => {
      state.loading = true
    })
    builder.addCase(deleteUser.fulfilled, (state, action) => {
      state.loading = false
      state.users = state.users.filter(user => user.id != action.payload)
      state.error = ''
    })
    builder.addCase(deleteUser.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
  },
})

export default userSlice.reducer