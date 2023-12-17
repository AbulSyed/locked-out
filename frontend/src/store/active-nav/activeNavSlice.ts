import { createSlice } from '@reduxjs/toolkit'

const initialState: InitialState = {
  activeNav: 'Overview'
}

interface InitialState {
  activeNav: string;
}

const activeNavSlice = createSlice({
  name: 'activeNav',
  initialState,
  reducers: {
    setActiveNavReducer(state, action) {
      state.activeNav = action.payload
    }
  },
})

export default activeNavSlice.reducer
export const { setActiveNavReducer } = activeNavSlice.actions