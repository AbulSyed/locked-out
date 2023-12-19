import { configureStore } from '@reduxjs/toolkit'

import appSlice from './app/appSlice'
import activeNavSlice from './active-nav/activeNavSlice'
import roleSlice from './role/roleSlice'

const store = configureStore({
  reducer: {
    app: appSlice,
    activeNav: activeNavSlice,
    role: roleSlice
  },
});

export default store
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch