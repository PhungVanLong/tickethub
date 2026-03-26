import { createContext, useContext, useReducer } from 'react'
import type { ReactNode } from 'react'

interface AppState {
  user: {
    isAuthenticated: boolean
    data: any | null
  }
  loading: boolean
}

interface AppAction {
  type: string
  payload?: any
}

const initialState: AppState = {
  user: {
    isAuthenticated: false,
    data: null
  },
  loading: false
}

const appReducer = (state: AppState, action: AppAction): AppState => {
  switch (action.type) {
    case 'SET_LOADING':
      return { ...state, loading: action.payload }
    case 'SET_USER':
      return {
        ...state,
        user: {
          isAuthenticated: !!action.payload,
          data: action.payload
        }
      }
    case 'LOGOUT':
      return {
        ...state,
        user: {
          isAuthenticated: false,
          data: null
        }
      }
    default:
      return state
  }
}

const AppContext = createContext<{
  state: AppState
  dispatch: React.Dispatch<AppAction>
} | null>(null)

export const AppProvider = ({ children }: { children: ReactNode }) => {
  const [state, dispatch] = useReducer(appReducer, initialState)

  return (
    <AppContext.Provider value={{ state, dispatch }}>
      {children}
    </AppContext.Provider>
  )
}

export const useAppContext = () => {
  const context = useContext(AppContext)
  if (!context) {
    throw new Error('useAppContext must be used within AppProvider')
  }
  return context
}
