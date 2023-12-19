import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

import identityServiceApi from '../../api/identityServiceApi'

const initialState: InitialState = {
  loading: false,
  apps: [],
  error: '',
}

interface InitialState {
  loading: boolean;
  apps: App[];
  error: string;
}

interface App {
  id: string,
  name: string,
  description: string,
  createdAt: string
}

interface CreateAppDto {
  name: string,
  description: string
}

interface UpdateAppDto extends CreateAppDto {
  id: string
}

export const createApp = createAsyncThunk('app/createApp', async (data: CreateAppDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.post(`/create-app`, data, {
      headers: {
        'x-correlation-id': 'frontend/createApp'
      }
    })

    return res.data
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const getApps = createAsyncThunk('app/getApps', async () => {
  try {
    const res = await identityServiceApi.get(`/get-app-list`, {
      headers: {
        'x-correlation-id': 'frontend/getApps'
      }
    })

    return res.data
  } catch (err: any) {
    return err.message
  }
})

export const updateApp = createAsyncThunk('app/updateApp', async (data: UpdateAppDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.put(`/update-app/${data.id}`, {
      name: data.name,
      description: data.description
    }, {
      headers: {
        'x-correlation-id': 'frontend/updateApp'
      }
    })

    return res.data
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message })
  }
})

export const deleteApp = createAsyncThunk('app/deleteApp', async (appId: string) => {
  try {
    const res = await identityServiceApi.delete(`/delete-app/${appId}`, {
      headers: {
        'x-correlation-id': 'frontend/deleteApp'
      }
    })

    if (res.status == 200) {
      return appId
    }
  } catch (err: any) {
    return err.message
  }
})

const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: { },
  extraReducers: (builder) => {
    // create an app
    builder.addCase(createApp.pending, (state) => {
      state.loading = true
    })
    builder.addCase(createApp.fulfilled, (state, action) => {
      state.loading = false
      state.apps.push(action.payload)
      state.error = ''
    })
    builder.addCase(createApp.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong...'
    })
    // get apps
    builder.addCase(getApps.pending, (state) => {
      state.loading = true
    })
    builder.addCase(getApps.fulfilled, (state, action) => {
      state.loading = false
      state.apps = action.payload
      state.error = ''
    })
    builder.addCase(getApps.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // update app
    builder.addCase(updateApp.pending, (state) => {
      state.loading = true
    })
    builder.addCase(updateApp.fulfilled, (state, action) => {
      state.loading = false
      state.apps = state.apps.map(app => {
        if (app.id == action.payload.id) {
          return action.payload
        } else {
          return app
        }
      })
      state.error = ''
    })
    builder.addCase(updateApp.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
    // delete app
    builder.addCase(deleteApp.pending, (state) => {
      state.loading = true
    })
    builder.addCase(deleteApp.fulfilled, (state, action) => {
      state.loading = false
      state.apps = state.apps.filter(app => app.id != action.payload)
      state.error = ''
    })
    builder.addCase(deleteApp.rejected, (state, action) => {
      state.loading = false
      state.error = action.error.message || 'Something went wrong'
    })
  },
})

export default appSlice.reducer