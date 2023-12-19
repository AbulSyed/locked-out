import { configureStore } from '@reduxjs/toolkit'

import appSlice from './app/appSlice'
import activeNavSlice from './active-nav/activeNavSlice'
import roleSlice from './role/roleSlice'
import authoritySlice from './authority/authoritySlice';

const store = configureStore({
  reducer: {
    app: appSlice,
    activeNav: activeNavSlice,
    role: roleSlice,
    authority: authoritySlice
  },
});

export default store
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch