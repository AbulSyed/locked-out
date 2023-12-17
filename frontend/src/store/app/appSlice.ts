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

interface AppDto {
  name: string,
  description: string
}

export const createApp = createAsyncThunk('app/createApp', async (data: AppDto, thunkAPI: any) => {
  try {
    const res = await identityServiceApi.post(`/create-app`, data, {
      headers: {
        'x-correlation-id': 'frontend/createApp'
      }
    })
    
    return res.data;
  } catch (err: any) {
    return thunkAPI.rejectWithValue({ message: err.message });
  }
})

export const getApps = createAsyncThunk('app/getApps', async () => {
  try {
    const res = await identityServiceApi.get(`/get-app-list`, {
      headers: {
        'x-correlation-id': 'frontend/getApps'
      }
    })

    return res.data;
  } catch (err: any) {
    return err.message;
  }
})

const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: { },
  extraReducers: (builder) => {
    // create an app
    builder.addCase(createApp.pending, (state) => {
      state.loading = true;
    })
    builder.addCase(createApp.fulfilled, (state, action) => {
      state.loading = false;
      state.apps.push(action.payload);
      state.error = '';
    })
    builder.addCase(createApp.rejected, (state, action) => {
      state.loading = false;
      state.apps = [];
      state.error = action.error.message || 'Something went wrong...';
    })
    // get apps
    builder.addCase(getApps.pending, (state) => {
      state.loading = true;
    })
    builder.addCase(getApps.fulfilled, (state, action) => {
      state.loading = false;
      state.apps = action.payload;
      state.error = '';
    })
    builder.addCase(getApps.rejected, (state, action) => {
      state.loading = false;
      state.apps = [];
      state.error = action.error.message || 'Something went wrong';
    })
  },
})

export default appSlice.reducer